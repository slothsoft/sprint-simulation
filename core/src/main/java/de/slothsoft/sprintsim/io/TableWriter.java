package de.slothsoft.sprintsim.io;

public interface TableWriter {

	void writeHeader(String... headers);

	void writeLine(Object... cells);
}
