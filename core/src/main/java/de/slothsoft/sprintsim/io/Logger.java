package de.slothsoft.sprintsim.io;

public interface Logger {

	default void logTitle(String string) {
		log(string);
		log(string.replaceAll(".", "=")); //$NON-NLS-1$ //$NON-NLS-2$
		logEmpty();
	}

	default void logEmpty() {
		log(""); //$NON-NLS-1$
	}

	void log(String string);
}
