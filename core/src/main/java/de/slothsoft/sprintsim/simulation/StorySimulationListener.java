package de.slothsoft.sprintsim.simulation;

import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import de.slothsoft.sprintsim.Member;
import de.slothsoft.sprintsim.Task;
import de.slothsoft.sprintsim.execution.SprintRetro;
import de.slothsoft.sprintsim.generation.SprintPlanning;
import de.slothsoft.sprintsim.impl.ArrayToArrayMap;

public class StorySimulationListener implements SimulationListener {

	private static final String[] MEMBER_NAMES = {"Angie", "Bert", "Charles", "David", "Emil", "Francis", "Gert",
			"Hans", "Ike", "James", "Klaus", "Lars", "Markus", "Norbert", "Olaf", "Paul", "Quentin", "Ralf", "Steffi",
			"Tony", "Ulf", "Viktor", "Wolfgang", "Xerox", "Yens", "Zack"};
	private static final int FIRST_TASK = 31415;
	private static final int COLUMN_SIZE = 15;
	private static final String EMPTY_COLUMN = IntStream.range(0, COLUMN_SIZE).mapToObj(i -> " ")
			.collect(Collectors.joining());

	private String taskIdPrefix = "LIO";
	private Logger logger = System.out::println;

	private ArrayToArrayMap<Member, String> memberNames;
	private ArrayToArrayMap<Task, String> taskNames;

	@Override
	public void simulationStarted(SimulationInfo simulationInfo) {
		this.memberNames = createMemberNames(simulationInfo.getMembers());

		this.logger.logTitle("Team Members");
		for (final Entry<Member, String> member : this.memberNames) {
			this.logger.log(MessageFormat.format("{0} ({1}, {2}h)", member.getValue(),
					member.getKey().getWorkPerformance(), String.valueOf(member.getKey().getWorkHoursPerDay())));
		}
		this.logger.logEmpty();
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
		this.taskNames = createTaskNames(sprintPlanning.getSprint().getTasks());

		this.logger.logTitle("Sprint Planning");

		// log title of table

		final StringBuilder sb = new StringBuilder(EMPTY_COLUMN);
		for (final Member member : this.memberNames.getKeys()) {
			sb.append(createTableRightAligned(this.memberNames.getValue(member)));
		}
		sb.append(createTableRightAligned("All"));
		this.logger.log(sb.toString());

		// log rest of table

		final NumberFormat format = NumberFormat.getIntegerInstance(Locale.ENGLISH);

		for (final Task task : sprintPlanning.getSprint().getTasks()) {
			sb.setLength(0);
			sb.append(createTableLeftAligned(this.taskNames.getValue(task)));

			for (final Member member : this.memberNames.getKeys()) {
				final double estimation = sprintPlanning.getTaskEstimation(task, member);
				sb.append(createTableRightAligned(format.format(estimation)));
			}
			sb.append(createTableRightAligned(format.format(sprintPlanning.getCollectedTaskEstimation(task))));
			this.logger.log(sb.toString());
		}

		// log additional stuff

		this.logger.logEmpty();
		this.logger.log("Estimated Hours:  " + sprintPlanning.getEstimatedHours());
		this.logger.log("Additional Hours: " + sprintPlanning.getEstimatedAdditionalHours());
		this.logger.logEmpty();
	}

	private static String createTableLeftAligned(String value) {
		return value + EMPTY_COLUMN.substring(0, Math.max(0, COLUMN_SIZE - value.length()));
	}

	private static String createTableRightAligned(String value) {
		return EMPTY_COLUMN.substring(0, Math.max(0, COLUMN_SIZE - value.length())) + value;
	}

	private ArrayToArrayMap<Task, String> createTaskNames(Task[] tasks) {
		final String[] taskNamesArray = new String[tasks.length];
		for (int i = 0; i < tasks.length; i++) {
			taskNamesArray[i] = createTaskName(i);
		}
		return new ArrayToArrayMap<Task, String>(tasks).values(taskNamesArray);
	}

	String createTaskName(int index) {
		return this.taskIdPrefix + '-' + String.valueOf(FIRST_TASK + index);
	}

	@Override
	public void sprintExecuted(SprintRetro sprintRetro) {
		this.logger.logTitle("Sprint Retro");
		this.logger.log("Necessary Additional Hours: " + sprintRetro.getNecessaryAdditionalHours());
		this.logger.logEmpty();
	}

	@Override
	public void simulationFinished(SimulationResult simulationResult) {
		// nothing to do any more
	}

	public Logger getLogger() {
		return this.logger;
	}

	public StorySimulationListener logger(Logger newLogger) {
		setLogger(newLogger);
		return this;
	}

	public void setLogger(Logger logger) {
		this.logger = Objects.requireNonNull(logger);
	}

	public String getTaskIdPrefix() {
		return this.taskIdPrefix;
	}

	public StorySimulationListener taskIdPrefix(String newTaskIdPrefix) {
		setTaskIdPrefix(newTaskIdPrefix);
		return this;
	}

	public void setTaskIdPrefix(String taskIdPrefix) {
		this.taskIdPrefix = Objects.requireNonNull(taskIdPrefix);
	}

}
