package de.slothsoft.sprintsim.simulation;

import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;

import de.slothsoft.sprintsim.Member;
import de.slothsoft.sprintsim.Task;
import de.slothsoft.sprintsim.execution.SprintExecutor;
import de.slothsoft.sprintsim.generation.SprintGenerator;
import de.slothsoft.sprintsim.generation.SprintPlanning;
import de.slothsoft.sprintsim.io.ComponentWriter;
import de.slothsoft.sprintsim.io.TextComponentWriter;

public class StoryTimelineListener implements TimelineListener {

	private static final int FIRST_TASK = 31415;
	/** A <code>String</code> for easier story telling. */
	private static final String TASK_DATA_NAME = "name"; //$NON-NLS-1$

	private ComponentWriter componentWriter = new TextComponentWriter(System.out::println);
	private String taskIdPrefix = "LIO"; //$NON-NLS-1$
	private int waitMillisPerHour;

	private final NumberFormat numberFormat = NumberFormat.getInstance(Locale.ENGLISH);

	private int sprintNumber;
	private int taskCount;
	private int longestDayInHours = 8;

	private int lastDay = -1;
	private double lastHour = -1;

	@Override
	public void eventHappened(TimelineEvent event) {
		waitBeforeLoggingIfNecessary(event.day, event.hour);

		switch (event.type) {
			case SIMULATION_STARTED :
				logSimulationStarted(event);
				break;
			case SPRINT_STARTED :
				logSprintStarted(event);
				break;
			case TASK_STARTED :
				logTaskStarted(event);
				break;
			case TASK_FINISHED :
				logTaskFinished(event);
				break;
			case SPRINT_FINISHED :
				logSprintFinished(event);
				break;
			case SIMULATION_FINISHED :
				logSimulationFinished(event);
				break;
			default :
				break;
		}

	}

	private void waitBeforeLoggingIfNecessary(int currentDay, double currentHour) {
		if (this.waitMillisPerHour >= 0 && this.lastDay >= TimelineSimulationListener.START_DAY) {
			waitBeforeLogging(currentDay, currentHour);
		}
		this.lastDay = currentDay;
		this.lastHour = currentHour;
	}

	private void waitBeforeLogging(int currentDay, double currentHour) {
		final double differenceInHours = calculateDifference(this.lastDay, this.lastHour, currentDay, currentHour,
				this.longestDayInHours);

		try {
			Thread.sleep((long) (differenceInHours * this.waitMillisPerHour));
		} catch (final InterruptedException e) {
			// ignore
		}
	}

	static double calculateDifference(int day1, double hour1, int day2, double hour2, int longestDayInHours) {
		final int diffInDays = day2 - day1;
		final double diffInHours = hour2 - hour1;
		return Math.abs(diffInDays * longestDayInHours + diffInHours);
	}

	private void logSimulationStarted(TimelineEvent event) {
		final SimulationInfo simulationInfo = (SimulationInfo) event.data;

		// prepare data

		final Member[] members = simulationInfo.getMembers();
		this.sprintNumber = 0;
		this.taskCount = 0;
		this.longestDayInHours = Arrays.stream(members).mapToInt(Member::getWorkHoursPerDay).max().getAsInt();
		PrettyNames.appendMemberNames(members);

		// log

		this.componentWriter.writeTitle(Messages.getString("TeamMembersTitle")); //$NON-NLS-1$

		for (final Member member : members) {
			this.componentWriter.writeLine(MessageFormat.format(Messages.getString("TeamMemberPattern"), //$NON-NLS-1$
					member.getUserData(PrettyNames.DATA_NAME), member.getWorkPerformance(),
					String.valueOf(member.getWorkHoursPerDay())));
		}
		this.componentWriter.writeEmpty();
	}

	private void logSprintStarted(TimelineEvent event) {
		final SprintPlanning sprintPlanning = (SprintPlanning) event.data;

		// prepare data

		appendTaskNames(sprintPlanning.getSprint().getTasks());
		this.sprintNumber++;

		// log

		this.componentWriter.writeTitle(
				MessageFormat.format(Messages.getString("SprintStartPattern"), String.valueOf(this.sprintNumber)));
	}

	private void appendTaskNames(Task[] tasks) {
		for (int i = 0; i < tasks.length; i++) {
			tasks[i].addUserData(TASK_DATA_NAME, createTaskName(this.taskCount + i));
		}
		this.taskCount += tasks.length;
	}

	String createTaskName(int index) {
		return this.taskIdPrefix + '-' + String.valueOf(FIRST_TASK + index);
	}

	private void logTaskStarted(TimelineEvent event) {
		final Task task = (Task) event.data;

		// prepare data

		final Member member = (Member) task.getUserData(SprintExecutor.TASK_DATA_ASSIGNEE);
		final int memberIndex = ((Integer) task.getUserData(SprintExecutor.TASK_DATA_ASSIGNEE_INDEX)).intValue();
		final double memberEstimation = ((double[]) task
				.getUserData(SprintGenerator.TASK_DATA_MEMBER_ESTIMATIONS))[memberIndex];
		final double teamEstimation = ((Double) task.getUserData(SprintGenerator.TASK_DATA_COLLECTED_ESTIMATION))
				.doubleValue();

		// log

		this.componentWriter.writeLine(MessageFormat.format(Messages.getString("TaskStartedPattern"), //$NON-NLS-1$
				member.getUserData(PrettyNames.DATA_NAME), task.getUserData(TASK_DATA_NAME),
				this.numberFormat.format(memberEstimation), this.numberFormat.format(teamEstimation),
				stringify(event.day, event.hour)));
	}

	private void logTaskFinished(TimelineEvent event) {
		final Task task = (Task) event.data;

		// prepare data

		final Member member = (Member) task.getUserData(SprintExecutor.TASK_DATA_ASSIGNEE);
		final double necessaryHours = ((Double) task.getUserData(SprintExecutor.TASK_DATA_NECESSARY_HOURS))
				.doubleValue();

		// log

		this.componentWriter.writeLine(MessageFormat.format(Messages.getString("TaskFinishedPattern"), //$NON-NLS-1$
				member.getUserData(PrettyNames.DATA_NAME), task.getUserData(TASK_DATA_NAME),
				this.numberFormat.format(necessaryHours), stringify(event.day, event.hour)));
	}

	static String stringify(int day, double hour) {
		final int intHours = (int) hour;
		final String zero = "%02d";
		final int minutes = (int) (Math.round((hour - intHours) * 60));
		final String hoursString = String.format(zero, Integer.valueOf(intHours)) + ':'
				+ String.format(zero, Integer.valueOf(minutes));
		return MessageFormat.format(Messages.getString("DayHourPattern"), //$NON-NLS-1$
				String.valueOf(day), hoursString);
	}

	@SuppressWarnings("unused")
	private void logSprintFinished(TimelineEvent event) {
		this.componentWriter.writeEmpty();
	}

	@SuppressWarnings("unused")
	private void logSimulationFinished(TimelineEvent event) {
		// nothing to do yet
	}

	public ComponentWriter getComponentWriter() {
		return this.componentWriter;
	}

	public StoryTimelineListener componentWriter(ComponentWriter newComponentWriter) {
		setComponentWriter(newComponentWriter);
		return this;
	}

	public void setComponentWriter(ComponentWriter componentWriter) {
		this.componentWriter = Objects.requireNonNull(componentWriter);
	}

	public int getWaitMillisPerHour() {
		return this.waitMillisPerHour;
	}

	public StoryTimelineListener waitMillisPerHour(int newWaitMillisPerHour) {
		setWaitMillisPerHour(newWaitMillisPerHour);
		return this;
	}

	public void setWaitMillisPerHour(int waitMillisPerHour) {
		this.waitMillisPerHour = waitMillisPerHour;
	}

	public String getTaskIdPrefix() {
		return this.taskIdPrefix;
	}

	public StoryTimelineListener taskIdPrefix(String newTaskIdPrefix) {
		setTaskIdPrefix(newTaskIdPrefix);
		return this;
	}

	public void setTaskIdPrefix(String taskIdPrefix) {
		this.taskIdPrefix = Objects.requireNonNull(taskIdPrefix);
	}

}
