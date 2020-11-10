package de.slothsoft.sprintsim.app;

import java.util.function.Function;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.data.renderer.ComponentRenderer;

import de.slothsoft.sprintsim.Performance;
import de.slothsoft.sprintsim.StandardPerformance;

public class PerformanceRenderer<T> extends ComponentRenderer<Component, T> {

	private static final long serialVersionUID = -7261734132965922954L;

	public PerformanceRenderer(Function<T, Performance> performanceGetter) {
		super(item -> {
			final Span span = new Span();
			span.setText(stringify(performanceGetter.apply(item)));
			return span;
		});
	}

	private static String stringify(Performance performance) {
		if (performance == null) return null;
		if (!(performance instanceof StandardPerformance)) return performance.toString();

		switch ((StandardPerformance) performance) {
			case JUNIOR :
				return Messages.getString("StandardPerformance.JUNIOR");
			case NORMAL :
				return Messages.getString("StandardPerformance.NORMAL");
			case SENIOR :
				return Messages.getString("StandardPerformance.SENIOR");
			default :
				throw new IllegalArgumentException("Do not know performance: " + performance);
		}
	}
}
