package de.slothsoft.sprintsim.simulation;

import java.util.EventObject;

import de.slothsoft.sprintsim.Task;
import de.slothsoft.sprintsim.execution.SprintRetro;
import de.slothsoft.sprintsim.generation.SprintPlanning;

public class TimelineEvent extends EventObject {

	private static final long serialVersionUID = -634659250042100866L;

	public enum Type {

		/** Data is {@link SimulationInfo}. */

		SIMULATION_STARTED,

		/** Data is {@link SprintPlanning}. */

		SPRINT_STARTED,

		/** Data is {@link Task}. */

		TASK_STARTED,

		/** Data is {@link Task}. */

		TASK_FINISHED,

		/** Data is {@link SprintRetro}. */

		SPRINT_FINISHED,

		/** Data is {@link SimulationResult}. */

		SIMULATION_FINISHED;
	}

	final Type type;
	final int day;
	final double hour;
	final Object data;

	TimelineEvent(Object source, Type type, int day, double hour, Object data) {
		super(source);
		this.type = type;
		this.day = day;
		this.hour = hour;
		this.data = data;
	}

	public Type getType() {
		return this.type;
	}

	/**
	 * Days start at 1.
	 */

	public int getDay() {
		return this.day;
	}

	/**
	 * Hours start at 0.
	 */

	public double getHour() {
		return this.hour;
	}

	public Object getData() {
		return this.data;
	}

	@Override
	public String toString() {
		return "TimelineEvent [type=" + this.type + ", day=" + this.day + ", hour=" + this.hour + "]";
	}

}
