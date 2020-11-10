package de.slothsoft.sprintsim.app;

import com.vaadin.flow.component.ClickNotifier;
import com.vaadin.flow.component.Component;

public interface HasRunButton<B extends Component & ClickNotifier<?>> {

	default boolean hasRunButton() {
		return true;
	}

	B createRunButton();

}
