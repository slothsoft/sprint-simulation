package de.slothsoft.sprintsim.app.config;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

import de.slothsoft.sprintsim.app.HasRunButton;
import de.slothsoft.sprintsim.app.MainView;
import de.slothsoft.sprintsim.app.SimulationBuilder;

@Route(value = "config", layout = MainView.class)
@RouteAlias(value = "", layout = MainView.class)
@CssImport("./styles/views/config-view.css")
@CssImport("./styles/shared.css")
public class ConfigView extends VerticalLayout implements HasRunButton<Button> {

	private static final long serialVersionUID = 5483344395483796140L;

	private static final String CLASS_CONFIG_PANEL = "config-panel";
	static final String INTEGER_FIELD_WIDTH = "80em";

	private final MembersConfigView membersConfigView;
	private final SprintConfigView sprintConfigView;
	private final TaskConfigView taskConfigView;

	private SimulationBuilder model;

	public ConfigView() {
		this.membersConfigView = new MembersConfigView();
		this.membersConfigView.setSizeFull();
		this.membersConfigView.addClassName(CLASS_CONFIG_PANEL);
		addPanel(Messages.getString("MembersConfig"), this.membersConfigView);

		this.sprintConfigView = new SprintConfigView();
		this.sprintConfigView.addClassName(CLASS_CONFIG_PANEL);
		addPanel(Messages.getString("SprintConfig"), this.sprintConfigView);

		this.taskConfigView = new TaskConfigView();
		this.taskConfigView.addClassName(CLASS_CONFIG_PANEL);
		addPanel(Messages.getString("TaskConfig"), this.taskConfigView);

		setModel(new SimulationBuilder());
	}

	private void addPanel(String title, Component component) {

		final Header header = new Header(new Label(title));
		add(header);
		add(component);
	}

	@Override
	public Button createRunButton() {
		final Button runButton = new Button(Messages.getString("RunButton"));
		runButton.setWidthFull();
		add(runButton);
		return runButton;
	}

	public SimulationBuilder getModel() {
		this.model.setMembers(this.membersConfigView.getModel());
		this.model.setTaskConfig(this.taskConfigView.getModel());
		this.model.setSprintConfig(this.sprintConfigView.getModel());
		this.model.setNumberOfSprints(this.sprintConfigView.getNumberOfSprints());
		return this.model;
	}

	public ConfigView model(SimulationBuilder newModel) {
		setModel(newModel);
		return this;
	}

	public void setModel(SimulationBuilder model) {
		this.model = model;

		this.membersConfigView.setModel(model.getMembers());
		this.taskConfigView.setModel(model.getTaskConfig());
		this.sprintConfigView.setModel(model.getSprintConfig());
		this.sprintConfigView.setNumberOfSprints(model.getNumberOfSprints());
	}
}
