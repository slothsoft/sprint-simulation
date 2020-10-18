
import de.slothsoft.sprintsim.Member;
import de.slothsoft.sprintsim.simulation.Simulation;
import de.slothsoft.sprintsim.simulation.StorySimulationListener;

public class SimpleStoryExecution {

	public static void main(String[] args) {
		final Member junior = Member.createJunior();
		final Member normal = Member.createNormal();
		final Member senior = Member.createSenior();

		final Simulation simulation = new Simulation(junior, normal, senior);
		simulation.addSimulationListener(new StorySimulationListener());
		simulation.runSprint();
	}
}
