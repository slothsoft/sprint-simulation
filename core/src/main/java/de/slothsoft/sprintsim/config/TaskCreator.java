package de.slothsoft.sprintsim.config;

import java.util.Objects;
import java.util.function.Supplier;

import de.slothsoft.sprintsim.Task;

public class TaskCreator {

	double probability = 1;
	Supplier<Task> constructor = Task::new;

	public Supplier<Task> getConstructor() {
		return this.constructor;
	}

	public TaskCreator constructor(Supplier<Task> newConstructor) {
		setConstructor(newConstructor);
		return this;
	}

	public void setConstructor(Supplier<Task> constructor) {
		this.constructor = Objects.requireNonNull(constructor);
	}

	public double getProbability() {
		return this.probability;
	}

	public TaskCreator probability(double newProbability) {
		setProbability(newProbability);
		return this;
	}

	public void setProbability(double probability) {
		this.probability = probability;
	}

}
