package de.slothsoft.sprintsim.simulation;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;

import de.slothsoft.sprintsim.Member;
import de.slothsoft.sprintsim.Task;
import de.slothsoft.sprintsim.execution.SprintRetro;
import de.slothsoft.sprintsim.generation.SprintPlanning;
import de.slothsoft.sprintsim.io.LogTableWriter;
import de.slothsoft.sprintsim.io.Logger;
import de.slothsoft.sprintsim.io.TaskWriter;

/**
 * This {@link SimulationListener} logs the information from multiple sprints in a nice
 * little table. If you have only one sprint consider using
 * {@link LoggingOneSprintSimulationListener}.
 */

class LoggingSprintsSimulationListener implements SimulationListener {

	private static final int SPRINT_OVERVIEW_COLUMN_SIZE = 30;

	private Member[] members;
	private Logger logger = System.out::println;
	private Function<Task, String> taskNameSupplier = TaskWriter.DEFAULT_TASK_NAME_SUPPLIER;
	private boolean printTasksOverview = true;

	private SprintPlanning lastSprintPlanning;

	@Override
	public void simulationStarted(SimulationInfo simulationInfo) {
		this.logger.logTitle(Messages.getString("TeamMembersTitle")); //$NON-NLS-1$

		this.members = simulationInfo.getMembers();
		for (final Member member : this.members) {
			this.logger.log(MessageFormat.format(Messages.getString("TeamMemberPattern"), //$NON-NLS-1$
					member.getUserData(LoggingSimulationListener.MEMBER_DATA_NAME), member.getWorkPerformance(),
					String.valueOf(member.getWorkHoursPerDay())));
		}
		this.logger.logEmpty();

		this.logger.logTitle(Messages.getString("SprintOverviewTitle")); //$NON-NLS-1$
		final LogTableWriter tableWriter = new LogTableWriter(this.logger).columnSize(SPRINT_OVERVIEW_COLUMN_SIZE);
		tableWriter.writeHeader(Messages.getString("EstimatedHours"), Messages.getString("EstimatedAdditionalHours"), //$NON-NLS-1$ //$NON-NLS-2$
				Messages.getString("RemainingHours"), //$NON-NLS-1$
				Messages.getString("AdditionalNecessaryHours")); //$NON-NLS-1$
	}

	@Override
	public void sprintPlanned(SprintPlanning sprintPlanning) {
		this.lastSprintPlanning = sprintPlanning;
	}

	@Override
	public void sprintExecuted(SprintRetro sprintRetro) {
		final LogTableWriter tableWriter = new LogTableWriter(this.logger).columnSize(SPRINT_OVERVIEW_COLUMN_SIZE);
		tableWriter.writeLine(Double.valueOf(this.lastSprintPlanning.getEstimatedHours()),
				Double.valueOf(this.lastSprintPlanning.getEstimatedAdditionalHours()),
				Double.valueOf(sprintRetro.getRemainingHours()),
				Double.valueOf(sprintRetro.getNecessaryAdditionalHours()));
	}

	@Override
	public void simulationFinished(SimulationResult simulationResult) {
		final LogTableWriter tableWriter = new LogTableWriter(this.logger).columnSize(SPRINT_OVERVIEW_COLUMN_SIZE);
		tableWriter.writeSeparatorLine(4);
		tableWriter.writeLine(
				Double.valueOf(calculateAverage(simulationResult.getPlannings(), SprintPlanning::getEstimatedHours)),
				Double.valueOf(
						calculateAverage(simulationResult.getPlannings(), SprintPlanning::getEstimatedAdditionalHours)),
				Double.valueOf(calculateAverage(simulationResult.getRetros(), SprintRetro::getRemainingHours)),
				Double.valueOf(
						calculateAverage(simulationResult.getRetros(), SprintRetro::getNecessaryAdditionalHours)));

		if (this.printTasksOverview) {
			this.logger.logEmpty();

			this.logger.logTitle(Messages.getString("TaskOverviewTitle")); //$NON-NLS-1$

			final TaskWriter taskWriter = new TaskWriter(new LogTableWriter(this.logger));
			taskWriter.setMemberNameSupplier(
					index -> (String) this.members[index].getUserData(LoggingSimulationListener.MEMBER_DATA_NAME));
			taskWriter.setTaskNameSupplier(this.taskNameSupplier);
			taskWriter.writeExecutionInfo(true).setWriteEstimationInfo(true);
			taskWriter.writeTasks(
					Arrays.stream(simulationResult.retros).flatMap(r -> Arrays.stream(r.getSprint().getTasks()))
							.sorted(Comparator.comparing(this.taskNameSupplier)).toArray(Task[]::new));
		}
	}

	private static <T> double calculateAverage(T[] values, ToDoubleFunction<T> valueGetter) {
		return Arrays.stream(values).mapToDouble(valueGetter).average().getAsDouble();
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
		return this.taskNameSupplier;
	}

	public LoggingSprintsSimulationListener taskNameSupplier(Function<Task, String> newTaskNameSupplier) {
		setTaskNameSupplier(newTaskNameSupplier);
		return this;
	}

	public void setTaskNameSupplier(Function<Task, String> taskNameSupplier) {
		this.taskNameSupplier = Objects.requireNonNull(taskNameSupplier);
	}

	public boolean isPrintTasksOverview() {
		return this.printTasksOverview;
	}

	public LoggingSprintsSimulationListener printTasksOverview(boolean newPrintTasksOverview) {
		setPrintTasksOverview(newPrintTasksOverview);
		return this;
	}

	public void setPrintTasksOverview(boolean printTasksOverview) {
		this.printTasksOverview = printTasksOverview;
	}

}
