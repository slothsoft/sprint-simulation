package de.slothsoft.sprintsim.simulation;

import de.slothsoft.sprintsim.Sprint;
import de.slothsoft.sprintsim.execution.SprintRetro;
import de.slothsoft.sprintsim.generation.SprintPlanning;

public class SimulationResult {

	SprintPlanning planning;
	SprintRetro retro;

	SimulationResult(SprintPlanning planning, SprintRetro retro) {
		this.planning = planning;
		this.retro = retro;
	}

	public Sprint getSprint() {
		return this.planning.getSprint();
	}

	public SprintPlanning getPlanning() {
		return this.planning;
	}

	public SprintRetro getRetro() {
		return this.retro;
	}

}
