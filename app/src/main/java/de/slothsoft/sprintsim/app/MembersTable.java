package de.slothsoft.sprintsim.app;

import com.vaadin.flow.component.grid.Grid.Column;

import de.slothsoft.sprintsim.Member;
import de.slothsoft.sprintsim.StandardPerformance;

public class MembersTable extends UserDataTable<Member> {

	private static final long serialVersionUID = 9223215756593222829L;

	public MembersTable() {
		super(SimulationBuilder.createDefaultMembers());
		addClassName("members-table");
	}

	@Override
	protected void addColumns() {
		super.addColumns();

		addWorkPerformanceColumn();
		addEstimationDeviation();
		addWorkHoursPerDay();
	}

	private void addWorkPerformanceColumn() {
		final Column<Member> column = this.grid.addColumn(new PerformanceRenderer<>(Member::getWorkPerformance));
		column.setComparator(member -> (StandardPerformance) member.getWorkPerformance());
		column.setHeader(Messages.getString("WorkPerformance"));
	}

	private void addEstimationDeviation() {
		final Column<Member> column = this.grid.addColumn(Member::getEstimationDeviation);
		column.setComparator(Member::getEstimationDeviation);
		column.setHeader(Messages.getString("EstimationDeviation"));
	}

	private void addWorkHoursPerDay() {
		final Column<Member> column = this.grid.addColumn(Member::getWorkHoursPerDay);
		column.setComparator(Member::getWorkHoursPerDay);
		column.setHeader(Messages.getString("WorkHoursPerDay"));
	}

	@Override
	public MembersTable model(Member[] newModel) {
		setModel(newModel);
		return this;
	}

	@Override
	public MembersTable showNameColumn(boolean newShowNameColumn) {
		setShowNameColumn(newShowNameColumn);
		return this;
	}

}
