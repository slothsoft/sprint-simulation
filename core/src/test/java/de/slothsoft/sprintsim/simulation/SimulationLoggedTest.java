package de.slothsoft.sprintsim.simulation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.slothsoft.sprintsim.Member;

public class SimulationLoggedTest {

	private static final String LINE_BREAK = "\n";

	StringBuilder log;
	LoggingSimulationListener loggingListener;
	Simulation simulation;

	@Before
	public void setUp() {
		this.log = new StringBuilder();

		this.loggingListener = new LoggingSimulationListener()
				.logger(string -> this.log.append(string).append(LINE_BREAK));

		this.simulation = new Simulation().seed(Long.valueOf(9876543210l));
		this.simulation.addSimulationListener(this.loggingListener);
	}

	@Test
	public void testSimple() throws Exception {
		// Arrange
		final Member senior = Member.createSenior();
		final Member normal = Member.createNormal();
		final Member junior = Member.createJunior();

		this.simulation.setMembers(senior, normal, junior);

		// Act
		this.simulation.runSprint();

		// Assert
		assertLogEqualsFile("simple-9876543210.txt");
	}

	private void assertLogEqualsFile(String expectFileName) throws IOException {
		final String expectedLog = readFile(expectFileName);
		Assert.assertEquals(expectedLog, this.log.toString());
	}

	private String readFile(String expectFileName) throws IOException {
		try (InputStream in = getClass().getResourceAsStream(expectFileName)) {
			Assert.assertNotNull("Could not find file: " + expectFileName, in);
			return new BufferedReader(new InputStreamReader(in)).lines().parallel()
					.collect(Collectors.joining(LINE_BREAK));
		}
	}

	@Test
	public void testSimpleWithoutTaskOverview() throws Exception {
		// Arrange
		this.loggingListener.printTasksOverview(false);

		final Member senior = Member.createSenior();
		final Member normal = Member.createNormal();
		final Member junior = Member.createJunior();

		this.simulation.setMembers(senior, normal, junior);

		// Act
		this.simulation.runSprint();

		// Assert
		assertLogEqualsFile("simple-no-task-overview-9876543210.txt");
	}

	@Test
	public void testMulti() throws Exception {
		// Arrange
		final Member senior = Member.createSenior();
		final Member normal = Member.createNormal();
		final Member junior = Member.createJunior();

		this.simulation.setMembers(senior, normal, junior);

		// Act
		this.simulation.runMilestone(3);

		// Assert
		assertLogEqualsFile("multi-9876543210.txt");
	}

	@Test
	public void testMultiWithoutTaskOverview() throws Exception {
		// Arrange
		this.loggingListener.printTasksOverview(false);

		final Member senior = Member.createSenior();
		final Member normal = Member.createNormal();
		final Member junior = Member.createJunior();

		this.simulation.setMembers(senior, normal, junior);

		// Act
		this.simulation.runMilestone(3);

		// Assert
		assertLogEqualsFile("multi-no-task-overview-9876543210.txt");
	}
}