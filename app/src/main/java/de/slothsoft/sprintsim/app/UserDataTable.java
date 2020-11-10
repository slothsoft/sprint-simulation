package de.slothsoft.sprintsim.app;

import java.util.Arrays;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.Column;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.data.selection.SingleSelect;

import de.slothsoft.sprintsim.HasUserData;
import de.slothsoft.sprintsim.simulation.PrettyNames;

public class UserDataTable<E extends HasUserData> extends Div {

	private static final long serialVersionUID = 2298814067722765737L;

	private static String CLASS_NAME = "user-data-table";

	protected final Grid<E> grid;
	private E[] model;

	private Column<E> nameColumn;

	public UserDataTable(E[] model) {
		setClassName(CLASS_NAME);

		this.model = model;

		this.grid = new Grid<>();
		this.grid.setItems(this.model);
		this.grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
		this.grid.setWidthFull();
		this.grid.setHeightFull();

		addColumns();

		add(this.grid);
	}

	protected void addColumns() {
		addNameColumn();
	}

	private void addNameColumn() {
		this.nameColumn = this.grid.addColumn(member -> member.getUserData(PrettyNames.DATA_NAME));
		this.nameColumn.setComparator(member -> (String) member.getUserData(PrettyNames.DATA_NAME));
		this.nameColumn.setHeader(Messages.getString("Name"));
	}

	public SingleSelect<Grid<E>, E> asSingleSelect() {
		return this.grid.asSingleSelect();
	}

	public void select(E item) {
		this.grid.select(item);
	}

	public E[] getModel() {
		return this.model;
	}

	public UserDataTable<E> model(E[] newModel) {
		setModel(newModel);
		return this;
	}

	public void setModel(E[] model) {
		this.model = model;
		this.grid.setItems(Arrays.asList(model));
	}

	public boolean isShowNameColumn() {
		return this.nameColumn.isVisible();
	}

	public UserDataTable<E> showNameColumn(boolean newShowNameColumn) {
		setShowNameColumn(newShowNameColumn);
		return this;
	}

	public void setShowNameColumn(boolean showNameColumn) {
		this.nameColumn.setVisible(showNameColumn);
	}

}
