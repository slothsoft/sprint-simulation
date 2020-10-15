package de.slothsoft.sprintsim.config;

import org.junit.Assert;
import org.junit.Test;

import de.slothsoft.sprintsim.Complexity;
import de.slothsoft.sprintsim.config.TaskConfig;

public class TaskConfigTest {

	private final TaskConfig config = new TaskConfig();

	@Test
	public void testGetHoursHigh() throws Exception {
		this.config.highComplexityHours(1);

		Assert.assertEquals(1, this.config.getHours(Complexity.HIGH));
	}

	@Test
	public void testGetHoursMedium() throws Exception {
		this.config.mediumComplexityHours(1);

		Assert.assertEquals(1, this.config.getHours(Complexity.MEDIUM));
	}

	@Test
	public void testGetHoursLow() throws Exception {
		this.config.lowComplexityHours(1);

		Assert.assertEquals(1, this.config.getHours(Complexity.LOW));
	}
}
