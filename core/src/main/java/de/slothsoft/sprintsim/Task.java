package de.slothsoft.sprintsim;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Task {

	Complexity complexity = Complexity.MEDIUM;

	Map<String, Object> userData = new HashMap<>();

	public Complexity getComplexity() {
		return this.complexity;
	}

	public Task complexity(Complexity newComplexity) {
		setComplexity(newComplexity);
		return this;
	}

	public void setComplexity(Complexity complexity) {
		this.complexity = Objects.requireNonNull(complexity);
	}

	public void addUserData(String key, Object value) {
		this.userData.put(key, value);
	}

	public void removeUserData(String key) {
		this.userData.remove(key);
	}

	public Object getUserData(String key) {
		return this.userData.get(key);
	}
}
