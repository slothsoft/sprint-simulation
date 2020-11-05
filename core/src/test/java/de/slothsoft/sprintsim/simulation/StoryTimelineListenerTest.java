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

public class StoryTimelineListenerTest {

	private static final String LINE_BREAK = "\n";
	private static final double DELTA = 0.0001;

	StringBuilder log;
	Simulation simulation;

	@Before
	public void setUp() {
		this.log = new StringBuilder();

		this.simulation = new Simulation().seed(Long.valueOf(9876543210l));
		this.simulation.addTimelineListener(
				new StoryTimelineListener().logger(string -> this.log.append(string).append(LINE_BREAK)));
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
		assertLogEqualsFile("story-simple-9876543210.txt");
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
	public void testMulti() throws Exception {
		// Arrange
		final Member senior = Member.createSenior();
		final Member normal = Member.createNormal();
		final Member junior = Member.createJunior();

		this.simulation.setMembers(senior, normal, junior);

		// Act
		this.simulation.runMilestone(3);

		// Assert
		assertLogEqualsFile("story-multi-9876543210.txt");
	}

	// other tests

	static class DayAndHour {

		int day;
		double hour;

		public DayAndHour(int day, double hour) {
			this.day = day;
			this.hour = hour;
		}

	}

	@Test
	public void testCalculateDifferenceInHours() throws Exception {
		assertCalculateDifferenceCorrect(new DayAndHour(1, 0), new DayAndHour(1, 3), 8, 3);
		assertCalculateDifferenceCorrect(new DayAndHour(1, 0), new DayAndHour(1, 3), 1, 3);
	}

	static void assertCalculateDifferenceCorrect(DayAndHour dayAndHour1, DayAndHour dayAndHour2, int longestDayInHours,
			double expected) {
		Assert.assertEquals(expected, StoryTimelineListener.calculateDifference(dayAndHour1.day, dayAndHour1.hour,
				dayAndHour2.day, dayAndHour2.hour, longestDayInHours), DELTA);
		Assert.assertEquals(expected, StoryTimelineListener.calculateDifference(dayAndHour2.day, dayAndHour2.hour,
				dayAndHour1.day, dayAndHour1.hour, longestDayInHours), DELTA);
	}

	@Test
	public void testCalculateDifferenceInDays() throws Exception {
		assertCalculateDifferenceCorrect(new DayAndHour(1, 0), new DayAndHour(2, 0), 8, 8);
		assertCalculateDifferenceCorrect(new DayAndHour(1, 3), new DayAndHour(2, 3), 1, 1);
	}

	@Test
	public void testCalculateDifferenceInDaysAndHours() throws Exception {
		assertCalculateDifferenceCorrect(new DayAndHour(1, 6), new DayAndHour(2, 0), 8, 2);
		assertCalculateDifferenceCorrect(new DayAndHour(1, 3), new DayAndHour(2, 6), 1, 4);
	}

	@Test
	public void testStringify() throws Exception {
		Assert.assertEquals("Day 1 00:00", StoryTimelineListener.stringify(1, 0));
		Assert.assertEquals("Day 5 04:00", StoryTimelineListener.stringify(5, 4));
		Assert.assertEquals("Day 3 02:30", StoryTimelineListener.stringify(3, 2.5));
		Assert.assertEquals("Day 3 02:30", StoryTimelineListener.stringify(3, 2.499999999));
		Assert.assertEquals("Day 100 10:18", StoryTimelineListener.stringify(100, 10.3));
	}

}