package de.slothsoft.sprintsim.simulation;

import de.slothsoft.sprintsim.Member;
import de.slothsoft.sprintsim.config.SprintConfig;
import de.slothsoft.sprintsim.config.TaskConfig;

public class SimulationInfo {

	private final Member[] members;
	private final TaskConfig taskConfig;
	private final SprintConfig sprintConfig;

	public SimulationInfo(Member[] members, TaskConfig taskConfig, SprintConfig sprintConfig) {
		this.members = members;
		this.taskConfig = taskConfig;
		this.sprintConfig = sprintConfig;
	}

	public Member[] getMembers() {
		return this.members;
	}

	public TaskConfig getTaskConfig() {
		return this.taskConfig;
	}

	public SprintConfig getSprintConfig() {
		return this.sprintConfig;
	}

}
