package de.slothsoft.sprintsim.app.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;

import de.slothsoft.sprintsim.Member;
import de.slothsoft.sprintsim.app.MemberForm;
import de.slothsoft.sprintsim.app.MembersTable;

public class MembersConfigView extends Div {

	private static final long serialVersionUID = 7565932228299223215L;

	private final MembersTable masterTable;
	private final MemberForm detailForm;

	private Member detailModel = Member.createNormal();

	private final Button save;
	private final Button saveNew;
	private final Button delete;

	public MembersConfigView() {

		// config master area

		this.masterTable = new MembersTable().showNameColumn(false);
		this.masterTable.setClassName("master-area");
		// when a row is selected or deselected, populate form
		this.masterTable.asSingleSelect().addValueChangeListener(event -> {
			if (event.getValue() != null) {
				setDetailModel(event.getValue());
			} else {
				clearForm();
			}
		});

		// config detail area

		this.detailForm = new MemberForm();

		// everything else

		this.save = new Button(Messages.getString("Save"));
		this.save.addClickListener(e -> {
			saveDetailModel(false);
			Notification.show(Messages.getString("MemberStored"));
		});

		this.saveNew = new Button(Messages.getString("SaveNew"));
		this.saveNew.addClickListener(e -> {
			saveDetailModel(true);
			Notification.show(Messages.getString("MemberStored"));
		});

		this.delete = new Button(Messages.getString("Delete"));
		this.delete.addClickListener(e -> {
			deleteDetailModel();
		});

		// build layout from this

		final SplitLayout splitLayout = new SplitLayout();
		splitLayout.setClassName("master-detail-area");
		splitLayout.setSizeFull();

		splitLayout.addToPrimary(this.masterTable);

		final Div detailArea = new Div();
		detailArea.setClassName("detail-area");
		detailArea.add(createButtonLayout());
		detailArea.add(this.detailForm);
		splitLayout.addToSecondary(detailArea);

		splitLayout.setSplitterPosition(70);
		add(splitLayout);
	}

	private Component createButtonLayout() {
		final HorizontalLayout buttonLayout = new HorizontalLayout();
		buttonLayout.setWidthFull();
		buttonLayout.setSpacing(true);
		this.save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		this.saveNew.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		this.delete.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
		buttonLayout.add(this.save, this.saveNew, this.delete);
		return buttonLayout;
	}

	private void clearForm() {
		setDetailModel(null);
	}

	private void setDetailModel(Member value) {
		this.detailModel = value;
		this.detailForm.setModel(this.detailModel == null ? null : this.detailModel.copy());
	}

	private void deleteDetailModel() {
		final List<Member> members = new ArrayList<>(Arrays.asList(this.masterTable.getModel()));
		members.remove(this.detailModel);
		this.masterTable.setModel(members.toArray(new Member[members.size()]));

		clearForm();
	}

	private void saveDetailModel(boolean saveAsNew) {
		Member updatedModel = this.detailForm.getModel();

		if (updatedModel == null) {
			updatedModel = Member.createNormal();
		}

		final List<Member> members = new ArrayList<>(Arrays.asList(this.masterTable.getModel()));
		final int index = members.indexOf(this.detailModel);
		if (index < 0 || saveAsNew) {
			members.add(updatedModel);
		} else {
			members.set(index, updatedModel);
		}
		this.masterTable.setModel(members.toArray(new Member[members.size()]));
		this.detailModel = updatedModel;

		setDetailModel(updatedModel);
	}

	public Member[] getModel() {
		return this.masterTable.getModel();
	}

	public MembersConfigView model(Member[] newModel) {
		setModel(newModel);
		return this;
	}

	public void setModel(Member[] model) {
		this.masterTable.setModel(model);
	}

}
