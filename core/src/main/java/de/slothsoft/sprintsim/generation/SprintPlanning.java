package de.slothsoft.sprintsim.generation;

import java.util.Objects;

import de.slothsoft.sprintsim.Member;
import de.slothsoft.sprintsim.Sprint;
import de.slothsoft.sprintsim.Task;
import de.slothsoft.sprintsim.impl.ArrayToArrayMap;

public class SprintPlanning {

	final Sprint sprint;
	final double estimatedHours;
	final double estimatedAdditionalHours;
	final ArrayToArrayMap<Task, ArrayToArrayMap<Member, Double>> taskEstimations;
	final ArrayToArrayMap<Task, Double> collectedTaskEstimationsMap;

	SprintPlanning(Sprint sprint, double estimatedHours, double estimatedAdditionalHours,
			ArrayToArrayMap<Task, ArrayToArrayMap<Member, Double>> taskEstimations,
			ArrayToArrayMap<Task, Double> collectedTaskEstimationsMap) {
		this.sprint = Objects.requireNonNull(sprint);
		this.estimatedHours = estimatedHours;
		this.estimatedAdditionalHours = estimatedAdditionalHours;
		this.taskEstimations = taskEstimations;
		this.collectedTaskEstimationsMap = collectedTaskEstimationsMap;
	}

	public Sprint getSprint() {
		return this.sprint;
	}

	public double getEstimatedHours() {
		return this.estimatedHours;
	}

	public double getEstimatedAdditionalHours() {
		return this.estimatedAdditionalHours;
	}

	public double getTaskEstimation(Task task, Member member) {
		return this.taskEstimations.getValue(task).getValue(member).doubleValue();
	}

	public double getCollectedTaskEstimation(Task task) {
		return this.collectedTaskEstimationsMap.getValue(task).doubleValue();
	}

}
