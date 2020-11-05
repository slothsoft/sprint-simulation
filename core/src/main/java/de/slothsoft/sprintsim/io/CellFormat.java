package de.slothsoft.sprintsim.io;

import java.text.NumberFormat;
import java.util.Locale;

class CellFormat {

	enum Alignment {
		BEGINNING,

		CENTER,

		END;
	}

	private final NumberFormat numberFormat;

	public CellFormat(Locale locale) {
		this.numberFormat = NumberFormat.getInstance(locale);
	}

	public String stringify(Object cell) {
		if (cell == null) return "";
		if (cell instanceof String) return (String) cell;
		if (cell instanceof Number) return this.numberFormat.format(cell);
		return cell.toString();
	}

	public Alignment getAlignment(Object cell) {
		if (cell instanceof Number) return Alignment.END;
		return Alignment.BEGINNING;
	}
}
