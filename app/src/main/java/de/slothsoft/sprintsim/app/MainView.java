package de.slothsoft.sprintsim.app;

import java.util.Optional;

import com.vaadin.flow.component.ClickNotifier;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabVariant;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.RouterLink;

import de.slothsoft.sprintsim.app.config.ConfigView;
import de.slothsoft.sprintsim.app.result.ResultView;
import de.slothsoft.sprintsim.simulation.Simulation;
import de.slothsoft.sprintsim.simulation.SimulationResult;

@JsModule("./styles/shared-styles.js")
@CssImport(value = "./styles/views/main-view.css", themeFor = "vaadin-app-layout")
@CssImport("./styles/views/main-view.css")
@CssImport("./styles/shared.css")
public class MainView extends AppLayout {

	private static final long serialVersionUID = 7509565421628701090L;

	private final Tabs menu;

	public MainView() {
		this.menu = createMenuTabs();
		addToNavbar(createTopBar(createHeader(), this.menu));
	}

	private static HorizontalLayout createHeader() {
		final HorizontalLayout header = new HorizontalLayout();
		header.setPadding(false);
		header.setSpacing(false);
		header.setWidthFull();
		header.setAlignItems(FlexComponent.Alignment.CENTER);
		header.setId("header");

		final Image logo = new Image("https://avatars2.githubusercontent.com/u/7118092", "slothsoft");
		logo.setId("logo");
		header.add(logo);

		header.add(new H1(Messages.getString("SprintSimulationTitle")));

		final Image github = new Image("icons/github.svg", "GitHub");
		final Anchor githubAnchor = new Anchor("https://github.com/slothsoft/sprint-simulation", github);
		githubAnchor.setId("github");
		header.add(githubAnchor);

		return header;
	}

	private static Tabs createMenuTabs() {
		final Tabs tabs = new Tabs();
		tabs.getStyle().set("max-width", "100%");
		tabs.add(getAvailableTabs());
		return tabs;
	}

	private static Tab[] getAvailableTabs() {
		return new Tab[]{

				createTab(Messages.getString("Config"), ConfigView.class),

				createTab(Messages.getString("RunResult"), ResultView.class),

		};
	}

	private static Tab createTab(String text, Class<? extends Component> navigationTarget) {
		final Tab tab = new Tab();
		tab.addThemeVariants(TabVariant.LUMO_ICON_ON_TOP);
		tab.add(new RouterLink(text, navigationTarget));
		ComponentUtil.setData(tab, Class.class, navigationTarget);
		return tab;
	}

	private static VerticalLayout createTopBar(Component... components) {
		final VerticalLayout layout = new VerticalLayout();
		layout.getThemeList().add("dark");
		layout.setWidthFull();
		layout.setSpacing(false);
		layout.setPadding(false);
		layout.setAlignItems(FlexComponent.Alignment.CENTER);
		layout.add(components);
		return layout;
	}

	@Override
	protected void afterNavigation() {
		super.afterNavigation();

		final Component content = getContent();
		getTabForComponent(content).ifPresent(this.menu::setSelectedTab);

		if (content instanceof ConfigView) {
			((ConfigView) content).setModel(SimulationBuilder.getSimulationInfoForCurrentSession());
		}

		if (content instanceof HasRunButton) {
			final HasRunButton<?> hasRunButton = (HasRunButton<?>) content;
			if (hasRunButton.hasRunButton()) {
				addListenerToRunButton(hasRunButton.createRunButton());
			}
		}
	}

	private Optional<Tab> getTabForComponent(Component component) {
		return this.menu.getChildren()
				.filter(tab -> ComponentUtil.getData(tab, Class.class).equals(component.getClass())).findFirst()
				.map(Tab.class::cast);
	}

	private <B extends Component & ClickNotifier<?>> void addListenerToRunButton(B runButton) {
		runButton.addClickListener(event -> {
			final SimulationResult simulationResult = runSimulation();
			ResultView.setResultForCurrentSession(simulationResult);

			final Component content = getContent();
			if (content instanceof ResultView) {
				((ResultView) content).setModel(simulationResult);
			} else {
				runButton.getUI().ifPresent(ui -> ui.navigate(ResultView.class));
			}
		});
	}

	SimulationResult runSimulation() {
		final SimulationBuilder builder = fetchSimulationBuilder();
		SimulationBuilder.setSimulationInfoForCurrentSession(builder);

		final Simulation simulation = builder.createSimulation();
		return simulation.runMilestone(builder.getNumberOfSprints());
	}

	private SimulationBuilder fetchSimulationBuilder() {
		final Component content = getContent();
		// we have the config open
		if (content instanceof ConfigView) return ((ConfigView) content).getModel();
		// we don't have the config, so we take it from the cache
		return SimulationBuilder.getSimulationInfoForCurrentSession();
	}
}
