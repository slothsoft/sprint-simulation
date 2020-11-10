package de.slothsoft.sprintsim.app.config;

import java.util.UUID;

import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.data.binder.Binder;

import de.slothsoft.sprintsim.config.TaskConfig;

public class TaskConfigView extends FormLayout {

	private static final long serialVersionUID = -8398344286861462479L;

	public static final String asd = UUID.randomUUID().toString();

	private final IntegerField lowComplexityHours;
	private final IntegerField mediumComplexityHours;
	private final IntegerField highComplexityHours;

	private final Binder<TaskConfig> binder = new Binder<>(TaskConfig.class);

	public TaskConfigView() {
		this.lowComplexityHours = new IntegerField();
		this.lowComplexityHours.setLabel(Messages.getString("LowComplexityHours")); //$NON-NLS-1$

		this.mediumComplexityHours = new IntegerField();
		this.mediumComplexityHours.setLabel(Messages.getString("MediumComplexityHours")); //$NON-NLS-1$

		this.highComplexityHours = new IntegerField();
		this.highComplexityHours.setLabel(Messages.getString("HighComplexityHours")); //$NON-NLS-1$

		add(this.lowComplexityHours, this.mediumComplexityHours, this.highComplexityHours);

		this.binder.setBean(new TaskConfig());
		this.binder.bindInstanceFields(this);
	}

	public TaskConfig getModel() {
		return this.binder.getBean();
	}

	public TaskConfigView model(TaskConfig newModel) {
		setModel(newModel);
		return this;
	}

	public void setModel(TaskConfig model) {
		this.binder.setBean(model);
	}

}
