package de.slothsoft.sprintsim.impl;

import java.util.Iterator;
import java.util.Map.Entry;

import org.junit.Assert;
import org.junit.Test;

public class ArrayToArrayMapTest {

	@Test
	public void testSetValues() throws Exception {
		final ArrayToArrayMap<String, Integer> map = new ArrayToArrayMap<>("A", "B", "C");
		map.setValues(Integer.valueOf(1), Integer.valueOf(2), Integer.valueOf(3));

		Assert.assertEquals(Integer.valueOf(1), map.getValue("A"));
		Assert.assertEquals(Integer.valueOf(2), map.getValue("B"));
		Assert.assertEquals(Integer.valueOf(3), map.getValue("C"));
	}

	@Test
	public void testSetValuesIllegal() throws Exception {
		final ArrayToArrayMap<String, Integer> map = new ArrayToArrayMap<>("D", "E", "F");

		try {
			map.setValues(Integer.valueOf(1), Integer.valueOf(2));
			Assert.fail("There should have been an exception!");
		} catch (final Exception e) {
			Assert.assertEquals("Only 3 elements are allowed, but was: 2", e.getMessage());
		}
	}

	@Test
	public void testSetValuesIllegalOtherElementName() throws Exception {
		final ArrayToArrayMap<String, Integer> map = new ArrayToArrayMap<>("G", "H", "I", "J");
		map.setElementName("value");

		try {
			map.setValues(Integer.valueOf(1));
			Assert.fail("There should have been an exception!");
		} catch (final Exception e) {
			Assert.assertEquals("Only 4 values are allowed, but was: 1", e.getMessage());
		}
	}

	@Test
	public void testValues() throws Exception {
		final ArrayToArrayMap<String, Integer> map = new ArrayToArrayMap<>("A", "B", "C");
		map.values(Integer.valueOf(1), Integer.valueOf(2), Integer.valueOf(3));

		Assert.assertEquals(Integer.valueOf(1), map.getValue("A"));
		Assert.assertEquals(Integer.valueOf(2), map.getValue("B"));
		Assert.assertEquals(Integer.valueOf(3), map.getValue("C"));
	}

	@Test
	public void testValuesIllegal() throws Exception {
		final ArrayToArrayMap<String, Integer> map = new ArrayToArrayMap<>("D", "E", "F");

		try {
			map.values(Integer.valueOf(1), Integer.valueOf(2));
			Assert.fail("There should have been an exception!");
		} catch (final Exception e) {
			Assert.assertEquals("Only 3 elements are allowed, but was: 2", e.getMessage());
		}
	}

	@Test
	public void testValuesIllegalOtherElementName() throws Exception {
		final ArrayToArrayMap<String, Integer> map = new ArrayToArrayMap<>("G", "H", "I", "J");
		map.setElementName("value");

		try {
			map.values(Integer.valueOf(1));
			Assert.fail("There should have been an exception!");
		} catch (final Exception e) {
			Assert.assertEquals("Only 4 values are allowed, but was: 1", e.getMessage());
		}
	}

	@Test
	public void testGetValueNull() throws Exception {
		final ArrayToArrayMap<String, Integer> map = new ArrayToArrayMap<>("K", "L", "M");

		Assert.assertEquals(null, map.getValue("K"));
		Assert.assertEquals(null, map.getValue("L"));
		Assert.assertEquals(null, map.getValue("M"));
	}

	@Test
	public void testGetValueUnknown() throws Exception {
		final ArrayToArrayMap<String, Integer> map = new ArrayToArrayMap<>("N", "O", "P");

		try {
			map.getValue("Q");
			Assert.fail("There should have been an exception!");
		} catch (final Exception e) {
			Assert.assertEquals("A element with key Q could not be found!", e.getMessage());
		}
	}

	@Test
	public void testGetValueUnknownOtherElementName() throws Exception {
		final ArrayToArrayMap<String, Integer> map = new ArrayToArrayMap<>("N", "O", "P");
		map.setElementName("value");

		try {
			map.getValue("Q");
			Assert.fail("There should have been an exception!");
		} catch (final Exception e) {
			Assert.assertEquals("A value with key Q could not be found!", e.getMessage());
		}
	}

	@Test
	public void testIterator() throws Exception {
		final ArrayToArrayMap<String, Integer> map = new ArrayToArrayMap<>("R", "S", "T");
		map.setValues(Integer.valueOf(1), Integer.valueOf(2), Integer.valueOf(3));

		final Iterator<Entry<String, Integer>> it = map.iterator();

		Assert.assertTrue(it.hasNext());
		Entry<String, Integer> next = it.next();
		Assert.assertEquals("R", next.getKey());
		Assert.assertEquals(Integer.valueOf(1), next.getValue());

		Assert.assertTrue(it.hasNext());
		next = it.next();
		Assert.assertEquals("S", next.getKey());
		Assert.assertEquals(Integer.valueOf(2), next.getValue());

		Assert.assertTrue(it.hasNext());
		next = it.next();
		Assert.assertEquals("T", next.getKey());
		Assert.assertEquals(Integer.valueOf(3), next.getValue());

		Assert.assertFalse(it.hasNext());
	}

	@Test
	public void testGetKeys() throws Exception {
		final ArrayToArrayMap<String, Integer> map = new ArrayToArrayMap<>("U", "V", "W");

		Assert.assertArrayEquals(new String[]{"U", "V", "W"}, map.getKeys());
	}

	@Test
	public void testGetKeysEmpty() throws Exception {
		final ArrayToArrayMap<String, Integer> map = new ArrayToArrayMap<>();

		Assert.assertArrayEquals(new String[0], map.getKeys());
	}
}
