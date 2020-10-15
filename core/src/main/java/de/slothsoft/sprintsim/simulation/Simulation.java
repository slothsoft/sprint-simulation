package de.slothsoft.sprintsim.simulation;

import java.util.Objects;

import de.slothsoft.sprintsim.Member;
import de.slothsoft.sprintsim.config.SprintConfig;
import de.slothsoft.sprintsim.config.TaskConfig;
import de.slothsoft.sprintsim.execution.SprintExecutor;
import de.slothsoft.sprintsim.execution.SprintRetro;
import de.slothsoft.sprintsim.generation.SprintGenerator;
import de.slothsoft.sprintsim.generation.SprintPlanning;

public class Simulation {

	private Member[] members;
	private TaskConfig taskConfig = new TaskConfig();
	private SprintConfig sprintConfig = SprintConfig.createDefault();

	public Simulation(Member... members) {
		this.members = Objects.requireNonNull(members);
	}

	public SimulationResult runSprint() {
		final SprintGenerator generator = createGenerator();
		final SprintPlanning sprintPlanning = generator.generate();

		final SprintExecutor executor = createExecutor();
		final SprintRetro retro = executor.execute(sprintPlanning.getSprint());

		return new SimulationResult(sprintPlanning, retro);
	}

	private SprintGenerator createGenerator() {
		return new SprintGenerator(this.members).taskConfig(this.taskConfig).sprintConfig(this.sprintConfig);
	}

	private SprintExecutor createExecutor() {
		return new SprintExecutor(this.members).taskConfig(this.taskConfig);
	}

	public TaskConfig getTaskConfig() {
		return this.taskConfig;
	}

	public Simulation taskConfig(TaskConfig newTaskConfig) {
		setTaskConfig(newTaskConfig);
		return this;
	}

	public void setTaskConfig(TaskConfig taskConfig) {
		this.taskConfig = Objects.requireNonNull(taskConfig);
	}

	public Member[] getMembers() {
		return this.members;
	}

	public Simulation members(Member... newMembers) {
		setMembers(newMembers);
		return this;
	}

	public void setMembers(Member... members) {
		this.members = Objects.requireNonNull(members);
	}

	public SprintConfig getSprintConfig() {
		return this.sprintConfig;
	}

	public Simulation sprintConfig(SprintConfig newSprintConfig) {
		setSprintConfig(newSprintConfig);
		return this;
	}

	public void setSprintConfig(SprintConfig sprintConfig) {
		this.sprintConfig = Objects.requireNonNull(sprintConfig);
	}

}
