package de.slothsoft.sprintsim.simulation;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Comparator;
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
 * This {@link SimulationListener} logs the information from multiple sprints in
 * a nice little table. If you have only one sprint consider using
 * {@link LoggingOneSprintSimulationListener}.
 */

class LoggingSprintsSimulationListener implements SimulationListener {

	private static final int SPRINT_OVERVIEW_COLUMN_SIZE = 30;
	private ArrayToArrayMap<Member, String> memberNames;
	private Logger logger = System.out::println;
	private Function<Task, String> taskNameSupplier = TaskWriter.DEFAULT_TASK_NAME_SUPPLIER; //$NON-NLS-1$

	private SprintPlanning lastSprintPlanning;

	public LoggingSprintsSimulationListener(ArrayToArrayMap<Member, String> memberNames) {
		this.memberNames = Objects.requireNonNull(memberNames);
	}

	@Override
	public void simulationStarted(SimulationInfo simulationInfo) {
		this.logger.logTitle(Messages.getString("TeamMembersTitle")); //$NON-NLS-1$
		for (final Entry<Member, String> member : this.memberNames) {
			this.logger.log(MessageFormat.format(Messages.getString("TeamMemberPattern"), member.getValue(), //$NON-NLS-1$
					member.getKey().getWorkPerformance(), String.valueOf(member.getKey().getWorkHoursPerDay())));
		}
		this.logger.logEmpty();

		this.logger.logTitle(Messages.getString("SprintOverviewTitle")); //$NON-NLS-1$
		LogTableWriter tableWriter = new LogTableWriter(this.logger).columnSize(SPRINT_OVERVIEW_COLUMN_SIZE);
		tableWriter.writeHeader(Messages.getString("EstimatedHours"), Messages.getString("EstimatedAdditionalHours"), Messages.getString("RemainingHours"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				Messages.getString("AdditionalNecessaryHours")); //$NON-NLS-1$
	}

	@Override
	public void sprintPlanned(SprintPlanning sprintPlanning) {
		this.lastSprintPlanning = sprintPlanning;
	}

	@Override
	public void sprintExecuted(SprintRetro sprintRetro) {
		LogTableWriter tableWriter = new LogTableWriter(this.logger).columnSize(SPRINT_OVERVIEW_COLUMN_SIZE);
		tableWriter.writeLine(this.lastSprintPlanning.getEstimatedHours(),
				this.lastSprintPlanning.getEstimatedAdditionalHours(), sprintRetro.getRemainingHours(),
				sprintRetro.getNecessaryAdditionalHours());
	}

	@Override
	public void simulationFinished(SimulationResult simulationResult) {
		logger.logEmpty();

		this.logger.logTitle(Messages.getString("TaskOverviewTitle")); //$NON-NLS-1$

		final TaskWriter taskWriter = new TaskWriter(new LogTableWriter(this.logger));
		taskWriter.setMemberNameSupplier(index -> this.memberNames.getValue(this.memberNames.getKeys()[index]));
		taskWriter.setTaskNameSupplier(this.taskNameSupplier);
		taskWriter.writeExecutionInfo(true).setWriteEstimationInfo(true);
		taskWriter.writeTasks(Arrays.stream(simulationResult.retros).flatMap(r -> Arrays.stream(r.getSprint().getTasks()))
						.sorted(Comparator.comparing(taskNameSupplier)).toArray(Task[]::new));
	}

	public Logger getLogger() {
		return this.logger;
	}

	public LoggingSprintsSimulationListener logger(Logger newLogger) {
		setLogger(newLogger);
		return this;
	}

	public void setLogger(Logger logger) {
		this.logger = Objects.requireNonNull(logger);
	}

	public Function<Task, String> getTaskNameSupplier() {
		return taskNameSupplier;
	}

	public LoggingSprintsSimulationListener taskNameSupplier(Function<Task, String> newTaskNameSupplier) {
		setTaskNameSupplier(newTaskNameSupplier);
		return this;
	}

	public void setTaskNameSupplier(Function<Task, String> taskNameSupplier) {
		this.taskNameSupplier = Objects.requireNonNull(taskNameSupplier);
	}

}
