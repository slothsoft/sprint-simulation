package de.slothsoft.sprintsim;

import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;

public class TaskTest {

	private final Task task = new Task();

	@Test
	public void testAddUserData() throws Exception {
		final String key = UUID.randomUUID().toString();
		final String value = UUID.randomUUID().toString();

		this.task.addUserData(key, value);

		Assert.assertEquals(value, this.task.getUserData(key));
	}

	@Test
	public void testRemoveUserData() throws Exception {
		final String key = UUID.randomUUID().toString();
		final String value = UUID.randomUUID().toString();

		this.task.addUserData(key, value);
		this.task.removeUserData(key);

		Assert.assertEquals(null, this.task.getUserData(key));
	}

	@Test
	public void testGetUserData() throws Exception {
		final String key = UUID.randomUUID().toString();

		Assert.assertEquals(null, this.task.getUserData(key));
	}
}
