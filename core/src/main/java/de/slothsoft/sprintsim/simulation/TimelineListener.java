package de.slothsoft.sprintsim.simulation;

import java.util.EventListener;

public interface TimelineListener extends EventListener {

	void eventHappened(TimelineEvent event);
}
