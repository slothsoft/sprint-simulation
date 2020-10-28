package de.slothsoft.sprintsim.config;

import de.slothsoft.sprintsim.Complexity;

public class TaskConfig {

	int lowComplexityHours = 2;
	int mediumComplexityHours = 8;
	int highComplexityHours = 16;

	public int getHours(Complexity complexity) {
		switch (complexity) {
			case HIGH :
				return this.highComplexityHours;
			case MEDIUM :
				return this.mediumComplexityHours;
			case LOW :
				return this.lowComplexityHours;
			default :
				throw new IllegalArgumentException("Do not know " + complexity + "!"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	public int getHighComplexityHours() {
		return this.highComplexityHours;
	}

	public TaskConfig highComplexityHours(int newHighComplexityHours) {
		setHighComplexityHours(newHighComplexityHours);
		return this;
	}

	public void setHighComplexityHours(int highComplexityHours) {
		this.highComplexityHours = highComplexityHours;
	}

	public int getLowComplexityHours() {
		return this.lowComplexityHours;
	}

	public TaskConfig lowComplexityHours(int newLowComplexityHours) {
		setLowComplexityHours(newLowComplexityHours);
		return this;
	}

	public void setLowComplexityHours(int lowComplexityHours) {
		this.lowComplexityHours = lowComplexityHours;
	}

	public int getMediumComplexityHours() {
		return this.mediumComplexityHours;
	}

	public TaskConfig mediumComplexityHours(int newMediumComplexityHours) {
		setMediumComplexityHours(newMediumComplexityHours);
		return this;
	}

	public void setMediumComplexityHours(int mediumComplexityHours) {
		this.mediumComplexityHours = mediumComplexityHours;
	}

}
