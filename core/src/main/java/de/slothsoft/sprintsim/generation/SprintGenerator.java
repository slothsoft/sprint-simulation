package de.slothsoft.sprintsim.generation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import de.slothsoft.sprintsim.Member;
import de.slothsoft.sprintsim.Sprint;
import de.slothsoft.sprintsim.Task;
import de.slothsoft.sprintsim.config.SprintConfig;
import de.slothsoft.sprintsim.config.TaskConfig;
import de.slothsoft.sprintsim.config.TaskCreator;

public class SprintGenerator {

	/** An array of the member's estimations - a <code>double[]</code>. */
	public static final String TASK_DATA_MEMBER_ESTIMATIONS = "memberEstimations"; //$NON-NLS-1$
	/** The final estimation of a task - a <code>double</code>. */
	public static final String TASK_DATA_COLLECTED_ESTIMATION = "collectedEstimation"; //$NON-NLS-1$

	private Member[] members;
	private Long seed;
	private Random rnd = new Random();
	private TaskConfig taskConfig = new TaskConfig();
	private SprintConfig sprintConfig = SprintConfig.createDefault();

	public SprintGenerator(Member... members) {
		this.members = Objects.requireNonNull(members);
	}

	public SprintPlanning generate() {
		final double targetWorkHours = Arrays.stream(this.members).mapToDouble(Member::getWorkHoursPerDay).sum()
				* this.sprintConfig.getLengthInDays();

		double entireWorkHours = 0;
		final List<Task> tasks = new ArrayList<>();

		while (entireWorkHours < targetWorkHours) {
			final Task task = createTask();
			final double[] hoursArray = estimateTask(task);
			final double hours = collectEstimations(hoursArray);

			task.addUserData(TASK_DATA_MEMBER_ESTIMATIONS, hoursArray);
			task.addUserData(TASK_DATA_COLLECTED_ESTIMATION, Double.valueOf(hours));

			tasks.add(task);
			entireWorkHours += hours;
		}
		return new SprintPlanning(new Sprint(tasks.toArray(new Task[tasks.size()])), entireWorkHours,
				Math.max(0, entireWorkHours - targetWorkHours));
	}

	Task createTask() {
		final double probabilitySum = Arrays.stream(this.sprintConfig.getTaskCreators())
				.mapToDouble(TaskCreator::getProbability).sum();
		final double taskType = this.rnd.nextDouble() * probabilitySum;
		double currentProbability = 0;

		for (final TaskCreator taskCreator : this.sprintConfig.getTaskCreators()) {
			currentProbability += taskCreator.getProbability();
			if (taskType <= currentProbability) return taskCreator.getConstructor().get();
		}

		// cannot really happen except if no task creators are given
		return new Task();
	}

	double[] estimateTask(Task task) {
		final double[] estimations = new double[this.members.length];
		for (int i = 0; i < estimations.length; i++) {
			estimations[i] = estimateTaskFromMember(task, this.members[i]);
		}
		return estimations;
	}

	double estimateTaskFromMember(Task task, Member member) {
		final double baseHours = this.taskConfig.getHours(task.getComplexity())
				* member.getWorkPerformance().getMultiplicator(task);
		double deviation = member.getEstimationDeviation() * this.rnd.nextGaussian() * 0.7;
		deviation = Math.max(-member.getEstimationDeviation(), Math.min(member.getEstimationDeviation(), deviation));
		return baseHours + deviation * baseHours;
	}

	// TODO: allow other operations besides average (max and median)

	private static double collectEstimations(double[] estimations) {
		return Arrays.stream(estimations).average().getAsDouble();
	}

	public Member[] getMembers() {
		return this.members;
	}

	public SprintGenerator members(Member... newMembers) {
		setMembers(newMembers);
		return this;
	}

	public void setMembers(Member... members) {
		this.members = Objects.requireNonNull(members);
	}

	public TaskConfig getTaskConfig() {
		return this.taskConfig;
	}

	public SprintGenerator taskConfig(TaskConfig newTaskConfig) {
		setTaskConfig(newTaskConfig);
		return this;
	}

	public void setTaskConfig(TaskConfig taskConfig) {
		this.taskConfig = Objects.requireNonNull(taskConfig);
	}

	public SprintConfig getSprintConfig() {
		return this.sprintConfig;
	}

	public SprintGenerator sprintConfig(SprintConfig newSprintConfig) {
		setSprintConfig(newSprintConfig);
		return this;
	}

	public void setSprintConfig(SprintConfig sprintConfig) {
		this.sprintConfig = Objects.requireNonNull(sprintConfig);
	}

	public Long getSeed() {
		return seed;
	}

	public SprintGenerator seed(Long newSeed) {
		setSeed(newSeed);
		return this;
	}
	

	public void setSeed(Long seed) {
		this.seed = seed;
		this.rnd = seed == null ? new Random() : new Random(seed.longValue());
	}
	
	

}
