
import de.slothsoft.sprintsim.Member;
import de.slothsoft.sprintsim.simulation.Simulation;
import de.slothsoft.sprintsim.simulation.LoggingSimulationListener;

public class SimpleSprintExample {

	public static void main(String[] args) {
		final Member senior = Member.createSenior();
		final Member normal = Member.createNormal();
		final Member junior = Member.createJunior();

		final Simulation simulation = new Simulation(senior, normal, junior);
		simulation.addSimulationListener(new LoggingSimulationListener());
		simulation.runSprint();
	}
}
