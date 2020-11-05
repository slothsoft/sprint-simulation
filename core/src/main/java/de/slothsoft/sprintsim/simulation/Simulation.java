package de.slothsoft.sprintsim.simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

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
	private Long seed;

	private final List<SimulationListener> simulationListeners = new ArrayList<>();
	private TimelineSimulationListener timelineSimulationListener;

	public Simulation(Member... members) {
		this.members = Objects.requireNonNull(members);
	}

	public SimulationResult runSprint() {
		return runMilestone(1);
	}

	public SimulationResult runMilestone(int numberOfSprints) {
		final SimulationInfo info = createInfo(numberOfSprints);
		fireListeners(listener -> listener.simulationStarted(info));

		final SprintGenerator generator = createGenerator();
		final SprintExecutor executor = createExecutor();

		final SprintPlanning[] sprintPlannings = new SprintPlanning[numberOfSprints];
		final SprintRetro[] sprintRetros = new SprintRetro[numberOfSprints];

		for (int sprintNumber = 0; sprintNumber < numberOfSprints; sprintNumber++) {
			final SprintPlanning sprintPlanning = generator.generate();
			fireListeners(listener -> listener.sprintPlanned(sprintPlanning));
			sprintPlannings[sprintNumber] = sprintPlanning;

			final SprintRetro retro = executor.execute(sprintPlanning.getSprint());
			fireListeners(listener -> listener.sprintExecuted(retro));
			sprintRetros[sprintNumber] = retro;
		}

		final SimulationResult result = new SimulationResult(sprintPlannings, sprintRetros);
		fireListeners(listener -> listener.simulationFinished(result));
		return result;
	}

	SimulationInfo createInfo(int numberOfSprints) {
		return new SimulationInfo(this.members, this.taskConfig, this.sprintConfig, numberOfSprints);
	}

	SprintGenerator createGenerator() {
		return new SprintGenerator(this.members).taskConfig(this.taskConfig).sprintConfig(this.sprintConfig)
				.seed(this.seed);
	}

	SprintExecutor createExecutor() {
		return new SprintExecutor(this.members).taskConfig(this.taskConfig);
	}

	protected void fireListeners(Consumer<SimulationListener> listenerAction) {
		for (final SimulationListener simulationListener : this.simulationListeners
				.toArray(new SimulationListener[this.simulationListeners.size()])) {
			listenerAction.accept(simulationListener);
		}
	}

	public void addSimulationListener(SimulationListener simulationListener) {
		this.simulationListeners.add(simulationListener);
	}

	public void removeSimulationListener(SimulationListener simulationListener) {
		this.simulationListeners.remove(simulationListener);
	}

	public void addTimelineListener(TimelineListener simulationListener) {
		getSafeTimelineSimulationListener().addTimelineListener(simulationListener);
	}

	public void removeTimelineListener(TimelineListener simulationListener) {
		getSafeTimelineSimulationListener().removeTimelineListener(simulationListener);
	}

	TimelineSimulationListener getSafeTimelineSimulationListener() {
		if (this.timelineSimulationListener == null) {
			this.timelineSimulationListener = new TimelineSimulationListener();
			this.simulationListeners.add(this.timelineSimulationListener);
		}
		return this.timelineSimulationListener;
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

	public Long getSeed() {
		return this.seed;
	}

	public Simulation seed(Long newSeed) {
		setSeed(newSeed);
		return this;
	}

	public void setSeed(Long seed) {
		this.seed = seed;
	}

}
