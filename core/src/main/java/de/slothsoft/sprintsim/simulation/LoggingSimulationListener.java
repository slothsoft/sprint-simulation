package de.slothsoft.sprintsim.simulation;

import java.util.Objects;

import de.slothsoft.sprintsim.Task;
import de.slothsoft.sprintsim.execution.SprintRetro;
import de.slothsoft.sprintsim.generation.SprintPlanning;
import de.slothsoft.sprintsim.io.ComponentWriter;
import de.slothsoft.sprintsim.io.TextComponentWriter;
import de.slothsoft.sprintsim.simulation.PrettyNames.TaskNamingInfo;

public class LoggingSimulationListener implements SimulationListener {

	private String taskIdPrefix = "LIO"; //$NON-NLS-1$
	private ComponentWriter componentWriter = new TextComponentWriter(System.out::println);
	private boolean printTasksOverview = true;

	private int taskCount;
	private SimulationListener loggingDelegator;

	@Override
	public void simulationStarted(SimulationInfo simulationInfo) {
		PrettyNames.appendMemberNames(simulationInfo.getMembers());
		this.taskCount = 0;

		if (simulationInfo.getNumberOfSprints() == 1) {
			this.loggingDelegator = new LoggingOneSprintSimulationListener().componentWriter(this.componentWriter)
					.taskNameSupplier(task -> (String) task.getUserData(PrettyNames.DATA_NAME))
					.printTasksOverview(this.printTasksOverview);
		} else {
			this.loggingDelegator = new LoggingSprintsSimulationListener().componentWriter(this.componentWriter)
					.taskNameSupplier(task -> (String) task.getUserData(PrettyNames.DATA_NAME))
					.printTasksOverview(this.printTasksOverview);
		}

		this.loggingDelegator.simulationStarted(simulationInfo);
	}

	@Override
	public void sprintPlanned(SprintPlanning sprintPlanning) {
		appendTaskNames(sprintPlanning.getSprint().getTasks());
		this.loggingDelegator.sprintPlanned(sprintPlanning);
	}

	private void appendTaskNames(Task[] tasks) {
		PrettyNames.appendTaskNames(tasks,
				new TaskNamingInfo().firstIndex(this.taskCount).taskIdPrefix(this.taskIdPrefix));
		this.taskCount += tasks.length;
	}

	@Override
	public void sprintExecuted(SprintRetro sprintRetro) {
		this.loggingDelegator.sprintExecuted(sprintRetro);
	}

	@Override
	public void simulationFinished(SimulationResult simulationResult) {
		this.loggingDelegator.simulationFinished(simulationResult);
	}

	public ComponentWriter getComponentWriter() {
		return this.componentWriter;
	}

	public LoggingSimulationListener componentWriter(ComponentWriter newComponentWriter) {
		setComponentWriter(newComponentWriter);
		return this;
	}

	public void setComponentWriter(ComponentWriter componentWriter) {
		this.componentWriter = Objects.requireNonNull(componentWriter);
	}

	public String getTaskIdPrefix() {
		return this.taskIdPrefix;
	}

	public LoggingSimulationListener taskIdPrefix(String newTaskIdPrefix) {
		setTaskIdPrefix(newTaskIdPrefix);
		return this;
	}

	public void setTaskIdPrefix(String taskIdPrefix) {
		this.taskIdPrefix = Objects.requireNonNull(taskIdPrefix);
	}

	public boolean isPrintTasksOverview() {
		return this.printTasksOverview;
	}

	public LoggingSimulationListener printTasksOverview(boolean newPrintTasksOverview) {
		setPrintTasksOverview(newPrintTasksOverview);
		return this;
	}

	public void setPrintTasksOverview(boolean printTasksOverview) {
		this.printTasksOverview = printTasksOverview;
	}

}
