package de.slothsoft.sprintsim.simulation;

import java.util.Arrays;
import java.util.Objects;

import de.slothsoft.sprintsim.Member;
import de.slothsoft.sprintsim.Task;
import de.slothsoft.sprintsim.execution.SprintRetro;
import de.slothsoft.sprintsim.generation.SprintPlanning;
import de.slothsoft.sprintsim.impl.ArrayToArrayMap;
import de.slothsoft.sprintsim.io.Logger;

public class LoggingSimulationListener implements SimulationListener {

	private static final String[] MEMBER_NAMES = {"Angie", "Bert", "Charles", "David", "Emil", "Francis", "Gert",
			"Hans", "Ike", "James", "Klaus", "Lars", "Markus", "Norbert", "Olaf", "Paul", "Quentin", "Ralf", "Steffi",
			"Tony", "Ulf", "Viktor", "Wolfgang", "Xerox", "Yens", "Zack"};
	private static final int FIRST_TASK = 31415;

	private String taskIdPrefix = "LIO";
	private Logger logger = System.out::println;

	private ArrayToArrayMap<Member, String> memberNames;
	private ArrayToArrayMap<Task, String> taskNames;
	
	private SimulationListener loggingDelegator;

	@Override
	public void simulationStarted(SimulationInfo simulationInfo) {
		this.taskNames = new ArrayToArrayMap<Task, String>();
		this.memberNames = createMemberNames(simulationInfo.getMembers());

		if (simulationInfo.getNumberOfSprints() == 1) {
			this.loggingDelegator = new LoggingOneSprintSimulationListener(this.memberNames).logger(this.logger).taskNameSupplier(task -> this.taskNames.getValue(task));
		} else {
			this.loggingDelegator = new LoggingSprintsSimulationListener(this.memberNames).logger(this.logger).taskNameSupplier(task -> this.taskNames.getValue(task));
		}
		
		this.loggingDelegator.simulationStarted(simulationInfo);
	}

	private static ArrayToArrayMap<Member, String> createMemberNames(Member[] members) {
		final String[] memberNamesArray = new String[members.length];
		for (int i = 0; i < members.length; i++) {
			memberNamesArray[i] = createMemberName(i);
		}
		return new ArrayToArrayMap<Member, String>(members).values(memberNamesArray);
	}

	static String createMemberName(int index) {
		final int memberNameIndex = index % MEMBER_NAMES.length;
		final int numberWithSameName = index / MEMBER_NAMES.length;
		return MEMBER_NAMES[memberNameIndex]
				+ (numberWithSameName > 0 ? ' ' + String.valueOf(numberWithSameName + 1) : "");
	}

	@Override
	public void sprintPlanned(SprintPlanning sprintPlanning) {
		appendTaskNames(sprintPlanning.getSprint().getTasks());
		this.loggingDelegator.sprintPlanned(sprintPlanning);
	}

	private void appendTaskNames(Task[] tasks) {
		int startIndex = this.taskNames.getKeys().length;
		final Task[] tasksArray = Arrays.copyOf(taskNames.getKeys(), startIndex + tasks.length);
		final String[] taskNamesArray = taskNames.getValues() == null ? new String[tasks.length] : Arrays.copyOf(taskNames.getValues(), tasksArray.length);
		
		for (int i = 0; i < tasks.length; i++) {
			tasksArray[startIndex + i] = tasks[i];
			taskNamesArray[startIndex + i] = createTaskName(startIndex + i);
		}

		this.taskNames = new ArrayToArrayMap<Task, String>(tasksArray).values(taskNamesArray);
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

}
