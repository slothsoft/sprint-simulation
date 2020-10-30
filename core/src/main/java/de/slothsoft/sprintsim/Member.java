package de.slothsoft.sprintsim;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Member {

	public static final Member createJunior() {
		return new Member().workPerformance(StandardPerformance.JUNIOR).estimationDeviation(0.5);
	}

	public static final Member createNormal() {
		return new Member().workPerformance(StandardPerformance.NORMAL).estimationDeviation(0.1);
	}

	public static final Member createSenior() {
		return new Member().workPerformance(StandardPerformance.SENIOR).estimationDeviation(0.0);
	}

	Performance workPerformance = StandardPerformance.NORMAL;
	double estimationDeviation = 0.0;
	int workHoursPerDay = 8;

	Map<String, Object> userData = new HashMap<>();

	public double getEstimationDeviation() {
		return this.estimationDeviation;
	}

	public Member estimationDeviation(double newEstimationDeviation) {
		setEstimationDeviation(newEstimationDeviation);
		return this;
	}

	public void setEstimationDeviation(double estimationDeviation) {
		this.estimationDeviation = estimationDeviation;
	}

	public int getWorkHoursPerDay() {
		return this.workHoursPerDay;
	}

	public Member workHoursPerDay(int newWorkHoursPerDay) {
		setWorkHoursPerDay(newWorkHoursPerDay);
		return this;
	}

	public void setWorkHoursPerDay(int workHoursPerDay) {
		this.workHoursPerDay = workHoursPerDay;
	}

	public Performance getWorkPerformance() {
		return this.workPerformance;
	}

	public Member workPerformance(Performance newWorkPerformance) {
		setWorkPerformance(newWorkPerformance);
		return this;
	}

	public void setWorkPerformance(Performance workPerformance) {
		this.workPerformance = Objects.requireNonNull(workPerformance);
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
