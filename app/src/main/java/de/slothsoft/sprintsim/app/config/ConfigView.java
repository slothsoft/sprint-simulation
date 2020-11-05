package de.slothsoft.sprintsim.app.config;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.accordion.AccordionPanel;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import de.slothsoft.sprintsim.simulation.Simulation;

public class ConfigView extends VerticalLayout {

	private static final long serialVersionUID = 5483344395483796140L;
	static final String INTEGER_FIELD_WIDTH = "80em";

	private final MemberConfigView memberConfigView;
	private final SprintConfigView sprintConfigView;
	private final TaskConfigView taskConfigView;

	public ConfigView() {
		this.memberConfigView = new MemberConfigView();
		addExpandablePanel(Messages.getString("MemberConfig"), this.memberConfigView, true);

		this.sprintConfigView = new SprintConfigView();
		addExpandablePanel(Messages.getString("SprintConfig"), this.sprintConfigView, true);

		this.taskConfigView = new TaskConfigView();
		addExpandablePanel(Messages.getString("TaskConfig"), this.taskConfigView, false);
	}

	private void addExpandablePanel(String title, Component component, boolean open) {
		final AccordionPanel panel = new AccordionPanel(title, component);

		final Accordion accordion = new Accordion();
		accordion.setWidthFull();
		accordion.add(panel);
		if (open) {
			accordion.open(panel);
		} else {
			accordion.close();
		}
		addAndExpand(accordion);
	}

	public Simulation createSimulation() {
		final Simulation simulation = new Simulation(this.memberConfigView.createMembers());
		simulation.sprintConfig(this.sprintConfigView.createConfig()).taskConfig(this.taskConfigView.createConfig());
		return simulation;
	}

	public int fetchNumberOfSprints() {
		return this.sprintConfigView.fetchNumberOfSprints();
	}
}
