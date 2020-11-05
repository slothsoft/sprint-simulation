package de.slothsoft.sprintsim.simulation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import de.slothsoft.sprintsim.Member;
import de.slothsoft.sprintsim.Task;
import de.slothsoft.sprintsim.execution.SprintExecutor;
import de.slothsoft.sprintsim.execution.SprintRetro;
import de.slothsoft.sprintsim.generation.SprintPlanning;
import de.slothsoft.sprintsim.simulation.TimelineEvent.Type;

/**
 * A {@link TimelineListener} that breaks the events of the sprints down so they happen
 * chronologically.
 */

class TimelineSimulationListener implements SimulationListener {

	static final int NO_DAY = -1;
	static final double NO_HOUR = Double.NaN;

	static final int START_DAY = 1;
	static final double START_HOUR = 0.0;

	private final List<TimelineListener> timelineListeners = new ArrayList<>();

	@Override
	public void simulationStarted(SimulationInfo simulationInfo) {
		fireListeners(new TimelineEvent(this, Type.SIMULATION_STARTED, NO_DAY, NO_HOUR, simulationInfo));
	}

	@Override
	public void sprintPlanned(SprintPlanning sprintPlanning) {
		fireListeners(new TimelineEvent(this, Type.SPRINT_STARTED, START_DAY, START_HOUR, sprintPlanning));
	}

	@Override
	public void sprintExecuted(SprintRetro sprintRetro) {
		final TaskAndTime lastTaskAndTime = fireTimelineEvents(sprintRetro.getMembers(),
				sprintRetro.getSprint().getTasks());
		fireListeners(
				new TimelineEvent(this, Type.SPRINT_FINISHED, lastTaskAndTime.day, lastTaskAndTime.hour, sprintRetro));
	}

	TaskAndTime fireTimelineEvents(Member[] members, Task[] tasks) {
		final List<Member> membersAsList = Arrays.asList(members);

		@SuppressWarnings("unchecked")
		final List<TaskAndTime>[] tasksByMember = new List[members.length];
		for (int i = 0; i < members.length; i++) {
			tasksByMember[i] = new ArrayList<>();
		}

		// sort the tasks by member and sum their times

		for (int taskIndex = 0; taskIndex < tasks.length; taskIndex++) {
			final Task task = tasks[taskIndex];

			final Member member = (Member) task.getUserData(SprintExecutor.TASK_DATA_ASSIGNEE);
			final int membersIndex = membersAsList.indexOf(member);

			if (membersIndex < 0) throw new IllegalArgumentException("Could not find member " + member);

			final int taskIndexByMember = tasksByMember[membersIndex].size();

			final TaskAndTime taskAndTime;
			if (taskIndexByMember == 0) {
				// first task of that member
				taskAndTime = new TaskAndTime(task);
			} else {
				// follow up task
				taskAndTime = tasksByMember[membersIndex].get(taskIndexByMember - 1).createNext(task);
			}
			tasksByMember[membersIndex].add(taskAndTime);
		}

		// now add the last task of each member

		for (int i = 0; i < tasksByMember.length; i++) {
			final TaskAndTime lastTaskAndTime = tasksByMember[i].stream().max(Comparator.naturalOrder()).get();
			final TaskAndTime finishedTaskAndTime = lastTaskAndTime.createNext(null);
			tasksByMember[i].add(finishedTaskAndTime);
		}

		// sort the tasks by their time and fire events in correct order

		Arrays.stream(tasksByMember).flatMap(List::stream).sorted().forEach(taskAndTime -> {
			if (taskAndTime.previousTask != null) {
				fireListeners(new TimelineEvent(this, Type.TASK_FINISHED, taskAndTime.day, taskAndTime.hour,
						taskAndTime.previousTask));
			}
			if (taskAndTime.task != null) {
				fireListeners(new TimelineEvent(this, Type.TASK_STARTED, taskAndTime.day, taskAndTime.hour,
						taskAndTime.task));
			}
		});

		return Arrays.stream(tasksByMember).flatMap(List::stream).max(Comparator.naturalOrder()).get();
	}

	@Override
	public void simulationFinished(SimulationResult simulationResult) {
		fireListeners(new TimelineEvent(this, Type.SIMULATION_FINISHED, NO_DAY, NO_HOUR, simulationResult));

	}

	protected void fireListeners(TimelineEvent event) {
		for (final TimelineListener timelineListener : this.timelineListeners
				.toArray(new TimelineListener[this.timelineListeners.size()])) {
			timelineListener.eventHappened(event);
		}
	}

	public void addTimelineListener(TimelineListener timelineListener) {
		this.timelineListeners.add(timelineListener);
	}

	public void removeTimelineListener(TimelineListener timelineListener) {
		this.timelineListeners.remove(timelineListener);
	}

	/*
	 *
	 */

	static class TaskAndTime implements Comparable<TaskAndTime> {

		final Task task;
		int day;
		double hour;
		Task previousTask;

		public TaskAndTime(Task task) {
			this(task, START_DAY, START_HOUR, null);
		}

		private TaskAndTime(Task task, int day, double hour, Task previousTask) {
			this.task = task;
			this.day = day;
			this.hour = hour;
			this.previousTask = previousTask;
		}

		public TaskAndTime createNext(Task nextTask) {
			final Member member = (Member) this.task.getUserData(SprintExecutor.TASK_DATA_ASSIGNEE);
			final double taskTime = ((Double) this.task.getUserData(SprintExecutor.TASK_DATA_NECESSARY_HOURS))
					.doubleValue();

			int newDay = this.day;
			double newHour = this.hour + taskTime;

			while (newHour >= member.getWorkHoursPerDay()) {
				newHour -= member.getWorkHoursPerDay();
				newDay++;
			}
			return new TaskAndTime(nextTask, newDay, newHour, this.task);
		}

		@Override
		public int compareTo(TaskAndTime that) {
			final int compare = Integer.compare(this.day, that.day);
			if (compare != 0) return compare;
			return Double.compare(this.hour, that.hour);
		}

	}
}
