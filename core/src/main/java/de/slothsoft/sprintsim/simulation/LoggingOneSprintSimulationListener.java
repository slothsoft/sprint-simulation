package de.slothsoft.sprintsim.simulation;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.function.Function;

import de.slothsoft.sprintsim.Member;
import de.slothsoft.sprintsim.Task;
import de.slothsoft.sprintsim.execution.SprintRetro;
import de.slothsoft.sprintsim.generation.SprintPlanning;
import de.slothsoft.sprintsim.io.ComponentWriter;
import de.slothsoft.sprintsim.io.TaskWriter;
import de.slothsoft.sprintsim.io.TextComponentWriter;

/**
 * This {@link SimulationListener} logs the information from one sprint very detailed (and
 * pretty). If you have multiple sprints consider using
 * {@link LoggingSprintsSimulationListener}.
 */

class LoggingOneSprintSimulationListener implements SimulationListener {

	private Member[] members;
	private ComponentWriter componentWriter = new TextComponentWriter(System.out::println);
	private Function<Task, String> taskNameSupplier = TaskWriter.DEFAULT_TASK_NAME_SUPPLIER;
	private boolean printTasksOverview = true;

	@Override
	public void simulationStarted(SimulationInfo simulationInfo) {

		this.componentWriter.writeTitle(Messages.getString("TeamMembersTitle")); //$NON-NLS-1$

		this.members = simulationInfo.getMembers();
		for (final Member member : this.members) {
			this.componentWriter.writeLine(MessageFormat.format(Messages.getString("TeamMemberPattern"), //$NON-NLS-1$
					member.getUserData(PrettyNames.DATA_NAME), member.getWorkPerformance(),
					String.valueOf(member.getWorkHoursPerDay())));
		}
		this.componentWriter.writeEmpty();
	}

	@Override
	public void sprintPlanned(SprintPlanning sprintPlanning) {
		this.componentWriter.writeTitle(Messages.getString("SprintPlanningTitle")); //$NON-NLS-1$
		this.componentWriter
				.writeLine(Messages.getString("EstimatedHours") + ": " + sprintPlanning.getEstimatedHours()); //$NON-NLS-1$ //$NON-NLS-2$
		this.componentWriter.writeLine(
				Messages.getString("EstimatedAdditionalHours") + ": " + sprintPlanning.getEstimatedAdditionalHours()); //$NON-NLS-1$ //$NON-NLS-2$
		this.componentWriter.writeEmpty();
	}

	@Override
	public void sprintExecuted(SprintRetro sprintRetro) {
		this.componentWriter.writeTitle(Messages.getString("SprintRetroTitle")); //$NON-NLS-1$
		this.componentWriter.writeLine(Messages.getString("RemainingHours") + ": " + sprintRetro.getRemainingHours()); //$NON-NLS-1$ //$NON-NLS-2$
		this.componentWriter.writeLine(
				Messages.getString("NecessaryAdditionalHours") + ": " + sprintRetro.getNecessaryAdditionalHours()); //$NON-NLS-1$ //$NON-NLS-2$
		this.componentWriter.writeEmpty();

		if (this.printTasksOverview) {
			this.componentWriter.writeTitle(Messages.getString("TaskOverviewTitle")); //$NON-NLS-1$

			final TaskWriter taskWriter = new TaskWriter(this.componentWriter);
			taskWriter.setMemberNameSupplier(
					index -> (String) this.members[index].getUserData(PrettyNames.DATA_NAME));
			taskWriter.setTaskNameSupplier(this.taskNameSupplier);
			taskWriter.writeExecutionInfo(true).setWriteEstimationInfo(true);
			taskWriter.writeTasks(sprintRetro.getSprint().getTasks());
		}
	}

	@Override
	public void simulationFinished(SimulationResult simulationResult) {
		// nothing to do
	}

	public Function<Task, String> getTaskNameSupplier() {
		return this.taskNameSupplier;
	}

	public LoggingOneSprintSimulationListener taskNameSupplier(Function<Task, String> newTaskNameSupplier) {
		setTaskNameSupplier(newTaskNameSupplier);
		return this;
	}

	public void setTaskNameSupplier(Function<Task, String> taskNameSupplier) {
		this.taskNameSupplier = Objects.requireNonNull(taskNameSupplier);
	}

	public boolean isPrintTasksOverview() {
		return this.printTasksOverview;
	}

	public LoggingOneSprintSimulationListener printTasksOverview(boolean newPrintTasksOverview) {
		setPrintTasksOverview(newPrintTasksOverview);
		return this;
	}

	public void setPrintTasksOverview(boolean printTasksOverview) {
		this.printTasksOverview = printTasksOverview;
	}

	public ComponentWriter getComponentWriter() {
		return this.componentWriter;
	}

	public LoggingOneSprintSimulationListener componentWriter(ComponentWriter newComponentWriter) {
		setComponentWriter(newComponentWriter);
		return this;
	}

	public void setComponentWriter(ComponentWriter componentWriter) {
		this.componentWriter = Objects.requireNonNull(componentWriter);
	}

}
