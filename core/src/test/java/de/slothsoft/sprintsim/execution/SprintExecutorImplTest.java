package de.slothsoft.sprintsim.execution;

import org.junit.Assert;
import org.junit.Test;

import de.slothsoft.sprintsim.Member;
import de.slothsoft.sprintsim.config.TaskConfig;

/**
 * "Impl" tests check implementation specific methods.
 *
 * @author Stef Schulz
 */

public class SprintExecutorImplTest {

	private static final double DELTA = 0.001;

	private final SprintExecutor executor = new SprintExecutor();

	@Test
	public void testSetMembers() throws Exception {
		final Member[] members = {Member.createNormal(), Member.createSenior()};
		this.executor.setMembers(members);
		Assert.assertArrayEquals(members, this.executor.getMembers());
	}

	@Test
	public void testMembers() throws Exception {
		final Member[] members = {Member.createNormal(), Member.createSenior()};
		this.executor.members(members);
		Assert.assertArrayEquals(members, this.executor.getMembers());
	}

	@Test
	public void testSetTaskConfig() throws Exception {
		final TaskConfig taskConfig = new TaskConfig();
		this.executor.setTaskConfig(taskConfig);
		Assert.assertSame(taskConfig, this.executor.getTaskConfig());
	}

	@Test
	public void testTaskConfig() throws Exception {
		final TaskConfig taskConfig = new TaskConfig();
		this.executor.taskConfig(taskConfig);
		Assert.assertSame(taskConfig, this.executor.getTaskConfig());
	}

	@Test
	public void testGenerateWorkHoursNone() throws Exception {
		// Assert
		final double[] workHours = this.executor.generateWorkHours(10);
		Assert.assertNotNull(workHours);
		Assert.assertArrayEquals(new double[0], workHours, DELTA);
	}

	@Test
	public void testGenerateWorkHoursSingle() throws Exception {
		// Arrange
		this.executor.setMembers(Member.createNormal());

		// Act
		final double[] workHours = this.executor.generateWorkHours(10);

		// Assert
		Assert.assertNotNull(workHours);
		Assert.assertArrayEquals(new double[]{80}, workHours, DELTA);
	}

	@Test
	public void testGenerateWorkHoursMultiple() throws Exception {
		// Arrange
		final Member junior = Member.createJunior().workHoursPerDay(6);
		final Member normal = Member.createNormal().workHoursPerDay(7);
		final Member senior = Member.createSenior().workHoursPerDay(8);

		this.executor.setMembers(junior, normal, senior);

		// Act
		final double[] workHours = this.executor.generateWorkHours(5);

		// Assert
		Assert.assertNotNull(workHours);
		Assert.assertArrayEquals(new double[]{30, 35, 40}, workHours, DELTA);
	}
}
