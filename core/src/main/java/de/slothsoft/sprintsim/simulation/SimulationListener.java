package de.slothsoft.sprintsim.simulation;

import java.util.EventListener;

import de.slothsoft.sprintsim.execution.SprintRetro;
import de.slothsoft.sprintsim.generation.SprintPlanning;

public interface SimulationListener extends EventListener {

	class Adapter implements SimulationListener {

		@Override
		public void simulationStarted(SimulationInfo simulationInfo) {
			// noop
		}

		@Override
		public void sprintPlanned(SprintPlanning sprintPlanning) {
			// noop
		}

		@Override
		public void sprintExecuted(SprintRetro sprintRetro) {
			// noop
		}

		@Override
		public void simulationFinished(SimulationResult simulationResult) {
			// noop
		}

	}

	void simulationStarted(SimulationInfo simulationInfo);

	void sprintPlanned(SprintPlanning sprintPlanning);

	void sprintExecuted(SprintRetro sprintRetro);

	void simulationFinished(SimulationResult simulationResult);
}
