package de.slothsoft.sprintsim;

import java.util.Objects;

public class Sprint {

	Task[] tasks;
	int lengthInDays = 10;

	public Sprint(Task... tasks) {
		this.tasks = Objects.requireNonNull(tasks);
	}

	public Task[] getTasks() {
		return this.tasks;
	}

	public Sprint tasks(Task... newTasks) {
		setTasks(newTasks);
		return this;
	}

	public void setTasks(Task... tasks) {
		this.tasks = Objects.requireNonNull(tasks);
	}

	public int getLengthInDays() {
		return this.lengthInDays;
	}

	public Sprint lengthInDays(int newLengthInDays) {
		setLengthInDays(newLengthInDays);
		return this;
	}

	public void setLengthInDays(int lengthInDays) {
		this.lengthInDays = lengthInDays;
	}

}
