package de.slothsoft.sprintsim.io;

import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import de.slothsoft.sprintsim.io.CellFormat.Alignment;

@RunWith(Parameterized.class)
public class CellFormatTest {

	@Parameters(name = "{2}")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][]{

				{new CellFormat(Locale.ENGLISH), null, "", Alignment.BEGINNING},

				{new CellFormat(Locale.ENGLISH), "string", "string", Alignment.BEGINNING},

				{new CellFormat(Locale.ENGLISH), Alignment.CENTER, "CENTER", Alignment.BEGINNING},

				{new CellFormat(Locale.ENGLISH), Integer.valueOf(42), "42", Alignment.END},

				{new CellFormat(Locale.ENGLISH), Double.valueOf(1.2), "1.2", Alignment.END},

		});
	}

	private final CellFormat cellFormat;
	private final Object value;
	private final String expectedString;
	private final Alignment expectedAlignment;

	public CellFormatTest(CellFormat cellFormat, Object value, String expectedString, Alignment expectedAlignment) {
		this.cellFormat = cellFormat;
		this.value = value;
		this.expectedString = expectedString;
		this.expectedAlignment = expectedAlignment;
	}

	@Test
	public void testStringify() throws Exception {
		Assert.assertEquals(this.expectedString, this.cellFormat.stringify(this.value));
	}

	@Test
	public void testGetAlignment() throws Exception {
		Assert.assertEquals(this.expectedAlignment, this.cellFormat.getAlignment(this.value));
	}

}