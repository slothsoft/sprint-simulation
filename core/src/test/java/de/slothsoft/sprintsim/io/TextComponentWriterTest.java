package de.slothsoft.sprintsim.io;

import org.junit.Assert;
import org.junit.Test;

public class TextComponentWriterTest {

	private final TextComponentWriter componentWriter = new TextComponentWriter();

	@Test
	public void testCreateTableCenterAligned() throws Exception {
		final String cell = this.componentWriter.createTableCenterAligned(0, "Hi :)");

		Assert.assertEquals("     Hi :)     ", cell);
	}

	@Test
	public void testCreateTableCenterAlignedWithColumnSize() throws Exception {
		this.componentWriter.setColumnSize(10);
		final String cell = this.componentWriter.createTableCenterAligned(1, "Hello");

		Assert.assertEquals("  Hello   ", cell);
	}

	@Test
	public void testCreateTableCenterAlignedTooLong() throws Exception {
		final String cell = this.componentWriter.createTableCenterAligned(2, "12345678901234567890");

		Assert.assertEquals("12345678901234567890", cell);
	}

	@Test
	public void testCreateTableLeftAligned() throws Exception {
		final String cell = this.componentWriter.createTableLeftAligned(0, "Left");

		Assert.assertEquals("Left           ", cell);
	}

	@Test
	public void testCreateTableLeftAlignedWithColumnSize() throws Exception {
		this.componentWriter.setColumnSize(10);
		final String cell = this.componentWriter.createTableLeftAligned(0, "left");

		Assert.assertEquals("left      ", cell);
	}

	@Test
	public void testCreateTableLeftAlignedTooLong() throws Exception {
		final String cell = this.componentWriter.createTableLeftAligned(0, "12345678901234567890");

		Assert.assertEquals("12345678901234567890", cell);
	}

	@Test
	public void testCreateTableRightAligned() throws Exception {
		final String cell = this.componentWriter.createTableRightAligned(0, "Right");

		Assert.assertEquals("          Right", cell);
	}

	@Test
	public void testCreateTableRightAlignedWithColumnSize() throws Exception {
		this.componentWriter.setColumnSize(10);
		final String cell = this.componentWriter.createTableRightAligned(3, "right");

		Assert.assertEquals("     right", cell);
	}

	@Test
	public void testCreateTableRightAlignedTooLong() throws Exception {
		final String cell = this.componentWriter.createTableRightAligned(-1, "12345678901234567890");

		Assert.assertEquals("12345678901234567890", cell);
	}

	@Test
	public void testWriteHeaders() throws Exception {
		final StringBuilder sb = new StringBuilder();
		this.componentWriter.setLogger(s -> sb.append(s).append('\n'));

		this.componentWriter.writeTableHeader("col1", "col2", "col3");

		Assert.assertEquals(
				"     col1           col2           col3      \n=============================================\n",
				sb.toString());
	}

	@Test
	public void testWriteHeadersWithColumnSize() throws Exception {
		final StringBuilder sb = new StringBuilder();
		this.componentWriter.setLogger(s -> sb.append(s).append('\n'));
		this.componentWriter.setColumnSize(10);

		this.componentWriter.writeTableHeader("col1", "col2", "col3");

		Assert.assertEquals("   col1      col2      col3   \n==============================\n", sb.toString());
	}

	@Test
	public void testWriteColumns() throws Exception {
		final StringBuilder sb = new StringBuilder();
		this.componentWriter.setLogger(sb::append);

		this.componentWriter.writeTableLine("string", Integer.valueOf(42), Double.valueOf(1.2));

		Assert.assertEquals("string         " + "             42" + "            1.2", sb.toString());
	}

	@Test
	public void testWriteColumnsWithColumnSize() throws Exception {
		final StringBuilder sb = new StringBuilder();
		this.componentWriter.setLogger(sb::append);
		this.componentWriter.setColumnSize(10);

		this.componentWriter.writeTableLine("string", Integer.valueOf(42), Double.valueOf(1.2));

		Assert.assertEquals("string    " + "        42" + "       1.2", sb.toString());
	}
}
