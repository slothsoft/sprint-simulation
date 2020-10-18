package de.slothsoft.sprintsim.simulation;

import org.junit.Assert;
import org.junit.Test;

public class StorySimulationListenerTest {

	StorySimulationListener listener = new StorySimulationListener();

	@Test
	public void testCreateMemberName() throws Exception {
		Assert.assertEquals("Angie", StorySimulationListener.createMemberName(0));
		Assert.assertEquals("Steffi", StorySimulationListener.createMemberName(18));
		Assert.assertEquals("David 2", StorySimulationListener.createMemberName(29));
	}

	@Test
	public void testCreateTaskName() throws Exception {
		Assert.assertEquals("LIO-31415", this.listener.createTaskName(0));
		Assert.assertEquals("LIO-31416", this.listener.createTaskName(1));
		Assert.assertEquals("LIO-31417", this.listener.createTaskName(2));
	}

	@Test
	public void testCreateTaskNameWithTaskIdPrefix() throws Exception {
		this.listener.setTaskIdPrefix("TASK");

		Assert.assertEquals("TASK-31415", this.listener.createTaskName(0));
		Assert.assertEquals("TASK-31416", this.listener.createTaskName(1));
		Assert.assertEquals("TASK-31417", this.listener.createTaskName(2));
	}

}
