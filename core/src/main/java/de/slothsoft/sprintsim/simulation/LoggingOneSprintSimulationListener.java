package de.slothsoft.sprintsim.simulation;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.function.Function;

import de.slothsoft.sprintsim.Member;
import de.slothsoft.sprintsim.Task;
import de.slothsoft.sprintsim.execution.SprintRetro;
import de.slothsoft.sprintsim.generation.SprintPlanning;
import de.slothsoft.sprintsim.io.LogTableWriter;
import de.slothsoft.sprintsim.io.Logger;
import de.slothsoft.sprintsim.io.TaskWriter;

/**
 * This {@link SimulationListener} logs the information from one sprint very
 * detailed (and pretty). If you have multiple sprints consider using
 * {@link LoggingSprintsSimulationListener}.
 */

class LoggingOneSprintSimulationListener implements SimulationListener {

	private Member[] members;
	private Logger logger = System.out::println;
	private Function<Task, String> taskNameSupplier = TaskWriter.DEFAULT_TASK_NAME_SUPPLIER; //$NON-NLS-1$

	@Override
	public void simulationStarted(SimulationInfo simulationInfo) {

		this.logger.logTitle(Messages.getString("TeamMembersTitle")); //$NON-NLS-1$
		
		this.members = simulationInfo.getMembers();
		for (final Member member : this.members) {
			this.logger.log(MessageFormat.format(Messages.getString("TeamMemberPattern"), member.getUserData(LoggingSimulationListener.MEMBER_DATA_NAME), //$NON-NLS-1$
					member.getWorkPerformance(), String.valueOf(member.getWorkHoursPerDay())));
		}
		this.logger.logEmpty();
	}

	@Override
	public void sprintPlanned(SprintPlanning sprintPlanning) {
		this.logger.logTitle(Messages.getString("SprintPlanningTitle")); //$NON-NLS-1$
		this.logger.log(Messages.getString("EstimatedHours") + ": "  + sprintPlanning.getEstimatedHours()); //$NON-NLS-1$ //$NON-NLS-2$
		this.logger.log(Messages.getString("EstimatedAdditionalHours") + ": " + sprintPlanning.getEstimatedAdditionalHours()); //$NON-NLS-1$ //$NON-NLS-2$
		this.logger.logEmpty();
	}

	@Override
	public void sprintExecuted(SprintRetro sprintRetro) {
		this.logger.logTitle(Messages.getString("SprintRetroTitle")); //$NON-NLS-1$
		this.logger.log(Messages.getString("RemainingHours") + ": " + sprintRetro.getRemainingHours()); //$NON-NLS-1$ //$NON-NLS-2$
		this.logger.log(Messages.getString("NecessaryAdditionalHours") + ": " + sprintRetro.getNecessaryAdditionalHours()); //$NON-NLS-1$ //$NON-NLS-2$
		this.logger.logEmpty();

		this.logger.logTitle(Messages.getString("TaskOverviewTitle")); //$NON-NLS-1$

		final TaskWriter taskWriter = new TaskWriter(new LogTableWriter(this.logger));
		taskWriter.setMemberNameSupplier(index -> (String) this.members[index].getUserData(LoggingSimulationListener.MEMBER_DATA_NAME));
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
