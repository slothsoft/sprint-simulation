package de.slothsoft.sprintsim.generation;

import java.util.Objects;

import de.slothsoft.sprintsim.Sprint;

public class SprintPlanning {

	final Sprint sprint;
	final double estimatedHours;

	SprintPlanning(Sprint sprint, double estimatedHours) {
		this.sprint = Objects.requireNonNull(sprint);
		this.estimatedHours = estimatedHours;
	}

	public Sprint getSprint() {
		return this.sprint;
	}

	public double getEstimatedHours() {
		return this.estimatedHours;
	}

}
