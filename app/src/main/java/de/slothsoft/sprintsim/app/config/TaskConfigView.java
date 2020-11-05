package de.slothsoft.sprintsim.app.config;

import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.IntegerField;

import de.slothsoft.sprintsim.config.TaskConfig;

public class TaskConfigView extends FormLayout {

	private static final long serialVersionUID = -8398344286861462479L;

	private final TaskConfig model;

	private final IntegerField lowComplexityHours;
	private final IntegerField mediumComplexityHours;
	private final IntegerField highComplexityHours;

	public TaskConfigView() {
		this.model = new TaskConfig();

		this.lowComplexityHours = new IntegerField();
		this.lowComplexityHours.setLabel(Messages.getString("LowComplexityHours")); //$NON-NLS-1$
		this.lowComplexityHours.setValue(Integer.valueOf(this.model.getLowComplexityHours()));

		this.mediumComplexityHours = new IntegerField();
		this.mediumComplexityHours.setLabel(Messages.getString("MediumComplexityHours")); //$NON-NLS-1$
		this.mediumComplexityHours.setValue(Integer.valueOf(this.model.getMediumComplexityHours()));

		this.highComplexityHours = new IntegerField();
		this.highComplexityHours.setLabel(Messages.getString("HighComplexityHours")); //$NON-NLS-1$
		this.highComplexityHours.setValue(Integer.valueOf(this.model.getHighComplexityHours()));

		add(this.lowComplexityHours, this.mediumComplexityHours, this.highComplexityHours);
		setResponsiveSteps(new ResponsiveStep(ConfigView.INTEGER_FIELD_WIDTH, 3));
	}

	public TaskConfig createConfig() {
		return this.model.lowComplexityHours(this.lowComplexityHours.getValue().intValue())
				.mediumComplexityHours(this.mediumComplexityHours.getValue().intValue())
				.highComplexityHours(this.highComplexityHours.getValue().intValue());
	}
}
