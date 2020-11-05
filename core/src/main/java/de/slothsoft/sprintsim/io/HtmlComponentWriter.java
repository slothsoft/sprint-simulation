package de.slothsoft.sprintsim.io;

import java.util.Locale;

public class HtmlComponentWriter implements ComponentWriter {

	private static final char LINE_BREAK = '\n';

	private final CellFormat cellFormat = new CellFormat(Locale.ENGLISH);
	private final StringBuilder html = new StringBuilder();

	private boolean nextTableLineIsHeader;

	@Override
	public void writeTitle(String string) {
		this.html.append("<h1>").append(string).append("</h1>").append(LINE_BREAK);
	}

	@Override
	public void writeLine(String string) {
		this.html.append("<p>").append(string).append("</p>").append(LINE_BREAK);
	}

	@Override
	public void writeEmpty() {
		this.html.append("<br/>").append(LINE_BREAK);
	}

	public void clear() {
		this.html.setLength(0);
	}

	public String getHtml() {
		return this.html.toString();
	}

	@Override
	public void startTable(TableInfo info) {
		this.html.append("<table>").append(LINE_BREAK);
	}

	@Override
	public void writeTableHeader(String... headers) {
		doWriteTableHeader((Object[]) headers);
	}

	private void doWriteTableHeader(Object... values) {
		this.html.append("<tr>");
		for (final Object value : values) {
			this.html.append("<th>").append(this.cellFormat.stringify(value)).append("</th>");
		}
		this.html.append("</tr>").append(LINE_BREAK);
	}

	@Override
	public void writeTableSeparatorLine(int columnCount) {
		this.nextTableLineIsHeader = true;
	}

	@Override
	public void writeTableLine(Object... cells) {
		if (this.nextTableLineIsHeader) {
			doWriteTableHeader(cells);
			this.nextTableLineIsHeader = false;
			return;
		}

		this.html.append("<tr>");
		for (final Object cell : cells) {
			this.html.append("<td>").append(this.cellFormat.stringify(cell)).append("</td>");
		}
		this.html.append("</tr>").append(LINE_BREAK);
	}

	@Override
	public void endTable() {
		this.html.append("</table>").append(LINE_BREAK);
	}

}
