package de.slothsoft.sprintsim.simulation;

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
