package de.slothsoft.sprintsim;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Sprint implements HasUserData {

	Task[] tasks;
	int lengthInDays = 10;

	Map<String, Object> userData = new HashMap<>();

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

	public void addUserData(String key, Object value) {
		this.userData.put(key, value);
	}

	public void removeUserData(String key) {
		this.userData.remove(key);
	}

	@Override
	public Object getUserData(String key) {
		return this.userData.get(key);
	}

}
