package de.slothsoft.sprintsim.io;

public interface TableWriter {

	void writeHeader(String... headers);

	void writeSeparatorLine(int columnCount);

	void writeLine(Object... cells);
}
