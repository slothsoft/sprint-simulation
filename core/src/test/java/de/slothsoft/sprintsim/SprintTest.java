package de.slothsoft.sprintsim;

import org.junit.Assert;
import org.junit.Test;

public class SprintTest {

	private final Sprint sprint = new Sprint();

	@Test
	public void testSetTask() throws Exception {
		final Task[] task = {new Task()};
		this.sprint.setTasks(task);
		Assert.assertArrayEquals(task, this.sprint.getTasks());
	}

	@Test
	public void testTask() throws Exception {
		final Task[] task = {new Task()};
		this.sprint.tasks(task);
		Assert.assertArrayEquals(task, this.sprint.getTasks());
	}
}
