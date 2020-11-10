package de.slothsoft.sprintsim.generation;

import java.util.Objects;

import de.slothsoft.sprintsim.Sprint;

public class SprintPlanning {

	final Sprint sprint;
	final double estimatedHours;
	final double estimatedAdditionalHours;

	public SprintPlanning(Sprint sprint, double estimatedHours, double estimatedAdditionalHours) {
		this.sprint = Objects.requireNonNull(sprint);
		this.estimatedHours = estimatedHours;
		this.estimatedAdditionalHours = estimatedAdditionalHours;
	}

	public Sprint getSprint() {
		return this.sprint;
	}

	public double getEstimatedHours() {
		return this.estimatedHours;
	}

	public double getEstimatedAdditionalHours() {
		return this.estimatedAdditionalHours;
	}

}
