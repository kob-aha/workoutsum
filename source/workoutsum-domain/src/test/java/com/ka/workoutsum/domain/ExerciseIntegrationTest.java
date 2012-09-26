package com.ka.workoutsum.domain;

import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@Configurable
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml")
@Transactional
public class ExerciseIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    private ExerciseDataOnDemand dod;

	@Test
    public void testCountExercises() {
        Assert.assertNotNull("Data on demand for 'Exercise' failed to initialize correctly", dod.getRandomExercise());
        long count = Exercise.countExercises();
        Assert.assertTrue("Counter for 'Exercise' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFindExercise() {
        Exercise obj = dod.getRandomExercise();
        Assert.assertNotNull("Data on demand for 'Exercise' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Exercise' failed to provide an identifier", id);
        obj = Exercise.findExercise(id);
        Assert.assertNotNull("Find method for 'Exercise' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'Exercise' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAllExercises() {
        Assert.assertNotNull("Data on demand for 'Exercise' failed to initialize correctly", dod.getRandomExercise());
        long count = Exercise.countExercises();
        Assert.assertTrue("Too expensive to perform a find all test for 'Exercise', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<Exercise> result = Exercise.findAllExercises();
        Assert.assertNotNull("Find all method for 'Exercise' illegally returned null", result);
        Assert.assertTrue("Find all method for 'Exercise' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindExerciseEntries() {
        Assert.assertNotNull("Data on demand for 'Exercise' failed to initialize correctly", dod.getRandomExercise());
        long count = Exercise.countExercises();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<Exercise> result = Exercise.findExerciseEntries(firstResult, maxResults);
        Assert.assertNotNull("Find entries method for 'Exercise' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'Exercise' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testFlush() {
        Exercise obj = dod.getRandomExercise();
        Assert.assertNotNull("Data on demand for 'Exercise' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Exercise' failed to provide an identifier", id);
        obj = Exercise.findExercise(id);
        Assert.assertNotNull("Find method for 'Exercise' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyExercise(obj);
        Integer currentVersion = obj.getVersion();
        obj.flush();
        Assert.assertTrue("Version for 'Exercise' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testMergeUpdate() {
        Exercise obj = dod.getRandomExercise();
        Assert.assertNotNull("Data on demand for 'Exercise' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Exercise' failed to provide an identifier", id);
        obj = Exercise.findExercise(id);
        boolean modified =  dod.modifyExercise(obj);
        Integer currentVersion = obj.getVersion();
        Exercise merged = obj.merge();
        obj.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'Exercise' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testPersist() {
        Assert.assertNotNull("Data on demand for 'Exercise' failed to initialize correctly", dod.getRandomExercise());
        Exercise obj = dod.getNewTransientExercise(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'Exercise' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'Exercise' identifier to be null", obj.getId());
        obj.persist();
        obj.flush();
        Assert.assertNotNull("Expected 'Exercise' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testRemove() {
        Exercise obj = dod.getRandomExercise();
        Assert.assertNotNull("Data on demand for 'Exercise' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Exercise' failed to provide an identifier", id);
        obj = Exercise.findExercise(id);
        obj.remove();
        obj.flush();
        Assert.assertNull("Failed to remove 'Exercise' with identifier '" + id + "'", Exercise.findExercise(id));
    }
}
