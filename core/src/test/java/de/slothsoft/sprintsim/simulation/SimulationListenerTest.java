package de.slothsoft.sprintsim.simulation;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.slothsoft.sprintsim.Member;
import de.slothsoft.sprintsim.execution.SprintRetro;
import de.slothsoft.sprintsim.generation.SprintPlanning;

public class SimulationListenerTest {

	Simulation simulation;

	@Before
	public void setUp() {
		this.simulation = new Simulation();
	}

	// sprintPlanned(SprintPlanning)

	@Test
	public void testAddSimulationListenerSprintPlanned() throws Exception {
		// Arrange
		final Member member = Member.createNormal();
		this.simulation.setMembers(member);

		final SprintPlanning[] listenerArgument = {null};
		this.simulation.addSimulationListener(new SimulationListener.Adapter() {

			@Override
			public void sprintPlanned(SprintPlanning sprintPlanning) {
				listenerArgument[0] = sprintPlanning;
			}
		});

		// Act
		final SimulationResult result = this.simulation.runSprint();

		// Assert
		Assert.assertNotNull(listenerArgument[0]);
		Assert.assertSame(result.getPlanning(), listenerArgument[0]);
	}

	@Test
	public void testRemoveSimulationListenerSprintPlanned() throws Exception {
		// Arrange
		final Member member = Member.createNormal();
		this.simulation.setMembers(member);

		final SprintPlanning[] listenerArgument = {null};
		final SimulationListener listener = new SimulationListener.Adapter() {

			@Override
			public void sprintPlanned(SprintPlanning sprintPlanning) {
				listenerArgument[0] = sprintPlanning;
			}
		};
		this.simulation.addSimulationListener(listener);
		this.simulation.removeSimulationListener(listener);

		// Act
		this.simulation.runSprint();

		// Assert
		Assert.assertNull(listenerArgument[0]);
	}

	@Test
	public void testRemoveSimulationListenerItselfSprintPlanned() throws Exception {
		// Arrange
		final Member member = Member.createNormal();
		this.simulation.setMembers(member);

		final SimulationListener listener = new SimulationListener.Adapter() {

			@Override
			public void sprintPlanned(SprintPlanning sprintPlanning) {
				SimulationListenerTest.this.simulation.removeSimulationListener(this);
			}
		};
		this.simulation.addSimulationListener(listener);

		// Act
		final SimulationResult result = this.simulation.runSprint();

		// Assert
		Assert.assertNotNull(result);
	}

	// sprintExecuted(SprintRetro)

	@Test
	public void testAddSimulationListenerSprintExecuted() throws Exception {
		// Arrange
		final Member member = Member.createNormal();
		this.simulation.setMembers(member);

		final SprintRetro[] listenerArgument = {null};
		this.simulation.addSimulationListener(new SimulationListener.Adapter() {

			@Override
			public void sprintExecuted(SprintRetro sprintRetro) {
				listenerArgument[0] = sprintRetro;
			}
		});

		// Act
		final SimulationResult result = this.simulation.runSprint();

		// Assert
		Assert.assertNotNull(listenerArgument[0]);
		Assert.assertSame(result.getRetro(), listenerArgument[0]);
	}

	@Test
	public void testRemoveSimulationListenerSprintExecuted() throws Exception {
		// Arrange
		final Member member = Member.createNormal();
		this.simulation.setMembers(member);

		final SprintRetro[] listenerArgument = {null};
		final SimulationListener listener = new SimulationListener.Adapter() {

			@Override
			public void sprintExecuted(SprintRetro sprintRetro) {
				listenerArgument[0] = sprintRetro;
			}
		};
		this.simulation.addSimulationListener(listener);
		this.simulation.removeSimulationListener(listener);

		// Act
		this.simulation.runSprint();

		// Assert
		Assert.assertNull(listenerArgument[0]);
	}

	@Test
	public void testRemoveSimulationListenerItselfSprintExecuted() throws Exception {
		// Arrange
		final Member member = Member.createNormal();
		this.simulation.setMembers(member);

		final SimulationListener listener = new SimulationListener.Adapter() {

			@Override
			public void sprintExecuted(SprintRetro sprintRetro) {
				SimulationListenerTest.this.simulation.removeSimulationListener(this);
			}
		};
		this.simulation.addSimulationListener(listener);

		// Act
		final SimulationResult result = this.simulation.runSprint();

		// Assert
		Assert.assertNotNull(result);
	}

	// simulationStarted(SprintRetro)

	@Test
	public void testAddSimulationListenerSimulationStarted() throws Exception {
		// Arrange
		final Member member = Member.createNormal();
		this.simulation.setMembers(member);

		final SimulationInfo[] listenerArgument = {null};
		this.simulation.addSimulationListener(new SimulationListener.Adapter() {

			@Override
			public void simulationStarted(SimulationInfo simulationInfo) {
				listenerArgument[0] = simulationInfo;
			}
		});

		// Act
		this.simulation.runSprint();

		// Assert
		Assert.assertNotNull(listenerArgument[0]);
		Assert.assertSame(this.simulation.getMembers(), listenerArgument[0].getMembers());
		Assert.assertSame(this.simulation.getSprintConfig(), listenerArgument[0].getSprintConfig());
		Assert.assertSame(this.simulation.getTaskConfig(), listenerArgument[0].getTaskConfig());
	}

	@Test
	public void testRemoveSimulationListenerSimulationStarted() throws Exception {
		// Arrange
		final Member member = Member.createNormal();
		this.simulation.setMembers(member);

		final SimulationInfo[] listenerArgument = {null};
		final SimulationListener listener = new SimulationListener.Adapter() {

			@Override
			public void simulationStarted(SimulationInfo simulationInfo) {
				listenerArgument[0] = simulationInfo;
			}
		};
		this.simulation.addSimulationListener(listener);
		this.simulation.removeSimulationListener(listener);

		// Act
		this.simulation.runSprint();

		// Assert
		Assert.assertNull(listenerArgument[0]);
	}

	@Test
	public void testRemoveSimulationListenerItselfSimulationStarted() throws Exception {
		// Arrange
		final Member member = Member.createNormal();
		this.simulation.setMembers(member);

		final SimulationListener listener = new SimulationListener.Adapter() {

			@Override
			public void simulationStarted(SimulationInfo simulationInfo) {
				SimulationListenerTest.this.simulation.removeSimulationListener(this);
			}
		};
		this.simulation.addSimulationListener(listener);

		// Act
		final SimulationResult result = this.simulation.runSprint();

		// Assert
		Assert.assertNotNull(result);
	}

	// simulationFinished(SprintRetro)

	@Test
	public void testAddSimulationListenerSimulationFinished() throws Exception {
		// Arrange
		final Member member = Member.createNormal();
		this.simulation.setMembers(member);

		final SimulationResult[] listenerArgument = {null};
		this.simulation.addSimulationListener(new SimulationListener.Adapter() {

			@Override
			public void simulationFinished(SimulationResult simulationResult) {
				listenerArgument[0] = simulationResult;
			}
		});

		// Act
		final SimulationResult result = this.simulation.runSprint();

		// Assert
		Assert.assertNotNull(listenerArgument);
		Assert.assertSame(result, listenerArgument[0]);
	}

	@Test
	public void testRemoveSimulationListenerSimulationFinished() throws Exception {
		// Arrange
		final Member member = Member.createNormal();
		this.simulation.setMembers(member);

		final SimulationResult[] listenerArgument = {null};
		final SimulationListener listener = new SimulationListener.Adapter() {

			@Override
			public void simulationFinished(SimulationResult simulationResult) {
				listenerArgument[0] = simulationResult;
			}
		};
		this.simulation.addSimulationListener(listener);
		this.simulation.removeSimulationListener(listener);

		// Act
		this.simulation.runSprint();

		// Assert
		Assert.assertNull(listenerArgument[0]);
	}

	@Test
	public void testRemoveSimulationListenerItselfSimulationFinished() throws Exception {
		// Arrange
		final Member member = Member.createNormal();
		this.simulation.setMembers(member);

		final SimulationListener listener = new SimulationListener.Adapter() {

			@Override
			public void simulationFinished(SimulationResult simulationResult) {
				SimulationListenerTest.this.simulation.removeSimulationListener(this);
			}
		};
		this.simulation.addSimulationListener(listener);

		// Act
		final SimulationResult result = this.simulation.runSprint();

		// Assert
		Assert.assertNotNull(result);
	}
}
