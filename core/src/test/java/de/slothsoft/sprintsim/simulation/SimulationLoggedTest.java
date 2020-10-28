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
	Simulation simulation;

	@Before
	public void setUp() {
		this.log = new StringBuilder();

		this.simulation = new Simulation().seed(9876543210l);
		this.simulation.addSimulationListener(new LoggingSimulationListener().logger(string -> this.log.append(string).append(LINE_BREAK)));
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
		String expectedLog = readFile(expectFileName);
		Assert.assertEquals(expectedLog, log.toString());
	}

	private String readFile(String expectFileName) throws IOException {
		try(InputStream in = getClass().getResourceAsStream(expectFileName))  {
			Assert.assertNotNull("Could not find file: " + expectFileName, in);
			return new BufferedReader(new InputStreamReader(in)).lines()
				   .parallel().collect(Collectors.joining(LINE_BREAK));
		}
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
}