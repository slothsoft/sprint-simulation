package de.slothsoft.sprintsim.simulation;

import de.slothsoft.sprintsim.Member;
import de.slothsoft.sprintsim.Task;

public final class PrettyNames {

	public static class TaskNamingInfo {

		String taskIdPrefix = "LIO"; //$NON-NLS-1$
		int firstIndex;

		public String getTaskIdPrefix() {
			return this.taskIdPrefix;
		}

		public TaskNamingInfo taskIdPrefix(String newTaskIdPrefix) {
			setTaskIdPrefix(newTaskIdPrefix);
			return this;
		}

		public void setTaskIdPrefix(String taskIdPrefix) {
			this.taskIdPrefix = taskIdPrefix;
		}

		public int getFirstIndex() {
			return this.firstIndex;
		}

		public TaskNamingInfo firstIndex(int newFirstIndex) {
			setFirstIndex(newFirstIndex);
			return this;
		}

		public void setFirstIndex(int firstIndex) {
			this.firstIndex = firstIndex;
		}
	}

	private static final String[] MEMBER_NAMES = {"Angie", "Bert", "Charles", "David", "Emil", "Francis", "Gert", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$
			"Hans", "Ike", "James", "Klaus", "Lars", "Markus", "Norbert", "Olaf", "Paul", "Quentin", "Ralf", "Steffi", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$ //$NON-NLS-10$ //$NON-NLS-11$ //$NON-NLS-12$
			"Tony", "Ulf", "Viktor", "Wolfgang", "Xerox", "Yens", "Zack"}; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$
	private static final int FIRST_TASK = 31415;

	/** A <code>String</code> for easier story telling. */
	public static final String DATA_NAME = "name"; //$NON-NLS-1$

	public static void appendMemberNames(Member[] members) {
		for (int i = 0; i < members.length; i++) {
			if (members[i].getUserData(DATA_NAME) == null) {
				members[i].addUserData(DATA_NAME, createMemberName(i));
			}
		}
	}

	static String createMemberName(int index) {
		final int memberNameIndex = index % MEMBER_NAMES.length;
		final int numberWithSameName = index / MEMBER_NAMES.length;
		return MEMBER_NAMES[memberNameIndex]
				+ (numberWithSameName > 0 ? ' ' + String.valueOf(numberWithSameName + 1) : ""); //$NON-NLS-1$
	}

	public static void appendTaskNames(Task[] tasks) {
		appendTaskNames(tasks, new TaskNamingInfo());
	}

	public static void appendTaskNames(Task[] tasks, TaskNamingInfo info) {
		for (int i = 0; i < tasks.length; i++) {
			if (tasks[i].getUserData(DATA_NAME) == null) {
				tasks[i].addUserData(DATA_NAME, createTaskName(info, i));
			}
		}
	}

	static String createTaskName(TaskNamingInfo info, int index) {
		return info.taskIdPrefix + '-' + String.valueOf(FIRST_TASK + info.firstIndex + index);
	}

	private PrettyNames() {
		// hide me
	}
}
