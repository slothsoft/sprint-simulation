package de.slothsoft.sprintsim.app.config;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.IntegerField;

import de.slothsoft.sprintsim.Member;

public class MemberConfigView extends FormLayout {

	private static final long serialVersionUID = -8398344286861462479L;

	private final IntegerField seniorMemberCount;
	private final IntegerField normalMemberCount;
	private final IntegerField juniorMemberCount;

	public MemberConfigView() {
		this.seniorMemberCount = new IntegerField();
		this.seniorMemberCount.setLabel(Messages.getString("SeniorMemberCount")); //$NON-NLS-1$
		this.seniorMemberCount.setValue(Integer.valueOf(1));

		this.normalMemberCount = new IntegerField();
		this.normalMemberCount.setLabel(Messages.getString("NormalMemberCount")); //$NON-NLS-1$
		this.normalMemberCount.setValue(Integer.valueOf(1));

		this.juniorMemberCount = new IntegerField();
		this.juniorMemberCount.setLabel(Messages.getString("JuniorMemberCount")); //$NON-NLS-1$
		this.juniorMemberCount.setValue(Integer.valueOf(1));

		add(this.seniorMemberCount, this.normalMemberCount, this.juniorMemberCount);
		setResponsiveSteps(new ResponsiveStep(ConfigView.INTEGER_FIELD_WIDTH, 1),
				new ResponsiveStep(ConfigView.INTEGER_FIELD_WIDTH, 2),
				new ResponsiveStep(ConfigView.INTEGER_FIELD_WIDTH, 3));
	}

	public Member[] createMembers() {
		final List<Member> result = new ArrayList<>();
		createMembers(result, this.seniorMemberCount.getValue(), () -> Member.createSenior());
		createMembers(result, this.normalMemberCount.getValue(), () -> Member.createNormal());
		createMembers(result, this.juniorMemberCount.getValue(), () -> Member.createJunior());
		return result.toArray(new Member[result.size()]);
	}

	private static void createMembers(List<Member> result, Integer value, Supplier<Member> memberCreator) {
		for (int i = 0; i < value.intValue(); i++) {
			result.add(memberCreator.get());
		}
	}
}
