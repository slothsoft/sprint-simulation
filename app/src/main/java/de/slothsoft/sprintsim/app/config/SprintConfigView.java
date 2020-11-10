package de.slothsoft.sprintsim.app.config;

import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.data.binder.Binder;

import de.slothsoft.sprintsim.config.SprintConfig;

public class SprintConfigView extends FormLayout {

	private static final long serialVersionUID = 1682270586744120693L;

	private final IntegerField numberOfSprints;
	private final IntegerField lengthInDays;

	private final Binder<SprintConfig> binder = new Binder<>(SprintConfig.class);

	public SprintConfigView() {
		this.numberOfSprints = new IntegerField();
		this.numberOfSprints.setLabel(Messages.getString("NumberOfSprints")); //$NON-NLS-1$
		this.numberOfSprints.setValue(Integer.valueOf(3));

		this.lengthInDays = new IntegerField();
		this.lengthInDays.setLabel(Messages.getString("LengthInDays")); //$NON-NLS-1$

		add(this.numberOfSprints, this.lengthInDays);

		this.binder.setBean(SprintConfig.createDefault());
		this.binder.bindInstanceFields(this);
	}

	public SprintConfig getModel() {
		return this.binder.getBean();
	}

	public SprintConfigView model(SprintConfig newModel) {
		setModel(newModel);
		return this;
	}

	public void setModel(SprintConfig model) {
		this.binder.setBean(model);
	}

	public SprintConfigView numberOfSprints(int newNumberOfSprints) {
		setNumberOfSprints(newNumberOfSprints);
		return this;
	}

	public void setNumberOfSprints(int numberOfSprints) {
		this.numberOfSprints.setValue(Integer.valueOf(numberOfSprints));
	}

	public int getNumberOfSprints() {
		return this.numberOfSprints.getValue().intValue();
	}
}
