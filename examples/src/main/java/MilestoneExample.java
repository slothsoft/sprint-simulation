
import de.slothsoft.sprintsim.Member;
import de.slothsoft.sprintsim.simulation.LoggingSimulationListener;
import de.slothsoft.sprintsim.simulation.Simulation;

public class MilestoneExample {

	public static void main(String[] args) {
		final Member senior = Member.createSenior().workHoursPerDay(4);
		final Member normal = Member.createNormal();
		final Member junior = Member.createJunior().workHoursPerDay(7);

		final Simulation simulation = new Simulation(senior, normal, junior);
		simulation.addSimulationListener(new LoggingSimulationListener().printTasksOverview(false));
		simulation.runMilestone(100);
	}
}
