package de.slothsoft.sprintsim.io;

import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;

public class HtmlLoggerTest {

	private final HtmlLogger logger = new HtmlLogger();

	@Test
	public void testClear() throws Exception {
		this.logger.log(UUID.randomUUID().toString());
		this.logger.clear();

		Assert.assertEquals("", this.logger.getHtml());
	}

	@Test
	public void testLog() throws Exception {
		final String value = UUID.randomUUID().toString();
		this.logger.log(value);

		Assert.assertEquals("<p>" + value + "</p>\n", this.logger.getHtml());
	}

	@Test
	public void testLogTitle() throws Exception {
		final String value = UUID.randomUUID().toString();
		this.logger.logTitle(value);

		Assert.assertEquals("<h1>" + value + "</h1>\n", this.logger.getHtml());
	}

	@Test
	public void testLogEmpty() throws Exception {
		this.logger.logEmpty();

		Assert.assertEquals("<br/>\n", this.logger.getHtml());
	}
}
