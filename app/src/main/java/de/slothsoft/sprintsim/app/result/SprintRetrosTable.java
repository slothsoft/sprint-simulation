package de.slothsoft.sprintsim.app.result;

import java.util.Arrays;
import java.util.function.ToDoubleFunction;
import java.util.stream.IntStream;

import com.vaadin.flow.component.grid.Grid.Column;
import com.vaadin.flow.function.ValueProvider;

import de.slothsoft.sprintsim.HasUserData;
import de.slothsoft.sprintsim.Member;
import de.slothsoft.sprintsim.Sprint;
import de.slothsoft.sprintsim.app.UserDataTable;
import de.slothsoft.sprintsim.execution.SprintRetro;
import de.slothsoft.sprintsim.generation.SprintPlanning;
import de.slothsoft.sprintsim.simulation.PrettyNames;

public class SprintRetrosTable extends UserDataTable<SprintRetrosTable.PlanningAndRetro> {

	private static final long serialVersionUID = -2213857423153670841L;

	static class PlanningAndRetro implements HasUserData {

		SprintPlanning planning;
		SprintRetro retro;

		public PlanningAndRetro(SprintPlanning planning, SprintRetro retro) {
			this.planning = planning;
			this.retro = retro;
		}

		@Override
		public Object getUserData(String key) {
			return this.planning.getSprint().getUserData(key);
		}

	}

	static class AveragePlanningAndRetro extends PlanningAndRetro {

		public AveragePlanningAndRetro(SprintPlanning[] plannings, SprintRetro[] retros) {
			super(createAverage(plannings), createAverage(retros));
		}

		private static SprintPlanning createAverage(SprintPlanning[] plannings) {
			if (plannings.length == 0) {
				return new SprintPlanning(new Sprint(), 0, 0);
			}
			return new SprintPlanning(plannings[0].getSprint(),
					calculateAverage(plannings, SprintPlanning::getEstimatedHours),
					calculateAverage(plannings, SprintPlanning::getEstimatedAdditionalHours));
		}

		private static <T> double calculateAverage(T[] retros, ToDoubleFunction<T> getter) {
			return Arrays.stream(retros).mapToDouble(getter).average().getAsDouble();
		}

		private static SprintRetro createAverage(SprintRetro[] retros) {
			if (retros.length == 0) {
				return new SprintRetro(new Sprint(), new Member[0], 0, 0);
			}
			return new SprintRetro(retros[0].getSprint(), retros[0].getMembers(),
					calculateAverage(retros, SprintRetro::getNecessaryAdditionalHours),
					calculateAverage(retros, SprintRetro::getRemainingHours));
		}

		@Override
		public Object getUserData(String key) {
			if (PrettyNames.DATA_NAME.equals(key)) {
				return Messages.getString("Average");
			}
			return null;
		}

	}

	public SprintRetrosTable() {
		super(new PlanningAndRetro[0]);
		addClassName("sprint-retros-table");
	}

	@Override
	protected void addColumns() {
		super.addColumns();

		addColumn(Messages.getString("EstimatedHours"), par -> par.planning.getEstimatedHours());
		addColumn(Messages.getString("EstimatedAdditionalHours"), par -> par.planning.getEstimatedAdditionalHours());
		addColumn(Messages.getString("RemainingHours"), par -> par.retro.getRemainingHours());
		addColumn(Messages.getString("NecessaryAdditionalHours"), par -> par.retro.getNecessaryAdditionalHours());
	}

	private Column<PlanningAndRetro> addColumn(String header, ValueProvider<PlanningAndRetro, Double> valueProvider) {
		final Column<PlanningAndRetro> column = this.grid.addColumn(valueProvider);
		column.setComparator(valueProvider);
		column.setHeader(header);
		return column;
	}

	@Override
	public SprintRetrosTable model(PlanningAndRetro[] newModel) {
		setModel(newModel);
		return this;
	}

	public SprintRetrosTable model(SprintPlanning[] plannings, SprintRetro[] retros) {
		setModel(plannings, retros);
		return this;
	}

	public void setModel(SprintPlanning[] plannings, SprintRetro[] retros) {
		super.setModel(IntStream.range(0, plannings.length + 1).mapToObj(i -> {
			if (i < plannings.length) {
				return new PlanningAndRetro(plannings[i], retros[i]);
			}
			// average row
			return new AveragePlanningAndRetro(plannings, retros);
		}).toArray(PlanningAndRetro[]::new));
	}

	@Override
	public SprintRetrosTable showNameColumn(boolean newShowNameColumn) {
		setShowNameColumn(newShowNameColumn);
		return this;
	}

}
