package de.slothsoft.sprintsim.io;

import org.junit.Assert;
import org.junit.Test;

public class LogTableWriterTest {

	private final LogTableWriter tableWriter = new LogTableWriter();

	@Test
	public void testCreateTableCenterAligned() throws Exception {
		final String cell = this.tableWriter.createTableCenterAligned("Hi :)");

		Assert.assertEquals("     Hi :)     ", cell);
	}

	@Test
	public void testCreateTableCenterAlignedWithColumnSize() throws Exception {
		this.tableWriter.setColumnSize(10);
		final String cell = this.tableWriter.createTableCenterAligned("Hello");

		Assert.assertEquals("  Hello   ", cell);
	}

	@Test
	public void testCreateTableCenterAlignedTooLong() throws Exception {
		final String cell = this.tableWriter.createTableCenterAligned("12345678901234567890");

		Assert.assertEquals("12345678901234567890", cell);
	}

	@Test
	public void testCreateTableLeftAligned() throws Exception {
		final String cell = this.tableWriter.createTableLeftAligned("Left");

		Assert.assertEquals("Left           ", cell);
	}

	@Test
	public void testCreateTableLeftAlignedWithColumnSize() throws Exception {
		this.tableWriter.setColumnSize(10);
		final String cell = this.tableWriter.createTableLeftAligned("left");

		Assert.assertEquals("left      ", cell);
	}

	@Test
	public void testCreateTableLeftAlignedTooLong() throws Exception {
		final String cell = this.tableWriter.createTableLeftAligned("12345678901234567890");

		Assert.assertEquals("12345678901234567890", cell);
	}

	@Test
	public void testCreateTableRightAligned() throws Exception {
		final String cell = this.tableWriter.createTableRightAligned("Right");

		Assert.assertEquals("          Right", cell);
	}

	@Test
	public void testCreateTableRightAlignedWithColumnSize() throws Exception {
		this.tableWriter.setColumnSize(10);
		final String cell = this.tableWriter.createTableRightAligned("right");

		Assert.assertEquals("     right", cell);
	}

	@Test
	public void testCreateTableRightAlignedTooLong() throws Exception {
		final String cell = this.tableWriter.createTableRightAligned("12345678901234567890");

		Assert.assertEquals("12345678901234567890", cell);
	}

	@Test
	public void testWriteHeaders() throws Exception {
		final StringBuilder sb = new StringBuilder();
		this.tableWriter.setLogger(s -> sb.append(s).append('\n'));

		this.tableWriter.writeHeader("col1", "col2", "col3");

		Assert.assertEquals(
				"     col1           col2           col3      \n=============================================\n",
				sb.toString());
	}

	@Test
	public void testWriteHeadersWithColumnSize() throws Exception {
		final StringBuilder sb = new StringBuilder();
		this.tableWriter.setLogger(s -> sb.append(s).append('\n'));
		this.tableWriter.setColumnSize(10);

		this.tableWriter.writeHeader("col1", "col2", "col3");

		Assert.assertEquals("   col1      col2      col3   \n==============================\n", sb.toString());
	}

	@Test
	public void testWriteColumns() throws Exception {
		final StringBuilder sb = new StringBuilder();
		this.tableWriter.setLogger(sb::append);

		this.tableWriter.writeLine("string", Integer.valueOf(42), Double.valueOf(1.2));

		Assert.assertEquals("string         " + "             42" + "            1.2", sb.toString());
	}

	@Test
	public void testWriteColumnsWithColumnSize() throws Exception {
		final StringBuilder sb = new StringBuilder();
		this.tableWriter.setLogger(sb::append);
		this.tableWriter.setColumnSize(10);

		this.tableWriter.writeLine("string", Integer.valueOf(42), Double.valueOf(1.2));

		Assert.assertEquals("string    " + "        42" + "       1.2", sb.toString());
	}
}
