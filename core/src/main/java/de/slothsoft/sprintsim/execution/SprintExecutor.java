package de.slothsoft.sprintsim.execution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

import de.slothsoft.sprintsim.Member;
import de.slothsoft.sprintsim.Sprint;
import de.slothsoft.sprintsim.Task;
import de.slothsoft.sprintsim.config.TaskConfig;

public class SprintExecutor {

	private Member[] members;
	private TaskConfig taskConfig = new TaskConfig();

	public SprintExecutor(Member... members) {
		this.members = Objects.requireNonNull(members);
	}

	public SprintRetro execute(Sprint sprint) {
		final List<Task> tasksToDo = new ArrayList<>(Arrays.asList(sprint.getTasks()));
		tasksToDo.sort(Comparator.comparing(Task::getComplexity)); // we do long tasks
																	// first

		if (this.members.length == 0) return new SprintRetro(this.members, Double.NaN, Double.NaN);

		final double[] workHours = generateWorkHours(sprint.getLengthInDays());
		for (final Task task : tasksToDo) {
			final int indexOfIdleMember = IntStream.range(0, this.members.length).boxed()
					.max(Comparator.comparingDouble(index -> workHours[index.intValue()])).get().intValue();
			final Member idleMember = this.members[indexOfIdleMember];

			final double taskBaseHours = this.taskConfig.getHours(task.getComplexity());
			final double taskHours = taskBaseHours * idleMember.getWorkPerformance().getMultiplicator(task);

			// remove the hours
			workHours[indexOfIdleMember] -= taskHours;
		}
		final double necessaryAdditionalHours = -DoubleStream.of(workHours).filter(d -> d < 0).sum();
		final double remainingHours = DoubleStream.of(workHours).filter(d -> d > 0).sum();
		return new SprintRetro(this.members, necessaryAdditionalHours, remainingHours);
	}

	double[] generateWorkHours(int lengthInDays) {
		final double[] result = new double[this.members.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = lengthInDays * this.members[i].getWorkHoursPerDay();
		}
		return result;
	}

	public Member[] getMembers() {
		return this.members;
	}

	public SprintExecutor members(Member... newMembers) {
		setMembers(newMembers);
		return this;
	}

	public void setMembers(Member... members) {
		this.members = Objects.requireNonNull(members);
	}

	public TaskConfig getTaskConfig() {
		return this.taskConfig;
	}

	public SprintExecutor taskConfig(TaskConfig newTaskConfig) {
		setTaskConfig(newTaskConfig);
		return this;
	}

	public void setTaskConfig(TaskConfig taskConfig) {
		this.taskConfig = Objects.requireNonNull(taskConfig);
	}

}
