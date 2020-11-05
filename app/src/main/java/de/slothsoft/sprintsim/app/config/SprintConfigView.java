package de.slothsoft.sprintsim.app.config;

import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.IntegerField;

import de.slothsoft.sprintsim.config.SprintConfig;

public class SprintConfigView extends FormLayout {

	private static final long serialVersionUID = 1682270586744120693L;

	private final SprintConfig model;
	private final IntegerField lengthInDays;

	public SprintConfigView() {
		this.model = SprintConfig.createDefault();

		this.lengthInDays = new IntegerField();
		this.lengthInDays.setLabel(Messages.getString("LengthInDays")); //$NON-NLS-1$
		this.lengthInDays.setValue(Integer.valueOf(this.model.getLengthInDays()));

		add(this.lengthInDays);
		setResponsiveSteps(new ResponsiveStep(ConfigView.INTEGER_FIELD_WIDTH, 1));
	}

	public SprintConfig createConfig() {
		return this.model.lengthInDays(this.lengthInDays.getValue().intValue());
	}
}
