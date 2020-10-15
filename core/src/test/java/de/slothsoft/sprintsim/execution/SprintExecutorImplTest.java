package de.slothsoft.sprintsim.execution;

import org.junit.Assert;
import org.junit.Test;

import de.slothsoft.sprintsim.Member;

/**
 * "Impl" tests check implementation specific methods.
 *
 * @author Stef Schulz
 */

public class SprintExecutorImplTest {

	private static final double DELTA = 0.001;

	@Test
	public void testGenerateWorkHoursNone() throws Exception {
		// Act
		final SprintExecutor executor = new SprintExecutor();

		// Assert
		final double[] workHours = executor.generateWorkHours(10);
		Assert.assertNotNull(workHours);
		Assert.assertArrayEquals(new double[0], workHours, DELTA);
	}

	@Test
	public void testGenerateWorkHoursSingle() throws Exception {
		// Arrange
		final Member member = Member.createNormal();

		// Act
		final SprintExecutor executor = new SprintExecutor(member);

		// Assert
		final double[] workHours = executor.generateWorkHours(10);
		Assert.assertNotNull(workHours);
		Assert.assertArrayEquals(new double[]{80}, workHours, DELTA);
	}

	@Test
	public void testGenerateWorkHoursMultiple() throws Exception {
		// Arrange
		final Member junior = Member.createJunior().workHoursPerDay(6);
		final Member normal = Member.createNormal().workHoursPerDay(7);
		final Member senior = Member.createSenior().workHoursPerDay(8);

		// Act
		final SprintExecutor executor = new SprintExecutor(junior, normal, senior);

		// Assert
		final double[] workHours = executor.generateWorkHours(5);
		Assert.assertNotNull(workHours);
		Assert.assertArrayEquals(new double[]{30, 35, 40}, workHours, DELTA);
	}
}
