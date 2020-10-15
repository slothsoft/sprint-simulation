

import de.slothsoft.sprintsim.Member;
import de.slothsoft.sprintsim.simulation.Simulation;
import de.slothsoft.sprintsim.simulation.SimulationResult;

public class SimpleExecution {

	public static void main(String[] args) {
		final Member junior = Member.createJunior();
		final Member normal = Member.createNormal();
		final Member senior = Member.createSenior();

		final Simulation simulation = new Simulation(junior, normal, senior);
		final SimulationResult result = simulation.runSprint();

		System.out.println("RemainingHours " + result.getRetro().getRemainingHours());
		System.out.println("NecessaryAdditionalHours " + result.getRetro().getNecessaryAdditionalHours());
	}
}
