package de.slothsoft.sprintsim.app;

import java.util.Arrays;

import de.slothsoft.sprintsim.Member;
import de.slothsoft.sprintsim.config.SprintConfig;
import de.slothsoft.sprintsim.config.TaskConfig;
import de.slothsoft.sprintsim.simulation.Simulation;

public class SimulationBuilder {

	private static final SessionInstance<SimulationBuilder> SIMULATION_INFO = new SessionInstance<>(
			SimulationBuilder::new);

	public static SimulationBuilder getSimulationInfoForCurrentSession() {
		return SIMULATION_INFO.getForCurrentSession();
	}

	public static void setSimulationInfoForCurrentSession(SimulationBuilder info) {
		SIMULATION_INFO.setForCurrentSession(info);
	}

	public static Member[] createDefaultMembers() {
		return new Member[]{Member.createSenior(), Member.createNormal(), Member.createJunior().workHoursPerDay(7)};
	}

	private Member[] members = createDefaultMembers();
	private TaskConfig taskConfig = new TaskConfig();
	private SprintConfig sprintConfig = SprintConfig.createDefault();
	private int numberOfSprints = 3;

	public Simulation createSimulation() {
		final Simulation simulation = new Simulation(this.members);
		simulation.sprintConfig(this.sprintConfig).taskConfig(this.taskConfig);
		return simulation;
	}

	public Member[] getMembers() {
		return this.members;
	}

	public SimulationBuilder members(Member[] newMembers) {
		setMembers(newMembers);
		return this;
	}

	public void setMembers(Member[] members) {
		this.members = members;
	}

	public int getNumberOfSprints() {
		return this.numberOfSprints;
	}

	public SimulationBuilder numberOfSprints(int newNumberOfSprints) {
		setNumberOfSprints(newNumberOfSprints);
		return this;
	}

	public void setNumberOfSprints(int numberOfSprints) {
		this.numberOfSprints = numberOfSprints;
	}

	public SprintConfig getSprintConfig() {
		return this.sprintConfig;
	}

	public SimulationBuilder sprintConfig(SprintConfig newSprintConfig) {
		setSprintConfig(newSprintConfig);
		return this;
	}

	public void setSprintConfig(SprintConfig sprintConfig) {
		this.sprintConfig = sprintConfig;
	}

	public TaskConfig getTaskConfig() {
		return this.taskConfig;
	}

	public SimulationBuilder taskConfig(TaskConfig newTaskConfig) {
		setTaskConfig(newTaskConfig);
		return this;
	}

	public void setTaskConfig(TaskConfig taskConfig) {
		this.taskConfig = taskConfig;
	}

	@Override
	public String toString() {
		return "SimulationBuilder [members=" + Arrays.toString(this.members) + ", taskConfig=" + this.taskConfig
				+ ", sprintConfig=" + this.sprintConfig + ", numberOfSprints=" + this.numberOfSprints + "]";
	}

}
