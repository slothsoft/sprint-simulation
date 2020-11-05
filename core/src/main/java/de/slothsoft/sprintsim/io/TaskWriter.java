package de.slothsoft.sprintsim.io;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.IntFunction;

import de.slothsoft.sprintsim.Task;
import de.slothsoft.sprintsim.execution.SprintExecutor;
import de.slothsoft.sprintsim.generation.SprintGenerator;
import de.slothsoft.sprintsim.io.ComponentWriter.TableInfo;

public class TaskWriter {

	public static final Function<Task, String> DEFAULT_TASK_NAME_SUPPLIER = task -> "TASK-" //$NON-NLS-1$
			+ String.valueOf(task.hashCode());

	private final ComponentWriter componentWriter;

	private IntFunction<String> memberNameSupplier = i -> String.valueOf(i);
	private Function<Task, String> taskNameSupplier = DEFAULT_TASK_NAME_SUPPLIER;

	private boolean writeEstimationInfo;
	private boolean writeExecutionInfo;

	public TaskWriter(ComponentWriter componentWriter) {
		this.componentWriter = Objects.requireNonNull(componentWriter);
	}

	public void writeTasks(Task... tasks) {
		this.componentWriter.startTable(new TableInfo());

		writeTasksHeaders(tasks[0]);
		writeTaskLines(tasks);

		this.componentWriter.endTable();
	}

	private void writeTasksHeaders(Task task) {
		final List<String> headers = new ArrayList<>();
		headers.add(null);

		if (this.writeEstimationInfo) {
			final double[] memberEstimations = (double[]) task
					.getUserData(SprintGenerator.TASK_DATA_MEMBER_ESTIMATIONS);
			for (int i = 0; i < memberEstimations.length; i++) {
				headers.add(this.memberNameSupplier.apply(i));
			}
			headers.add(Messages.getString("All")); //$NON-NLS-1$
		}

		if (this.writeExecutionInfo) {
			if (this.writeEstimationInfo) {
				headers.add(null);
			}
			headers.add(Messages.getString("Assignee")); //$NON-NLS-1$
			headers.add(Messages.getString("NecessaryTime")); //$NON-NLS-1$
		}

		this.componentWriter.writeTableHeader(headers.toArray(new String[headers.size()]));
	}

	private void writeTaskLines(Task[] tasks) {
		for (final Task task : tasks) {
			writeTaskLine(task);
		}
	}

	private void writeTaskLine(Task task) {
		final List<Object> cells = new ArrayList<>();
		cells.add(this.taskNameSupplier.apply(task));

		if (this.writeEstimationInfo) {
			final double[] memberEstimations = (double[]) task
					.getUserData(SprintGenerator.TASK_DATA_MEMBER_ESTIMATIONS);
			for (int i = 0; i < memberEstimations.length; i++) {
				cells.add(Double.valueOf(memberEstimations[i]));
			}
			cells.add(task.getUserData(SprintGenerator.TASK_DATA_COLLECTED_ESTIMATION));
		}

		if (this.writeExecutionInfo) {
			if (this.writeEstimationInfo) {
				cells.add(null);
			}
			final int memberIndex = ((Integer) task.getUserData(SprintExecutor.TASK_DATA_ASSIGNEE_INDEX)).intValue();
			cells.add(this.memberNameSupplier.apply(memberIndex));
			cells.add(task.getUserData(SprintExecutor.TASK_DATA_NECESSARY_HOURS));
		}

		this.componentWriter.writeTableLine(cells.toArray());
	}

	public IntFunction<String> getMemberNameSupplier() {
		return this.memberNameSupplier;
	}

	public TaskWriter memberNameSupplier(IntFunction<String> newMemberNameSupplier) {
		setMemberNameSupplier(newMemberNameSupplier);
		return this;
	}

	public void setMemberNameSupplier(IntFunction<String> memberNameSupplier) {
		this.memberNameSupplier = Objects.requireNonNull(memberNameSupplier);
	}

	public Function<Task, String> getTaskNameSupplier() {
		return this.taskNameSupplier;
	}

	public TaskWriter taskNameSupplier(Function<Task, String> newTaskNameSupplier) {
		setTaskNameSupplier(newTaskNameSupplier);
		return this;
	}

	public void setTaskNameSupplier(Function<Task, String> taskNameSupplier) {
		this.taskNameSupplier = Objects.requireNonNull(taskNameSupplier);
	}

	public boolean isWriteEstimationInfo() {
		return this.writeEstimationInfo;
	}

	public TaskWriter writeEstimationInfo(boolean newWriteEstimationInfo) {
		setWriteEstimationInfo(newWriteEstimationInfo);
		return this;
	}

	public void setWriteEstimationInfo(boolean writeEstimationInfo) {
		this.writeEstimationInfo = writeEstimationInfo;
	}

	public boolean isWriteExecutionInfo() {
		return this.writeExecutionInfo;
	}

	public TaskWriter writeExecutionInfo(boolean newWriteExecutionInfo) {
		setWriteExecutionInfo(newWriteExecutionInfo);
		return this;
	}

	public void setWriteExecutionInfo(boolean writeExecutionInfo) {
		this.writeExecutionInfo = writeExecutionInfo;
	}

}
