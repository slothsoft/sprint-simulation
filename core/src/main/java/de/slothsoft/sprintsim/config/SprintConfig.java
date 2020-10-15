package de.slothsoft.sprintsim.config;

import java.util.Arrays;
import java.util.Objects;

import de.slothsoft.sprintsim.Complexity;
import de.slothsoft.sprintsim.Task;

public class SprintConfig {

	public static SprintConfig createDefault() {
		return new SprintConfig(Arrays.stream(Complexity.values())
				.map(c -> new TaskCreator().constructor(() -> new Task().complexity(c)))
				.toArray(TaskCreator[]::new));
	}

	int lengthInDays = 10;
	TaskCreator[] taskCreators;

	public SprintConfig(TaskCreator... taskCreators) {
		this.taskCreators = Objects.requireNonNull(taskCreators);
	}

	public TaskCreator[] getTaskCreators() {
		return this.taskCreators;
	}

	public SprintConfig taskCreators(TaskCreator... newTaskCreators) {
		setTaskCreators(newTaskCreators);
		return this;
	}

	public void setTaskCreators(TaskCreator... taskCreators) {
		this.taskCreators = Objects.requireNonNull(taskCreators);
	}

	public int getLengthInDays() {
		return this.lengthInDays;
	}

	public SprintConfig lengthInDays(int newLengthInDays) {
		setLengthInDays(newLengthInDays);
		return this;
	}

	public void setLengthInDays(int lengthInDays) {
		this.lengthInDays = lengthInDays;
	}

}
