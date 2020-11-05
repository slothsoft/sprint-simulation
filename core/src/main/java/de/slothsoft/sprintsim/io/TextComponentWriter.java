package de.slothsoft.sprintsim.io;

import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import de.slothsoft.sprintsim.io.CellFormat.Alignment;

public class TextComponentWriter implements ComponentWriter {

	private Logger logger;

	private int columnSize = 15;
	private TableInfo tableInfo;
	private String emptyColumn = createEmptyColumn(this.columnSize);

	private final CellFormat cellFormat = new CellFormat(Locale.ENGLISH);

	public TextComponentWriter() {
		this(System.out::println);
	}

	public TextComponentWriter(Logger logger) {
		this.logger = Objects.requireNonNull(logger);
	}

	@Override
	public void writeTitle(String string) {
		writeLine(string);
		writeLine(string.replaceAll(".", "=")); //$NON-NLS-1$ //$NON-NLS-2$
		writeEmpty();
	}

	@Override
	public void writeLine(String string) {
		this.logger.log(string);
	}

	@Override
	public void startTable(TableInfo newInfo) {
		this.tableInfo = newInfo;
		this.emptyColumn = createEmptyColumn(this.columnSize);
	}

	@Override
	public void endTable() {
		this.tableInfo = null;
		this.emptyColumn = createEmptyColumn(this.columnSize);
	}

	@Override
	public void writeTableHeader(String... headers) {
		final StringBuilder headersString = new StringBuilder();
		for (int i = 0; i < headers.length; i++) {
			headersString.append(createTableCenterAligned(i, headers[i]));
		}

		this.logger.log(headersString.toString());
		writeTableSeparatorLine(headers.length);
	}

	String createTableCenterAligned(int index, String value) {
		if (value == null) return this.emptyColumn;
		final int indentWidth = Math.max(0, calculateColumnSize(index) - value.length());
		return this.emptyColumn.substring(0, indentWidth / 2) + value
				+ this.emptyColumn.substring(0, indentWidth - indentWidth / 2);
	}

	@Override
	public void writeTableSeparatorLine(int columnCount) {
		final String separatorLine = IntStream.range(0, columnCount)
				.mapToObj(i -> this.emptyColumn.substring(0, calculateColumnSize(i))).collect(Collectors.joining());
		this.logger.log(separatorLine.replaceAll(".", "="));
	}

	@Override
	public void writeTableLine(Object... cells) {
		final StringBuilder cellsString = new StringBuilder();
		for (int i = 0; i < cells.length; i++) {
			cellsString.append(createCellString(i, cells[i]));
		}
		this.logger.log(cellsString.toString());
	}

	String createCellString(int index, Object cell) {
		final Alignment aligment = this.cellFormat.getAlignment(cell);

		switch (aligment) {
			case BEGINNING :
				return createTableLeftAligned(index, this.cellFormat.stringify(cell));
			case CENTER :
				return createTableCenterAligned(index, this.cellFormat.stringify(cell));
			case END :
				return createTableRightAligned(index, this.cellFormat.stringify(cell));
			default :
				throw new IllegalArgumentException("Do not know alignment " + aligment);
		}
	}

	String createTableLeftAligned(int index, String value) {
		return value + this.emptyColumn.substring(0, Math.max(0, calculateColumnSize(index) - value.length()));
	}

	private int calculateColumnSize(int index) {
		final double ratio = this.tableInfo == null || this.tableInfo.columnRatios == null
				? 1.0
				: this.tableInfo.columnRatios[index];
		return (int) Math.round(this.columnSize * ratio);
	}

	String createTableRightAligned(int index, String value) {
		return this.emptyColumn.substring(0, Math.max(0, calculateColumnSize(index) - value.length())) + value;
	}

	public int getColumnSize() {
		return this.columnSize;
	}

	public TextComponentWriter columnSize(int newColumnSize) {
		setColumnSize(newColumnSize);
		return this;
	}

	public void setColumnSize(int columnSize) {
		this.columnSize = columnSize;
		this.emptyColumn = createEmptyColumn(columnSize);
	}

	private String createEmptyColumn(int targetSize) {
		final double maxRatio = this.tableInfo == null || this.tableInfo.columnRatios == null
				? 1.0
				: Arrays.stream(this.tableInfo.columnRatios).max().getAsDouble();
		return IntStream.range(0, (int) Math.round(targetSize * maxRatio)).mapToObj(i -> " ") //$NON-NLS-1$
				.collect(Collectors.joining());
	}

	public Logger getLogger() {
		return this.logger;
	}

	public TextComponentWriter logger(Logger newLogger) {
		setLogger(newLogger);
		return this;
	}

	public void setLogger(Logger logger) {
		this.logger = Objects.requireNonNull(logger);
	}
}
