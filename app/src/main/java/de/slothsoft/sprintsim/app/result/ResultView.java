package de.slothsoft.sprintsim.app.result;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import de.slothsoft.sprintsim.app.HasRunButton;
import de.slothsoft.sprintsim.app.MainView;
import de.slothsoft.sprintsim.app.MembersTable;
import de.slothsoft.sprintsim.app.SessionInstance;
import de.slothsoft.sprintsim.simulation.PrettyNames;
import de.slothsoft.sprintsim.simulation.SimulationResult;

@Route(value = "result", layout = MainView.class)
@CssImport("./styles/shared.css")
public class ResultView extends VerticalLayout implements HasRunButton<Button> {

	private static final long serialVersionUID = -8243165312789523979L;

	private static final SessionInstance<SimulationResult> RESULT = new SessionInstance<>(() -> null);

	public static SimulationResult getResultForCurrentSession() {
		return RESULT.getForCurrentSession();
	}

	public static void setResultForCurrentSession(SimulationResult result) {
		RESULT.setForCurrentSession(result);
	}

	private SimulationResult model;

	public ResultView() {
		this.model = getResultForCurrentSession();

		createComponents();
	}

	private void createComponents() {
		if (this.model != null) {
			PrettyNames.appendMemberNames(this.model.getMembers());

			add(new Header(new Label(Messages.getString("Members"))));

			final MembersTable membersTable = new MembersTable();
			membersTable.setSizeFull();
			membersTable.setModel(this.model.getMembers());
			addTableWithWrapper(membersTable, "10em");

			add(new Header(new Label(Messages.getString("Sprints"))));

			final SprintRetrosTable sprintRetrosTable = new SprintRetrosTable();
			sprintRetrosTable.setSizeFull();
			sprintRetrosTable.setModel(this.model.getPlannings(), this.model.getRetros());
			addTableWithWrapper(sprintRetrosTable, "20em");

		} else {
			add(new Label(Messages.getString("NoResultYet")));
		}
	}

	private void addTableWithWrapper(final Component table, String height) {
		final Div membersTableWrapper = new Div();
		membersTableWrapper.setId("grid-wrapper");
		membersTableWrapper.setWidthFull();
		membersTableWrapper.setHeight(height);
		membersTableWrapper.add(table);
		add(membersTableWrapper);
	}

	@Override
	public boolean hasRunButton() {
		return this.model == null;
	}

	@Override
	public Button createRunButton() {
		final Button runButton = new Button(Messages.getString("RunButton"));
		runButton.setWidthFull();
		add(runButton);
		return runButton;
	}

	public SimulationResult getModel() {
		return this.model;
	}

	public ResultView model(SimulationResult newModel) {
		setModel(newModel);
		return this;
	}

	public void setModel(SimulationResult model) {
		this.model = model;
		recreateComponents();
	}

	private void recreateComponents() {
		removeAll();
		createComponents();
	}

}
