package de.slothsoft.sprintsim.generation;

import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.slothsoft.sprintsim.Complexity;
import de.slothsoft.sprintsim.Member;
import de.slothsoft.sprintsim.Task;

public class SprintGeneratorTest {

	private static final double DELTA = 0.001;

	private SprintGenerator generator;

	@Before
	public void setUp() {
		this.generator = new SprintGenerator().seed(1234567790l);
	}

	@Test
	public void testCreateExecutor() throws Exception {
		// Arrange
		final Member member = Member.createNormal();
		this.generator.setMembers(member);

		// Act
		final SprintPlanning planning = this.generator.generate();

		// Assert
		Assert.assertNotNull(planning);
		Assert.assertNotNull(planning.sprint);

		// the one member has 80h
		Assert.assertTrue("Too few work hours assigned: " + planning.estimatedHours,
				planning.getEstimatedHours() >= 80.0);
		Assert.assertEquals(planning.getEstimatedHours() - 80, planning.getEstimatedAdditionalHours(), DELTA);

		// on default the three types of task should have the same number
		// 80 / (2 + 4 + 16) = 3.6, but it's random, so let's assume 2 of each

		final int minNumberOfTasks = 2;

		for (final Complexity complexity : Complexity.values()) {
			final Task[] filteredTasks = findTasks(planning.sprint.getTasks(), i -> i.getComplexity() == complexity);
			Assert.assertTrue("Tasks with complexity " + complexity + " are too few: " + filteredTasks.length,
					filteredTasks.length >= minNumberOfTasks);
		}
	}

	private static Task[] findTasks(Task[] tasks, Predicate<Task> filter) {
		return Arrays.stream(tasks).filter(filter).toArray(Task[]::new);
	}
}
