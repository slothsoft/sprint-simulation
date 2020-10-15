package de.slothsoft.sprintsim;

import java.util.Objects;

public class Task {

	Complexity complexity = Complexity.MEDIUM;

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

}
