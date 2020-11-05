package de.slothsoft.sprintsim.app.config;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

final class Messages {
	private static final String BUNDLE_NAME = "de.slothsoft.sprintsim.app.config.messages"; //$NON-NLS-1$

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

	private Messages() {
		// noop
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (final MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
