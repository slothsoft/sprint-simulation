package de.slothsoft.sprintsim.simulation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.slothsoft.sprintsim.Member;
import de.slothsoft.sprintsim.Task;
import de.slothsoft.sprintsim.execution.SprintExecutor;

public class TimelineListenerTest {

	private static final double DELTA = 0.0001;

	Simulation simulation;

	@Before
	public void setUp() {
		this.simulation = new Simulation();
	}

	@Test
	public void testAddTimelineListenerSprintPlanned() throws Exception {
		// Arrange
		final Member member = Member.createNormal();
		this.simulation.setMembers(member);

		final List<TimelineEvent> listenerArguments = new ArrayList<>();
		this.simulation.addTimelineListener(event -> listenerArguments.add(event));

		// Act
		final SimulationResult result = this.simulation.runSprint();

		// Assert
		Assert.assertFalse(listenerArguments.isEmpty());
		final Iterator<TimelineEvent> it = listenerArguments.iterator();

		// 1) SIMULATION_STARTED

		Assert.assertTrue(it.hasNext());

		TimelineEvent timelineEvent = it.next();
		Assert.assertEquals(TimelineEvent.Type.SIMULATION_STARTED, timelineEvent.getType());
		Assert.assertEquals(TimelineSimulationListener.NO_DAY, timelineEvent.getDay());
		Assert.assertEquals(TimelineSimulationListener.NO_HOUR, timelineEvent.getHour(), DELTA);
		Assert.assertEquals(this.simulation.createInfo(1), timelineEvent.getData());

		// SPRINT_STARTED

		Assert.assertTrue(it.hasNext());

		timelineEvent = it.next();
		Assert.assertEquals(TimelineEvent.Type.SPRINT_STARTED, timelineEvent.getType());
		Assert.assertEquals(1, timelineEvent.getDay());
		Assert.assertEquals(0, timelineEvent.getHour(), DELTA);
		Assert.assertSame(result.getPlannings()[0], timelineEvent.getData());

		// TASK_STARTED

		final int tasks = (listenerArguments.size() - 4) / 2;
		Assert.assertTrue("Tasks are missing! " + listenerArguments, tasks > 0);

		for (int i = 0; i < tasks; i++) {
			timelineEvent = it.next();
			Assert.assertEquals(TimelineEvent.Type.TASK_STARTED, timelineEvent.getType());
			Assert.assertTrue(timelineEvent.getDay() >= 1);
			Assert.assertTrue(timelineEvent.getHour() >= 0);
			Assert.assertNotNull(timelineEvent.getData());

			timelineEvent = it.next();
			Assert.assertEquals(TimelineEvent.Type.TASK_FINISHED, timelineEvent.getType());
			Assert.assertTrue(timelineEvent.getDay() >= 1);
			Assert.assertTrue(timelineEvent.getHour() >= 0);
			Assert.assertNotNull(timelineEvent.getData());
		}

		// SPRINT_FINISHED

		Assert.assertTrue(it.hasNext());

		timelineEvent = it.next();
		Assert.assertEquals(TimelineEvent.Type.SPRINT_FINISHED, timelineEvent.getType());
		Assert.assertTrue(timelineEvent.getDay() >= 1);
		Assert.assertTrue(timelineEvent.getHour() >= 0);
		Assert.assertSame(result.getRetros()[0], timelineEvent.getData());

		// SIMULATION_FINISHED

		Assert.assertTrue(it.hasNext());

		timelineEvent = it.next();
		Assert.assertEquals(TimelineEvent.Type.SIMULATION_FINISHED, timelineEvent.getType());
		Assert.assertEquals(TimelineSimulationListener.NO_DAY, timelineEvent.getDay());
		Assert.assertEquals(TimelineSimulationListener.NO_HOUR, timelineEvent.getHour(), DELTA);
		Assert.assertSame(result, timelineEvent.getData());
	}

	@Test
	public void testRemoveTimelineListenerSprintPlanned() throws Exception {
		// Arrange
		final Member member = Member.createNormal();
		this.simulation.setMembers(member);

		final List<TimelineEvent> listenerArguments = new ArrayList<>();
		final TimelineListener listener = event -> listenerArguments.add(event);
		this.simulation.addTimelineListener(listener);
		this.simulation.removeTimelineListener(listener);

		// Act
		this.simulation.runSprint();

		// Assert
		Assert.assertTrue("Where did the events come from? " + listenerArguments, listenerArguments.isEmpty());
	}

	@Test
	public void testRemoveTimelineListenerItselfSprintPlanned() throws Exception {
		// Arrange
		final Member member = Member.createNormal();
		this.simulation.setMembers(member);

		final TimelineListener listener = new TimelineListener() {

			@Override
			public void eventHappened(TimelineEvent event) {
				TimelineListenerTest.this.simulation.removeTimelineListener(this);
			}
		};
		this.simulation.addTimelineListener(listener);

		// Act
		final SimulationResult result = this.simulation.runSprint();

		// Assert
		Assert.assertNotNull(result);
	}

	@Test
	public void testFireTimelineEvents() throws Exception {
		// Arrange

		final Member member8h = Member.createNormal();
		final Member member6h = Member.createNormal().workHoursPerDay(6);
		final Member[] members = new Member[]{member8h, member6h};

		final Task taskMonday8h = createTask(member8h, 8);
		final Task taskMonday6h = createTask(member6h, 6);

		final Task taskTuesday8h1 = createTask(member8h, 8);
		final Task taskTuesday8h2 = createTask(member6h, 8); // needs until Wednesday

		final Task taskWednesday1h = createTask(member8h, 1);
		final Task taskWednesday5h = createTask(member8h, 5);
		final Task taskWednesday2h = createTask(member8h, 2);
		final Task taskWednesday3h1 = createTask(member6h, 3);
		final Task taskWednesday3h2 = createTask(member6h, 3); // needs until Tuesday

		final Task[] tasks = new Task[]{taskMonday8h, taskMonday6h, taskTuesday8h1, taskTuesday8h2, taskWednesday1h,
				taskWednesday5h, taskWednesday2h, taskWednesday3h1, taskWednesday3h2};

		final List<TimelineEvent> listenerArguments = new ArrayList<>();
		final TimelineSimulationListener listener = new TimelineSimulationListener();
		listener.addTimelineListener(event -> listenerArguments.add(event));

		// Act
		listener.fireTimelineEvents(members, tasks);

		// Assert
		Assert.assertFalse(listenerArguments.isEmpty());
		final Iterator<TimelineEvent> it = listenerArguments.iterator();

		// 1) Monday
		assertNextIs(new TimelineEvent(listener, TimelineEvent.Type.TASK_STARTED, 1, 0, taskMonday8h), it);
		assertNextIs(new TimelineEvent(listener, TimelineEvent.Type.TASK_STARTED, 1, 0, taskMonday6h), it);

		// 2) Tuesday
		assertNextIs(new TimelineEvent(listener, TimelineEvent.Type.TASK_FINISHED, 2, 0, taskMonday8h), it);
		assertNextIs(new TimelineEvent(listener, TimelineEvent.Type.TASK_STARTED, 2, 0, taskTuesday8h1), it);

		assertNextIs(new TimelineEvent(listener, TimelineEvent.Type.TASK_FINISHED, 2, 0, taskMonday6h), it);
		assertNextIs(new TimelineEvent(listener, TimelineEvent.Type.TASK_STARTED, 2, 0, taskTuesday8h2), it);

		// 2) Wednesday
		assertNextIs(new TimelineEvent(listener, TimelineEvent.Type.TASK_FINISHED, 3, 0, taskTuesday8h1), it);
		assertNextIs(new TimelineEvent(listener, TimelineEvent.Type.TASK_STARTED, 3, 0, taskWednesday1h), it);

		assertNextIs(new TimelineEvent(listener, TimelineEvent.Type.TASK_FINISHED, 3, 1, taskWednesday1h), it);
		assertNextIs(new TimelineEvent(listener, TimelineEvent.Type.TASK_STARTED, 3, 1, taskWednesday5h), it);

		assertNextIs(new TimelineEvent(listener, TimelineEvent.Type.TASK_FINISHED, 3, 2, taskTuesday8h2), it);
		assertNextIs(new TimelineEvent(listener, TimelineEvent.Type.TASK_STARTED, 3, 2, taskWednesday3h1), it);
		assertNextIs(new TimelineEvent(listener, TimelineEvent.Type.TASK_FINISHED, 3, 5, taskWednesday3h1), it);
		assertNextIs(new TimelineEvent(listener, TimelineEvent.Type.TASK_STARTED, 3, 5, taskWednesday3h2), it);

		assertNextIs(new TimelineEvent(listener, TimelineEvent.Type.TASK_FINISHED, 3, 6, taskWednesday5h), it);
		assertNextIs(new TimelineEvent(listener, TimelineEvent.Type.TASK_STARTED, 3, 6, taskWednesday2h), it);

		// 2) Thursday
		assertNextIs(new TimelineEvent(listener, TimelineEvent.Type.TASK_FINISHED, 4, 0, taskWednesday2h), it);
		assertNextIs(new TimelineEvent(listener, TimelineEvent.Type.TASK_FINISHED, 4, 2, taskWednesday3h2), it);
	}

	private static Task createTask(Member assignee, double neededTime) {
		final Task result = new Task();
		result.addUserData(SprintExecutor.TASK_DATA_ASSIGNEE, assignee);
		result.addUserData(SprintExecutor.TASK_DATA_NECESSARY_HOURS, Double.valueOf(neededTime));
		return result;
	}

	private static void assertNextIs(TimelineEvent expected, Iterator<TimelineEvent> it) {
		String error = "Expected TimelineEvent: " + expected;

		Assert.assertTrue(error, it.hasNext());

		final TimelineEvent timelineEvent = it.next();
		error += ", but was: " + timelineEvent;

		Assert.assertEquals(error, expected.getType(), timelineEvent.getType());
		Assert.assertEquals(error, expected.getDay(), timelineEvent.getDay());
		Assert.assertEquals(error, expected.getHour(), timelineEvent.getHour(), DELTA);
		Assert.assertSame(error, expected.getData(), timelineEvent.getData());
	}
}
