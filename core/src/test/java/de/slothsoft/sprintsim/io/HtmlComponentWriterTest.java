package de.slothsoft.sprintsim.io;

import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;

import de.slothsoft.sprintsim.io.ComponentWriter.TableInfo;

public class HtmlComponentWriterTest {

	private final HtmlComponentWriter writer = new HtmlComponentWriter();

	@Test
	public void testClear() throws Exception {
		this.writer.writeLine(UUID.randomUUID().toString());
		this.writer.clear();

		Assert.assertEquals("", this.writer.getHtml());
	}

	@Test
	public void testWriteLine() throws Exception {
		final String value = UUID.randomUUID().toString();
		this.writer.writeLine(value);

		Assert.assertEquals("<p>" + value + "</p>\n", this.writer.getHtml());
	}

	@Test
	public void testWriteTitle() throws Exception {
		final String value = UUID.randomUUID().toString();
		this.writer.writeTitle(value);

		Assert.assertEquals("<h1>" + value + "</h1>\n", this.writer.getHtml());
	}

	@Test
	public void testWriteEmpty() throws Exception {
		this.writer.writeEmpty();

		Assert.assertEquals("<br/>\n", this.writer.getHtml());
	}

	@Test
	public void testStartTable() throws Exception {
		this.writer.startTable(new TableInfo());

		Assert.assertEquals("<table>\n", this.writer.getHtml());
	}

	@Test
	public void testEndTable() throws Exception {
		this.writer.endTable();

		Assert.assertEquals("</table>\n", this.writer.getHtml());
	}

	@Test
	public void testWriteTableHeader() throws Exception {
		this.writer.writeTableHeader("A", "B", "C");

		Assert.assertEquals("<tr><th>A</th><th>B</th><th>C</th></tr>\n", this.writer.getHtml());
	}

	@Test
	public void testWriteTableHeaderEmpty() throws Exception {
		this.writer.writeTableHeader();

		Assert.assertEquals("<tr></tr>\n", this.writer.getHtml());
	}

	@Test
	public void testWriteTableLine() throws Exception {
		this.writer.writeTableLine("string", Integer.valueOf(42), Double.valueOf(1.2));

		Assert.assertEquals("<tr><td>string</td><td>42</td><td>1.2</td></tr>\n", this.writer.getHtml());
	}

	@Test
	public void testWriteTableLineEmpty() throws Exception {
		this.writer.writeTableLine();

		Assert.assertEquals("<tr></tr>\n", this.writer.getHtml());
	}

	@Test
	public void testWriteTableSeparatorLine() throws Exception {
		this.writer.writeTableSeparatorLine(3);
		this.writer.writeTableLine("string", Integer.valueOf(42), Double.valueOf(1.2));

		Assert.assertEquals("<tr><th>string</th><th>42</th><th>1.2</th></tr>\n", this.writer.getHtml());
	}
}
