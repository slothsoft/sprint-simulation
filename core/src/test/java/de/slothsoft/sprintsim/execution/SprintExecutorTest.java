package de.slothsoft.sprintsim.execution;

import org.junit.Assert;
import org.junit.Test;

import de.slothsoft.sprintsim.Member;
import de.slothsoft.sprintsim.Sprint;
import de.slothsoft.sprintsim.Task;
import de.slothsoft.sprintsim.config.TaskConfig;

public class SprintExecutorTest {

	// TODO: test tasks with complexity

	private static final double DELTA = 0.001;

	// -- ZERO MEMBERS --

	@Test
	public void testNoMemberOneTask() throws Exception {
		// Arrange
		final Task task = new Task();

		final Sprint sprint = new Sprint(task);

		// Act
		final SprintExecutor executor = new SprintExecutor();
		final SprintRetro result = executor.execute(sprint);

		// Assert
		Assert.assertNotNull(result);

		// we don't know how long tasks take without people
		Assert.assertEquals(Double.NaN, result.getNecessaryAdditionalHours(), DELTA);
		Assert.assertEquals(Double.NaN, result.getRemainingHours(), DELTA);
	}

	// -- ONE MEMBER --

	@Test
	public void testOneMemberOneTask() throws Exception {
		// Arrange
		final Member member = Member.createNormal();

		final Task task = new Task();

		final Sprint sprint = new Sprint(task);

		// Act
		final SprintExecutor executor = new SprintExecutor(member);
		final SprintRetro result = executor.execute(sprint);

		// Assert
		Assert.assertNotNull(result);

		// member has 80 hours in the 10 days of the sprint
		// the task only takes 8 of those
		Assert.assertEquals(0, result.getNecessaryAdditionalHours(), DELTA);
		Assert.assertEquals(72, result.getRemainingHours(), DELTA);
	}

	@Test
	public void testOneMemberMultipleTasks() throws Exception {
		// Arrange
		final Member member = Member.createNormal();

		final Task[] tasks = new Task[10];
		for (int i = 0; i < tasks.length; i++) {
			tasks[i] = new Task();
		}

		final Sprint sprint = new Sprint(tasks);

		// Act
		final SprintExecutor executor = new SprintExecutor(member);
		final SprintRetro result = executor.execute(sprint);

		// Assert
		Assert.assertNotNull(result);

		// member has 80 hours in the 10 days of the sprint
		// the tasks take 80 of those
		Assert.assertEquals(0, result.getNecessaryAdditionalHours(), DELTA);
		Assert.assertEquals(0, result.getRemainingHours(), DELTA);
	}

	@Test
	public void testOneMemberTooManyTasks() throws Exception {
		// Arrange
		final Member member = Member.createNormal();

		final Task[] tasks = new Task[11];
		for (int i = 0; i < tasks.length; i++) {
			tasks[i] = new Task();
		}

		final Sprint sprint = new Sprint(tasks);

		// Act
		final SprintExecutor executor = new SprintExecutor(member);
		final SprintRetro result = executor.execute(sprint);

		// Assert
		Assert.assertNotNull(result);

		// member has 80 hours in the 10 days of the sprint
		// the tasks take 88 of those
		Assert.assertEquals(8, result.getNecessaryAdditionalHours(), DELTA);
		Assert.assertEquals(0, result.getRemainingHours(), DELTA);
	}

	// -- TWO MEMBERS --

	@Test
	public void testTwoMembersOneTask() throws Exception {
		// Arrange
		final Member member1 = Member.createNormal();
		final Member member2 = Member.createNormal();

		final Task task = new Task();

		final Sprint sprint = new Sprint(task).lengthInDays(5);

		// Act
		final SprintExecutor executor = new SprintExecutor(member1, member2);
		final SprintRetro result = executor.execute(sprint);

		// Assert
		Assert.assertNotNull(result);

		// members have 80 hours in the 5 days of the sprint
		// the task only takes 8 of those
		Assert.assertEquals(0, result.getNecessaryAdditionalHours(), DELTA);
		Assert.assertEquals(72, result.getRemainingHours(), DELTA);
	}

	@Test
	public void testTwoMemberMultipleTasks() throws Exception {
		// Arrange
		final Member member1 = Member.createNormal();
		final Member member2 = Member.createNormal();

		final Task[] tasks = new Task[20];
		for (int i = 0; i < tasks.length; i++) {
			tasks[i] = new Task();
		}

		final Sprint sprint = new Sprint(tasks);

		// Act
		final SprintExecutor executor = new SprintExecutor(member1, member2);
		final SprintRetro result = executor.execute(sprint);

		// Assert
		Assert.assertNotNull(result);

		// members have 160 hours in the 10 days of the sprint
		// the tasks take 160 of those
		Assert.assertEquals(0, result.getNecessaryAdditionalHours(), DELTA);
		Assert.assertEquals(0, result.getRemainingHours(), DELTA);
	}

	@Test
	public void testTwoMembersTooManyTasks() throws Exception {
		// Arrange
		final Member member1 = Member.createNormal().workHoursPerDay(6);
		final Member member2 = Member.createNormal().workHoursPerDay(2);

		final Task[] tasks = new Task[11];
		for (int i = 0; i < tasks.length; i++) {
			tasks[i] = new Task();
		}

		final Sprint sprint = new Sprint(tasks);

		// Act
		final SprintExecutor executor = new SprintExecutor(member1, member2);
		final SprintRetro result = executor.execute(sprint);

		// Assert
		Assert.assertNotNull(result);

		// members have 80 hours in the 10 days of the sprint
		// the tasks take 88 of those
		Assert.assertEquals(8, result.getNecessaryAdditionalHours(), DELTA);
		Assert.assertEquals(0, result.getRemainingHours(), DELTA);
	}

	// -- IT'S COMPLICATED --

	@Test
	public void testTwoMembersWithDifferentPerformance() throws Exception {
		// Arrange
		final Member memberSlow = Member.createNormal().workPerformance(task -> 2);
		final Member memberNormal = Member.createNormal();

		final Task[] tasks = new Task[15];
		for (int i = 0; i < tasks.length; i++) {
			tasks[i] = new Task();
		}

		final Sprint sprint = new Sprint(tasks);

		// Act
		final SprintExecutor executor = new SprintExecutor(memberSlow, memberNormal);
		final SprintRetro result = executor.execute(sprint);

		// Assert
		Assert.assertNotNull(result);

		// memberSlow can only do 5 tasks
		// memberNormal can do 10
		Assert.assertEquals(0, result.getNecessaryAdditionalHours(), DELTA);
		Assert.assertEquals(0, result.getRemainingHours(), DELTA);
	}

	@Test
	public void testTwoMembersWithDifferentPerformanceTwoMore() throws Exception {
		// Arrange
		final Member memberSlow = Member.createNormal().workPerformance(task -> 2);
		final Member memberNormal = Member.createNormal();

		final Task[] tasks = new Task[17];
		for (int i = 0; i < tasks.length; i++) {
			tasks[i] = new Task();
		}

		final Sprint sprint = new Sprint(tasks);

		// Act
		final SprintExecutor executor = new SprintExecutor(memberSlow, memberNormal);
		final SprintRetro result = executor.execute(sprint);

		// Assert
		Assert.assertNotNull(result);

		// memberSlow can only do 5 tasks
		// memberNormal can do 10
		// from the other two each should get one (so that's additional 16 + 8 hours)
		Assert.assertEquals(24, result.getNecessaryAdditionalHours(), DELTA);
		Assert.assertEquals(0, result.getRemainingHours(), DELTA);
	}

	@Test
	public void testTwoMembersWithDifferentPerformanceThreeMore() throws Exception {
		// Arrange
		final Member memberSlow = Member.createNormal().workPerformance(task -> 2);
		final Member memberNormal = Member.createNormal();

		final Task[] tasks = new Task[18];
		for (int i = 0; i < tasks.length; i++) {
			tasks[i] = new Task();
		}

		final Sprint sprint = new Sprint(tasks);

		// Act
		final SprintExecutor executor = new SprintExecutor(memberSlow, memberNormal);
		final SprintRetro result = executor.execute(sprint);

		// Assert
		Assert.assertNotNull(result);

		// memberSlow can only do 5 tasks
		// memberNormal can do 10
		// from the other three each should get one, then normal gets another
		// (so that's additional 16 + 8 * 2 hours)
		Assert.assertEquals(32, result.getNecessaryAdditionalHours(), DELTA);
		Assert.assertEquals(0, result.getRemainingHours(), DELTA);
	}

	@Test
	public void testOneMemberWorksOnly() throws Exception {
		// Arrange
		final Member member1 = Member.createNormal();
		final Member member2 = Member.createNormal();

		final Sprint sprint = new Sprint(new Task());

		final SprintExecutor executor = new SprintExecutor(member1, member2);
		executor.setTaskConfig(new TaskConfig().mediumComplexityHours(100));

		// Act
		final SprintRetro result = executor.execute(sprint);

		// Assert
		Assert.assertNotNull(result);

		// only one member can do the task, and he will take 100h of his 80h for it
		// the other one has nothing to do
		Assert.assertEquals(20, result.getNecessaryAdditionalHours(), DELTA);
		Assert.assertEquals(80, result.getRemainingHours(), DELTA);
	}

}
