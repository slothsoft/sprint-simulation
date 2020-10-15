package de.slothsoft.sprintsim.generation;

import java.util.Arrays;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.slothsoft.sprintsim.Complexity;
import de.slothsoft.sprintsim.Task;
import de.slothsoft.sprintsim.Member;

/**
 * "Impl" tests check implementation specific methods.
 *
 * @author Stef Schulz
 */

public class SprintGeneratorImplTest {

	private static final double DELTA = 0.001;

	private SprintGenerator generator;

	@Before
	public void setUp() {
		this.generator = new SprintGenerator();
		this.generator.rnd = new Random(1234567890l);
	}

	@Test
	public void testCreateTask() throws Exception {
		// Arrange
		this.generator.setMembers(Member.createNormal());

		// Act
		final Task[] tasks = IntStream.range(0, 100).mapToObj(i -> this.generator.createTask())
				.toArray(Task[]::new);

		// Assert

		// on default the three types of task should have the same number
		// 100 / 3 = 33.3, but it's random, so let's assume 30 of each at least

		final int minNumberOfTasks = 30;

		for (final Complexity complexity : Complexity.values()) {
			final Task[] filteredTasks = findTasks(tasks, i -> i.getComplexity() == complexity);
			Assert.assertTrue("Tasks with complexity " + complexity + " are too few: " + filteredTasks.length,
					filteredTasks.length >= minNumberOfTasks);
		}
	}

	private static Task[] findTasks(Task[] tasks, Predicate<Task> filter) {
		return Arrays.stream(tasks).filter(filter).toArray(Task[]::new);
	}

	@Test
	public void testEstimateTaskFromMemberMediumNoDeviation() throws Exception {
		// Arrange
		final Member member = Member.createNormal().estimationDeviation(0);

		final Task task = new Task().complexity(Complexity.MEDIUM);

		// Act + Assert

		// on default medium tasks should be 8h

		for (int i = 0; i < 10; i++) {
			final double estimation = this.generator.estimateTaskFromMember(task, member);

			Assert.assertEquals(8, estimation, DELTA);
		}
	}

	@Test
	public void testEstimateTaskFromMemberMedium() throws Exception {
		// Arrange
		final Member member = Member.createNormal().estimationDeviation(0.1);

		final Task task = new Task().complexity(Complexity.MEDIUM);

		// Act + Assert

		// on default medium tasks should be around 8h with 10% deviation

		for (int i = 0; i < 10; i++) {
			final double estimation = this.generator.estimateTaskFromMember(task, member);

			Assert.assertTrue("Estimation #" + i + " too low: " + estimation, estimation >= 7.2);
			Assert.assertTrue("Estimation #" + i + " too high: " + estimation, estimation <= 8.8);
		}
	}

	@Test
	public void testEstimateTaskFromMemberLowNoDeviation() throws Exception {
		// Arrange
		final Member member = Member.createNormal().estimationDeviation(0);

		final Task task = new Task().complexity(Complexity.LOW);

		// Act + Assert

		// on default low tasks should be 2h

		for (int i = 0; i < 10; i++) {
			final double estimation = this.generator.estimateTaskFromMember(task, member);

			Assert.assertEquals(2, estimation, DELTA);
		}
	}

	@Test
	public void testEstimateTaskFromMemberLow() throws Exception {
		// Arrange
		final Member member = Member.createNormal().estimationDeviation(0.2);

		final Task task = new Task().complexity(Complexity.LOW);

		// Act + Assert

		// on default low tasks should be around 2h with 20% deviation

		for (int i = 0; i < 10; i++) {
			final double estimation = this.generator.estimateTaskFromMember(task, member);

			Assert.assertTrue("Estimation #" + i + " too low: " + estimation, estimation >= 1.6);
			Assert.assertTrue("Estimation #" + i + " too high: " + estimation, estimation <= 2.4);
		}
	}

	@Test
	public void testEstimateTaskFromMemberHighNoDeviation() throws Exception {
		// Arrange
		final Member member = Member.createNormal().estimationDeviation(0);

		final Task task = new Task().complexity(Complexity.HIGH);

		this.generator.getTaskConfig().setHighComplexityHours(10);

		// Act + Assert

		// high tasks should be 10h

		for (int i = 0; i < 10; i++) {
			final double estimation = this.generator.estimateTaskFromMember(task, member);

			Assert.assertEquals(10, estimation, DELTA);
		}
	}

	@Test
	public void testEstimateTaskFromMemberHigh() throws Exception {
		// Arrange
		final Member member = Member.createNormal().estimationDeviation(0.1);

		final Task task = new Task().complexity(Complexity.HIGH);

		this.generator.getTaskConfig().setHighComplexityHours(10);

		// Act + Assert

		// high tasks should be around 10h with 10% deviation

		for (int i = 0; i < 10; i++) {
			final double estimation = this.generator.estimateTaskFromMember(task, member);

			Assert.assertTrue("Estimation #" + i + " too low: " + estimation, estimation >= 9.0);
			Assert.assertTrue("Estimation #" + i + " too high: " + estimation, estimation <= 11.0);
		}
	}
}
