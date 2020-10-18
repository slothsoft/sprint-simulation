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
import de.slothsoft.sprintsim.impl.ArrayToArrayMap;

public class SprintGenerator {

	private Member[] members;
	Random rnd = new Random();
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
		final List<double[]> taskEstimations = new ArrayList<>();
		final List<Double> collectedTaskEstimations = new ArrayList<>();

		while (entireWorkHours < targetWorkHours) {
			final Task task = createTask();
			final double[] hoursArray = estimateTask(task);
			final double hours = collectEstimations(hoursArray);

			taskEstimations.add(hoursArray);
			collectedTaskEstimations.add(Double.valueOf(hours));
			tasks.add(task);
			entireWorkHours += hours;
		}

		final Task[] tasksArray = tasks.toArray(new Task[tasks.size()]);
		final ArrayToArrayMap<Task, ArrayToArrayMap<Member, Double>> taskEstimationsMap = createTaskEstimationsMap(
				taskEstimations, tasksArray);
		final ArrayToArrayMap<Task, Double> collectedTaskEstimationsMap = new ArrayToArrayMap<Task, Double>(tasksArray)
				.values(collectedTaskEstimations.toArray(new Double[collectedTaskEstimations.size()]));

		return new SprintPlanning(new Sprint(tasksArray), entireWorkHours,
				Math.max(0, entireWorkHours - targetWorkHours), taskEstimationsMap, collectedTaskEstimationsMap);
	}

	private ArrayToArrayMap<Task, ArrayToArrayMap<Member, Double>> createTaskEstimationsMap(
			final List<double[]> taskEstimations, final Task[] tasksArray) {
		return new ArrayToArrayMap<Task, ArrayToArrayMap<Member, Double>>(tasksArray)
				.values(taskEstimations.stream()
						.map(e -> new ArrayToArrayMap<Member, Double>(this.members)
								.values(Arrays.stream(e).boxed().toArray(Double[]::new)))
						.toArray(ArrayToArrayMap[]::new));
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
		final int baseHours = this.taskConfig.getHours(task.getComplexity());
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

}
