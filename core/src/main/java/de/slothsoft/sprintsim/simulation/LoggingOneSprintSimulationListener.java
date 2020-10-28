package de.slothsoft.sprintsim.simulation;

import java.text.MessageFormat;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.Function;

import de.slothsoft.sprintsim.Member;
import de.slothsoft.sprintsim.Task;
import de.slothsoft.sprintsim.execution.SprintRetro;
import de.slothsoft.sprintsim.generation.SprintPlanning;
import de.slothsoft.sprintsim.impl.ArrayToArrayMap;
import de.slothsoft.sprintsim.io.LogTableWriter;
import de.slothsoft.sprintsim.io.Logger;
import de.slothsoft.sprintsim.io.TaskWriter;

/**
 * This {@link SimulationListener} logs the information from one sprint very
 * detailed (and pretty). If you have multiple sprints consider using
 * {@link LoggingSprintsSimulationListener}.
 */

class LoggingOneSprintSimulationListener implements SimulationListener {

	private ArrayToArrayMap<Member, String> memberNames;
	private Logger logger = System.out::println;
	private Function<Task, String> taskNameSupplier = task -> "TASK-" + String.valueOf(task.hashCode());

	public LoggingOneSprintSimulationListener(ArrayToArrayMap<Member, String> memberNames) {
		this.memberNames = Objects.requireNonNull(memberNames);
	}

	@Override
	public void simulationStarted(SimulationInfo simulationInfo) {

		this.logger.logTitle("Team Members");
		for (final Entry<Member, String> member : this.memberNames) {
			this.logger.log(MessageFormat.format("{0} ({1}, {2}h)", member.getValue(),
					member.getKey().getWorkPerformance(), String.valueOf(member.getKey().getWorkHoursPerDay())));
		}
		this.logger.logEmpty();
	}

	@Override
	public void sprintPlanned(SprintPlanning sprintPlanning) {
		this.logger.logTitle("Sprint Planning");
		this.logger.log("Estimated Hours:  " + sprintPlanning.getEstimatedHours());
		this.logger.log("Additional Hours: " + sprintPlanning.getEstimatedAdditionalHours());
		this.logger.logEmpty();
	}

	@Override
	public void sprintExecuted(SprintRetro sprintRetro) {
		this.logger.logTitle("Sprint Retro");
		this.logger.log("Remaining Hours: " + sprintRetro.getRemainingHours());
		this.logger.log("Necessary Additional Hours: " + sprintRetro.getNecessaryAdditionalHours());
		this.logger.logEmpty();

		this.logger.logTitle("Tasks Overview");

		final TaskWriter taskWriter = new TaskWriter(new LogTableWriter(this.logger));
		taskWriter.setMemberNameSupplier(index -> this.memberNames.getValue(this.memberNames.getKeys()[index]));
		taskWriter.setTaskNameSupplier(this.taskNameSupplier);
		taskWriter.writeExecutionInfo(true).setWriteEstimationInfo(true);
		taskWriter.writeTasks(sprintRetro.getSprint().getTasks());
	}

	@Override
	public void simulationFinished(SimulationResult simulationResult) {
		// nothing to do
	}

	public Logger getLogger() {
		return this.logger;
	}

	public LoggingOneSprintSimulationListener logger(Logger newLogger) {
		setLogger(newLogger);
		return this;
	}

	public void setLogger(Logger logger) {
		this.logger = Objects.requireNonNull(logger);
	}

	public Function<Task, String> getTaskNameSupplier() {
		return taskNameSupplier;
	}

	public LoggingOneSprintSimulationListener taskNameSupplier(Function<Task, String> newTaskNameSupplier) {
		setTaskNameSupplier(newTaskNameSupplier);
		return this;
	}

	public void setTaskNameSupplier(Function<Task, String> taskNameSupplier) {
		this.taskNameSupplier = Objects.requireNonNull(taskNameSupplier);
	}

}
