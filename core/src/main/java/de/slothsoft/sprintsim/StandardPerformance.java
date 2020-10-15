package de.slothsoft.sprintsim;

import de.slothsoft.sprintsim.Task;

public enum StandardPerformance implements Performance {

	JUNIOR {

		@Override
		public double getMultiplicator(Task task) {
			return 2.0;
		}
	},
	
	NORMAL {
		
		@Override
		public double getMultiplicator(Task task) {
			return 1.0;
		}
	},
	
	SENIOR {

		@Override
		public double getMultiplicator(Task task) {
			return 0.7;
		}
	},
	
	;

}
