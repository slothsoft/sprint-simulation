package de.slothsoft.sprintsim.simulation;

import de.slothsoft.sprintsim.execution.SprintRetro;
import de.slothsoft.sprintsim.generation.SprintPlanning;

public class SimulationResult {

	SprintPlanning[] plannings;
	SprintRetro[] retros;

	SimulationResult(SprintPlanning[] plannings, SprintRetro[] retros) {
		this.plannings = plannings;
		this.retros = retros;
	}

	public SprintPlanning[] getPlannings() {
		return this.plannings;
	}

	public SprintRetro[] getRetros() {
		return this.retros;
	}

}
