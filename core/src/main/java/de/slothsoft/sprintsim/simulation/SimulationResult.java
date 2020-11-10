package de.slothsoft.sprintsim.simulation;

import de.slothsoft.sprintsim.Member;
import de.slothsoft.sprintsim.execution.SprintRetro;
import de.slothsoft.sprintsim.generation.SprintPlanning;

public class SimulationResult {

	Member[] members;
	SprintPlanning[] plannings;
	SprintRetro[] retros;

	SimulationResult(Member[] members, SprintPlanning[] plannings, SprintRetro[] retros) {
		this.members = members;
		this.plannings = plannings;
		this.retros = retros;
	}

	public Member[] getMembers() {
		return this.members;
	}

	public SprintPlanning[] getPlannings() {
		return this.plannings;
	}

	public SprintRetro[] getRetros() {
		return this.retros;
	}

}
