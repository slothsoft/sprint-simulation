package de.slothsoft.sprintsim.simulation;

import java.util.Arrays;
import java.util.Objects;

import de.slothsoft.sprintsim.Member;
import de.slothsoft.sprintsim.config.SprintConfig;
import de.slothsoft.sprintsim.config.TaskConfig;

public class SimulationInfo {

	private final Member[] members;
	private final TaskConfig taskConfig;
	private final SprintConfig sprintConfig;
	private final int numberOfSprints;

	public SimulationInfo(Member[] members, TaskConfig taskConfig, SprintConfig sprintConfig, int numberOfSprints) {
		this.members = members;
		this.taskConfig = taskConfig;
		this.sprintConfig = sprintConfig;
		this.numberOfSprints = numberOfSprints;
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

	public int getNumberOfSprints() {
		return this.numberOfSprints;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.members, Integer.valueOf(this.numberOfSprints), this.sprintConfig, this.taskConfig);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;

		final SimulationInfo other = (SimulationInfo) obj;
		if (!Arrays.equals(this.members, other.members)) return false;
		if (this.numberOfSprints != other.numberOfSprints) return false;
		if (!Objects.equals(this.sprintConfig, other.sprintConfig)) return false;
		if (!Objects.equals(this.taskConfig, other.taskConfig)) return false;
		return true;
	}

	@Override
	public String toString() {
		return "SimulationInfo [members=" + Arrays.toString(this.members) + ", taskConfig=" + this.taskConfig
				+ ", sprintConfig=" + this.sprintConfig + ", numberOfSprints=" + this.numberOfSprints + "]";
	}

}
