package de.slothsoft.sprintsim.io;

public interface Logger {

	default void logTitle(String string) {
		log(string);
		log(string.replaceAll(".", "="));
		logEmpty();
	}

	default void logEmpty() {
		log("");
	}

	void log(String string);
}
