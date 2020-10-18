package de.slothsoft.sprintsim.execution;

import java.util.Objects;

import de.slothsoft.sprintsim.Member;
import de.slothsoft.sprintsim.Sprint;

public class SprintRetro {

	final Sprint sprint;
	final Member[] members;
	final double necessaryAdditionalHours;
	final double remainingHours;

	SprintRetro(Sprint sprint, Member[] members, double necessaryAdditionalHours, double remainingHours) {
		this.sprint = Objects.requireNonNull(sprint);
		this.members = Objects.requireNonNull(members);
		this.necessaryAdditionalHours = necessaryAdditionalHours;
		this.remainingHours = remainingHours;
	}

	/**
	 * Returns the additional hours necessary to finish the sprint. If one team member
	 * needs 2 more hours, will return 2. If one team member needs 2 more hours, and
	 * another has 2 remaining, will still return 2, because that is not how real life
	 * works. If all members finished their tasks in the sprint, will return 0. Cannot
	 * return a negative value.
	 *
	 * @return necessary additional hours
	 */

	public double getNecessaryAdditionalHours() {
		return this.necessaryAdditionalHours;
	}

	/**
	 * Returns the hours that team members could not spent in the sprint, because there
	 * were no more issues. If one team member has 2 hours remaining, will return 2. If
	 * one team member has 2 hours remaining, and another has 2 too much, will still
	 * return 2, because that is not how real life works. If all members worked until the
	 * sprint was over, will return 0. Cannot return a negative value.
	 *
	 * @return necessary additional hours
	 */

	public double getRemainingHours() {
		return this.remainingHours;
	}

	public Sprint getSprint() {
		return this.sprint;
	}

}
