package de.slothsoft.sprintsim.io;

public class HtmlLogger implements Logger {

	private static final char LINE_BREAK = '\n';

	private final StringBuilder html = new StringBuilder();

	@Override
	public void logTitle(String string) {
		this.html.append("<h1>").append(string).append("</h1>").append(LINE_BREAK);
	}

	@Override
	public void log(String string) {
		this.html.append("<p>").append(string).append("</p>").append(LINE_BREAK);
	}

	@Override
	public void logEmpty() {
		this.html.append("<br/>").append(LINE_BREAK);
	}

	public void clear() {
		this.html.setLength(0);
	}

	public String getHtml() {
		return this.html.toString();
	}

}
