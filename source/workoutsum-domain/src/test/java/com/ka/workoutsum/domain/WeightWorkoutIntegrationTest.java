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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml")
@Transactional
@Configurable
public class WeightWorkoutIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    private WeightWorkoutDataOnDemand dod;

	@Test
    public void testCountWeightWorkouts() {
        Assert.assertNotNull("Data on demand for 'WeightWorkout' failed to initialize correctly", dod.getRandomWeightWorkout());
        long count = WeightWorkout.countWeightWorkouts();
        Assert.assertTrue("Counter for 'WeightWorkout' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFindWeightWorkout() {
        WeightWorkout obj = dod.getRandomWeightWorkout();
        Assert.assertNotNull("Data on demand for 'WeightWorkout' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'WeightWorkout' failed to provide an identifier", id);
        obj = WeightWorkout.findWeightWorkout(id);
        Assert.assertNotNull("Find method for 'WeightWorkout' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'WeightWorkout' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAllWeightWorkouts() {
        Assert.assertNotNull("Data on demand for 'WeightWorkout' failed to initialize correctly", dod.getRandomWeightWorkout());
        long count = WeightWorkout.countWeightWorkouts();
        Assert.assertTrue("Too expensive to perform a find all test for 'WeightWorkout', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<WeightWorkout> result = WeightWorkout.findAllWeightWorkouts();
        Assert.assertNotNull("Find all method for 'WeightWorkout' illegally returned null", result);
        Assert.assertTrue("Find all method for 'WeightWorkout' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindWeightWorkoutEntries() {
        Assert.assertNotNull("Data on demand for 'WeightWorkout' failed to initialize correctly", dod.getRandomWeightWorkout());
        long count = WeightWorkout.countWeightWorkouts();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<WeightWorkout> result = WeightWorkout.findWeightWorkoutEntries(firstResult, maxResults);
        Assert.assertNotNull("Find entries method for 'WeightWorkout' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'WeightWorkout' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testFlush() {
        WeightWorkout obj = dod.getRandomWeightWorkout();
        Assert.assertNotNull("Data on demand for 'WeightWorkout' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'WeightWorkout' failed to provide an identifier", id);
        obj = WeightWorkout.findWeightWorkout(id);
        Assert.assertNotNull("Find method for 'WeightWorkout' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyWeightWorkout(obj);
        Integer currentVersion = obj.getVersion();
        obj.flush();
        Assert.assertTrue("Version for 'WeightWorkout' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testMergeUpdate() {
        WeightWorkout obj = dod.getRandomWeightWorkout();
        Assert.assertNotNull("Data on demand for 'WeightWorkout' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'WeightWorkout' failed to provide an identifier", id);
        obj = WeightWorkout.findWeightWorkout(id);
        boolean modified =  dod.modifyWeightWorkout(obj);
        Integer currentVersion = obj.getVersion();
        WeightWorkout merged = obj.merge();
        obj.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'WeightWorkout' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testPersist() {
        Assert.assertNotNull("Data on demand for 'WeightWorkout' failed to initialize correctly", dod.getRandomWeightWorkout());
        WeightWorkout obj = dod.getNewTransientWeightWorkout(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'WeightWorkout' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'WeightWorkout' identifier to be null", obj.getId());
        obj.persist();
        obj.flush();
        Assert.assertNotNull("Expected 'WeightWorkout' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testRemove() {
        WeightWorkout obj = dod.getRandomWeightWorkout();
        Assert.assertNotNull("Data on demand for 'WeightWorkout' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'WeightWorkout' failed to provide an identifier", id);
        obj = WeightWorkout.findWeightWorkout(id);
        obj.remove();
        obj.flush();
        Assert.assertNull("Failed to remove 'WeightWorkout' with identifier '" + id + "'", WeightWorkout.findWeightWorkout(id));
    }
}
