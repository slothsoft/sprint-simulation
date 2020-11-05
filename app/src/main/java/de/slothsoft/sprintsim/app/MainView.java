package de.slothsoft.sprintsim.app;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.material.Material;

import de.slothsoft.sprintsim.Member;
import de.slothsoft.sprintsim.app.config.ConfigView;
import de.slothsoft.sprintsim.app.result.ResultView;
import de.slothsoft.sprintsim.config.SprintConfig;
import de.slothsoft.sprintsim.config.TaskConfig;
import de.slothsoft.sprintsim.io.HtmlLogger;
import de.slothsoft.sprintsim.simulation.LoggingSimulationListener;
import de.slothsoft.sprintsim.simulation.Simulation;

/**
 * The main view contains a button and a click listener.
 */

@Route
@PWA(name = "Sprint Simulation", shortName = "Sprint Simulation")
@Theme(value = Material.class)
public class MainView extends VerticalLayout {

	private static final long serialVersionUID = -6515374665513033293L;

	private final ConfigView configView;
	private final ResultView resultView;

	public MainView() {
		this.configView = new ConfigView();
		add(this.configView);

		final HorizontalLayout buttons = new HorizontalLayout();
		buttons.setWidthFull();

		final Button runButton = new Button(Messages.getString("RunButton"), this::onRunEvent);
		runButton.setWidthFull();
		buttons.add(runButton);

		add(buttons);

		this.resultView = new ResultView();
		add(this.resultView);
	}

	@SuppressWarnings("unused")
	void onRunEvent(ClickEvent<Button> event) {
		final Member[] members = this.configView.createMembers();
		final SprintConfig sprintConfig = this.configView.createSprintConfig();
		final TaskConfig taskConfig = this.configView.createTaskConfig();

		final HtmlLogger logger = new HtmlLogger();

		final Simulation simulation = new Simulation(members);
		simulation.sprintConfig(sprintConfig).taskConfig(taskConfig);
		simulation.addSimulationListener(new LoggingSimulationListener().logger(logger));
		simulation.runSprint();

		this.resultView.setValue(logger.getHtml());
	}
}
