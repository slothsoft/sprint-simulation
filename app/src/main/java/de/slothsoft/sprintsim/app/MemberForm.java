package de.slothsoft.sprintsim.app;

import java.util.function.Function;

import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.data.binder.Binder;

import de.slothsoft.sprintsim.Member;
import de.slothsoft.sprintsim.Performance;
import de.slothsoft.sprintsim.StandardPerformance;

public class MemberForm extends FormLayout {

	private static final long serialVersionUID = -8398344286861462479L;

	private final Select<Performance> workPerformance;
	private final NumberField estimationDeviation;
	private final IntegerField workHoursPerDay;

	private final Binder<Member> binder = new Binder<>(Member.class);

	public MemberForm() {
		this.workPerformance = new Select<>();
		this.workPerformance.setItems(StandardPerformance.values());
		this.workPerformance.setLabel(Messages.getString("WorkPerformance")); //$NON-NLS-1$
		this.workPerformance.setRenderer(new PerformanceRenderer<>(Function.identity()));

		this.estimationDeviation = new NumberField();
		this.estimationDeviation.setLabel(Messages.getString("EstimationDeviation")); //$NON-NLS-1$

		this.workHoursPerDay = new IntegerField();
		this.workHoursPerDay.setLabel(Messages.getString("WorkHoursPerDay")); //$NON-NLS-1$

		add(this.workPerformance, this.estimationDeviation, this.workHoursPerDay);

		this.binder.setBean(Member.createNormal());
		this.binder.bindInstanceFields(this);
	}

	public Member getModel() {
		return this.binder.getBean();
	}

	public MemberForm model(Member newModel) {
		setModel(newModel);
		return this;
	}

	public void setModel(Member model) {
		this.binder.setBean(model);
	}

}
