package de.slothsoft.sprintsim.io;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LogTableWriter implements TableWriter {

	private Logger logger;

	private int columnSize = 15;
	private String emptyColumn = createEmptyColumn(this.columnSize);

	private final NumberFormat numberFormat = NumberFormat.getInstance(Locale.ENGLISH);

	public LogTableWriter() {
		this(System.out::println);
	}

	public LogTableWriter(Logger logger) {
		this.logger = Objects.requireNonNull(logger);
	}

	@Override
	public void writeHeader(String... headers) {
		final String headersString = Arrays.stream(headers).map(this::createTableCenterAligned)
				.collect(Collectors.joining());
		this.logger.log(headersString);
		writeSeparatorLine(headers.length);
	}

	String createTableCenterAligned(String value) {
		if (value == null) return this.emptyColumn;
		final int indentWidth = Math.max(0, this.columnSize - value.length());
		return this.emptyColumn.substring(0, indentWidth / 2) + value
				+ this.emptyColumn.substring(0, indentWidth - indentWidth / 2);
	}

	@Override
	public void writeSeparatorLine(int columnCount) {
		final String separatorLine = IntStream.range(0, columnCount * this.columnSize).mapToObj(i -> "=")
				.collect(Collectors.joining());
		this.logger.log(separatorLine);
	}

	@Override
	public void writeLine(Object... cells) {
		final String cellsString = Arrays.stream(cells).map(this::createCellString).collect(Collectors.joining());
		this.logger.log(cellsString);
	}

	String createCellString(Object cell) {
		if (cell == null) return this.emptyColumn;
		if (cell instanceof String) return createTableLeftAligned((String) cell);
		if (cell instanceof Number) return createTableRightAligned(this.numberFormat.format(cell));
		return createTableLeftAligned(cell.toString());
	}

	String createTableLeftAligned(String value) {
		return value + this.emptyColumn.substring(0, Math.max(0, this.columnSize - value.length()));
	}

	String createTableRightAligned(String value) {
		return this.emptyColumn.substring(0, Math.max(0, this.columnSize - value.length())) + value;
	}

	public int getColumnSize() {
		return this.columnSize;
	}

	public LogTableWriter columnSize(int newColumnSize) {
		setColumnSize(newColumnSize);
		return this;
	}

	public void setColumnSize(int columnSize) {
		this.columnSize = columnSize;
		this.emptyColumn = createEmptyColumn(columnSize);
	}

	private static String createEmptyColumn(int targetSize) {
		return IntStream.range(0, targetSize).mapToObj(i -> " ").collect(Collectors.joining()); //$NON-NLS-1$
	}

	public Logger getLogger() {
		return this.logger;
	}

	public LogTableWriter logger(Logger newLogger) {
		setLogger(newLogger);
		return this;
	}

	public void setLogger(Logger logger) {
		this.logger = Objects.requireNonNull(logger);
	}
}
