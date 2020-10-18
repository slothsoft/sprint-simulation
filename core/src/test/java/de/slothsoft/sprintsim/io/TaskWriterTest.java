package de.slothsoft.sprintsim.io;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import de.slothsoft.sprintsim.Task;
import de.slothsoft.sprintsim.generation.SprintGenerator;

public class TaskWriterTest {

	private final StringBuilder sb = new StringBuilder();
	private final TableWriter tableWriter = new LogTableWriter(s -> this.sb.append(s).append('\n'));
	private final TaskWriter taskWriter = new TaskWriter(this.tableWriter);

	@Test
	public void testWriteTasks() {
		final Task task1 = new Task();
		final Task task2 = new Task();
		final Task task3 = new Task();
		final List<Task> tasks = Arrays.asList(task1, task2, task3);

		this.taskWriter.setTaskNameSupplier(task -> String.valueOf(tasks.indexOf(task)));
		this.taskWriter.writeTasks(task1, task2, task3);

		final String expected = "               \n" + "===============\n" + "0              \n" + "1              \n"
				+ "2              \n";
		Assert.assertEquals(expected, this.sb.toString());
	}

	@Test
	@SuppressWarnings("boxing")
	public void testWriteEstimationInfo() {
		final Task task1 = new Task();
		task1.addUserData(SprintGenerator.TASK_DATA_COLLECTED_ESTIMATION, 10.0);
		task1.addUserData(SprintGenerator.TASK_DATA_MEMBER_ESTIMATIONS, new double[]{1, 2});

		final Task task2 = new Task();
		task2.addUserData(SprintGenerator.TASK_DATA_COLLECTED_ESTIMATION, 20.0);
		task2.addUserData(SprintGenerator.TASK_DATA_MEMBER_ESTIMATIONS, new double[]{3, 4});

		final Task task3 = new Task();
		task3.addUserData(SprintGenerator.TASK_DATA_COLLECTED_ESTIMATION, 30.0);
		task3.addUserData(SprintGenerator.TASK_DATA_MEMBER_ESTIMATIONS, new double[]{5, 6});

		final List<Task> tasks = Arrays.asList(task1, task2, task3);

		this.taskWriter.setTaskNameSupplier(task -> String.valueOf(tasks.indexOf(task)));
		this.taskWriter.setMemberNameSupplier(i -> "AB".substring(i, i + 1));
		this.taskWriter.writeEstimationInfo(true);
		this.taskWriter.writeTasks(task1, task2, task3);

		final String expected = "                      A              B             All      \n"
				+ "============================================================\n"
				+ "0                            1              2             10\n"
				+ "1                            3              4             20\n"
				+ "2                            5              6             30\n";
		Assert.assertEquals(expected, this.sb.toString());
	}
}
