package de.slothsoft.sprintsim.simulation;

import java.util.Objects;

import de.slothsoft.sprintsim.Member;
import de.slothsoft.sprintsim.Task;
import de.slothsoft.sprintsim.execution.SprintRetro;
import de.slothsoft.sprintsim.generation.SprintPlanning;
import de.slothsoft.sprintsim.io.Logger;

public class LoggingSimulationListener implements SimulationListener {

	private static final String[] MEMBER_NAMES = {"Angie", "Bert", "Charles", "David", "Emil", "Francis", "Gert", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$
			"Hans", "Ike", "James", "Klaus", "Lars", "Markus", "Norbert", "Olaf", "Paul", "Quentin", "Ralf", "Steffi", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$ //$NON-NLS-10$ //$NON-NLS-11$ //$NON-NLS-12$
			"Tony", "Ulf", "Viktor", "Wolfgang", "Xerox", "Yens", "Zack"}; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$
	private static final int FIRST_TASK = 31415;
	/** A <code>String</code> for easier story telling. */
	public static final String TASK_DATA_NAME = "name"; //$NON-NLS-1$
	/** A <code>String</code> for easier story telling. */
	public static final String MEMBER_DATA_NAME = "name"; //$NON-NLS-1$

	private String taskIdPrefix = "LIO"; //$NON-NLS-1$
	private Logger logger = System.out::println;
	private boolean printTasksOverview = true;

	private int taskCount;
	private SimulationListener loggingDelegator;

	@Override
	public void simulationStarted(SimulationInfo simulationInfo) {
		appendMemberNames(simulationInfo.getMembers());
		this.taskCount = 0;

		if (simulationInfo.getNumberOfSprints() == 1) {
			this.loggingDelegator = new LoggingOneSprintSimulationListener().logger(this.logger)
					.taskNameSupplier(task -> (String) task.getUserData(TASK_DATA_NAME))
					.printTasksOverview(this.printTasksOverview);
		} else {
			this.loggingDelegator = new LoggingSprintsSimulationListener().logger(this.logger)
					.taskNameSupplier(task -> (String) task.getUserData(TASK_DATA_NAME))
					.printTasksOverview(this.printTasksOverview);
		}

		this.loggingDelegator.simulationStarted(simulationInfo);
	}

	static void appendMemberNames(Member[] members) {
		for (int i = 0; i < members.length; i++) {
			members[i].addUserData(MEMBER_DATA_NAME, createMemberName(i));
		}
	}

	static String createMemberName(int index) {
		final int memberNameIndex = index % MEMBER_NAMES.length;
		final int numberWithSameName = index / MEMBER_NAMES.length;
		return MEMBER_NAMES[memberNameIndex]
				+ (numberWithSameName > 0 ? ' ' + String.valueOf(numberWithSameName + 1) : ""); //$NON-NLS-1$
	}

	@Override
	public void sprintPlanned(SprintPlanning sprintPlanning) {
		appendTaskNames(sprintPlanning.getSprint().getTasks());
		this.loggingDelegator.sprintPlanned(sprintPlanning);
	}

	// TODO: maybe make this and the member name generator configurable

	private void appendTaskNames(Task[] tasks) {
		for (int i = 0; i < tasks.length; i++) {
			tasks[i].addUserData(TASK_DATA_NAME, createTaskName(this.taskCount + i));
		}
		this.taskCount += tasks.length;
	}

	String createTaskName(int index) {
		return this.taskIdPrefix + '-' + String.valueOf(FIRST_TASK + index);
	}

	@Override
	public void sprintExecuted(SprintRetro sprintRetro) {
		this.loggingDelegator.sprintExecuted(sprintRetro);
	}

	@Override
	public void simulationFinished(SimulationResult simulationResult) {
		this.loggingDelegator.simulationFinished(simulationResult);
	}

	public Logger getLogger() {
		return this.logger;
	}

	public LoggingSimulationListener logger(Logger newLogger) {
		setLogger(newLogger);
		return this;
	}

	public void setLogger(Logger logger) {
		this.logger = Objects.requireNonNull(logger);
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
