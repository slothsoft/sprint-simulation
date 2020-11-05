package de.slothsoft.sprintsim.app.result;

import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class ResultView extends VerticalLayout {

	private static final long serialVersionUID = -8243165312789523979L;

	private Html html;

	public ResultView() {
		this.html = new Html(createHtml(""));
		add(this.html);
	}

	private static String createHtml(String innerHtml) {
		return "<html><body><div>" + innerHtml + "</div></body></html>";
	}

	public void setValue(String value) {
		remove(this.html);

		this.html = new Html(createHtml(value));
		add(this.html);
	}

	public String getValue() {
		return this.html.getInnerHtml();
	}
}
