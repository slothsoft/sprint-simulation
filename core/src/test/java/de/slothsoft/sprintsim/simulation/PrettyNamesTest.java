package de.slothsoft.sprintsim.simulation;

import org.junit.Assert;
import org.junit.Test;

import de.slothsoft.sprintsim.simulation.PrettyNames.TaskNamingInfo;

public class PrettyNamesTest {

	@Test
	public void testCreateMemberName() throws Exception {
		Assert.assertEquals("Angie", PrettyNames.createMemberName(0));
		Assert.assertEquals("Steffi", PrettyNames.createMemberName(18));
		Assert.assertEquals("David 2", PrettyNames.createMemberName(29));
	}

	@Test
	public void testCreateTaskName() throws Exception {
		final TaskNamingInfo info = new TaskNamingInfo();

		Assert.assertEquals("LIO-31415", PrettyNames.createTaskName(info, 0));
		Assert.assertEquals("LIO-31416", PrettyNames.createTaskName(info, 1));
		Assert.assertEquals("LIO-31417", PrettyNames.createTaskName(info, 2));
	}

	@Test
	public void testCreateTaskNameWithTaskIdPrefix() throws Exception {
		final TaskNamingInfo info = new TaskNamingInfo().taskIdPrefix("TASK");

		Assert.assertEquals("TASK-31415", PrettyNames.createTaskName(info, 0));
		Assert.assertEquals("TASK-31416", PrettyNames.createTaskName(info, 1));
		Assert.assertEquals("TASK-31417", PrettyNames.createTaskName(info, 2));
	}

	@Test
	public void testCreateTaskNameWithFirstIndex() throws Exception {
		final TaskNamingInfo info = new TaskNamingInfo().firstIndex(10);

		Assert.assertEquals("LIO-31425", PrettyNames.createTaskName(info, 0));
		Assert.assertEquals("LIO-31426", PrettyNames.createTaskName(info, 1));
		Assert.assertEquals("LIO-31427", PrettyNames.createTaskName(info, 2));
	}

}
