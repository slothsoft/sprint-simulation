package de.slothsoft.sprintsim.io;

public interface ComponentWriter {

	class TableInfo {

		double[] columnRatios;

		public double[] getColumnRatios() {
			return this.columnRatios;
		}

		public TableInfo columnRatios(double... newColumnRatios) {
			setColumnRatios(newColumnRatios);
			return this;
		}

		public void setColumnRatios(double... columnRatios) {
			this.columnRatios = columnRatios;
		}
	}

	void startTable(TableInfo info);

	void writeTableHeader(String... headers);

	void writeTableSeparatorLine(int columnCount);

	void writeTableLine(Object... cells);

	void endTable();

	void writeTitle(String string);

	default void writeEmpty() {
		writeLine(""); //$NON-NLS-1$
	}

	void writeLine(String string);
}
