package de.slothsoft.sprintsim;

import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;

public class MemberTest {

	private final Member member = new Member();

	@Test
	public void testAddUserData() throws Exception {
		final String key = UUID.randomUUID().toString();
		final String value = UUID.randomUUID().toString();

		this.member.addUserData(key, value);

		Assert.assertEquals(value, this.member.getUserData(key));
	}

	@Test
	public void testRemoveUserData() throws Exception {
		final String key = UUID.randomUUID().toString();
		final String value = UUID.randomUUID().toString();

		this.member.addUserData(key, value);
		this.member.removeUserData(key);

		Assert.assertEquals(null, this.member.getUserData(key));
	}

	@Test
	public void testGetUserData() throws Exception {
		final String key = UUID.randomUUID().toString();

		Assert.assertEquals(null, this.member.getUserData(key));
	}
}
