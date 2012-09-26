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
public class CardioWorkoutIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    private CardioWorkoutDataOnDemand dod;

	@Test
    public void testCountCardioWorkouts() {
        Assert.assertNotNull("Data on demand for 'CardioWorkout' failed to initialize correctly", dod.getRandomCardioWorkout());
        long count = CardioWorkout.countCardioWorkouts();
        Assert.assertTrue("Counter for 'CardioWorkout' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFindCardioWorkout() {
        CardioWorkout obj = dod.getRandomCardioWorkout();
        Assert.assertNotNull("Data on demand for 'CardioWorkout' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'CardioWorkout' failed to provide an identifier", id);
        obj = CardioWorkout.findCardioWorkout(id);
        Assert.assertNotNull("Find method for 'CardioWorkout' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'CardioWorkout' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAllCardioWorkouts() {
        Assert.assertNotNull("Data on demand for 'CardioWorkout' failed to initialize correctly", dod.getRandomCardioWorkout());
        long count = CardioWorkout.countCardioWorkouts();
        Assert.assertTrue("Too expensive to perform a find all test for 'CardioWorkout', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<CardioWorkout> result = CardioWorkout.findAllCardioWorkouts();
        Assert.assertNotNull("Find all method for 'CardioWorkout' illegally returned null", result);
        Assert.assertTrue("Find all method for 'CardioWorkout' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindCardioWorkoutEntries() {
        Assert.assertNotNull("Data on demand for 'CardioWorkout' failed to initialize correctly", dod.getRandomCardioWorkout());
        long count = CardioWorkout.countCardioWorkouts();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<CardioWorkout> result = CardioWorkout.findCardioWorkoutEntries(firstResult, maxResults);
        Assert.assertNotNull("Find entries method for 'CardioWorkout' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'CardioWorkout' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testFlush() {
        CardioWorkout obj = dod.getRandomCardioWorkout();
        Assert.assertNotNull("Data on demand for 'CardioWorkout' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'CardioWorkout' failed to provide an identifier", id);
        obj = CardioWorkout.findCardioWorkout(id);
        Assert.assertNotNull("Find method for 'CardioWorkout' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyCardioWorkout(obj);
        Integer currentVersion = obj.getVersion();
        obj.flush();
        Assert.assertTrue("Version for 'CardioWorkout' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testMergeUpdate() {
        CardioWorkout obj = dod.getRandomCardioWorkout();
        Assert.assertNotNull("Data on demand for 'CardioWorkout' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'CardioWorkout' failed to provide an identifier", id);
        obj = CardioWorkout.findCardioWorkout(id);
        boolean modified =  dod.modifyCardioWorkout(obj);
        Integer currentVersion = obj.getVersion();
        CardioWorkout merged = obj.merge();
        obj.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'CardioWorkout' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testPersist() {
        Assert.assertNotNull("Data on demand for 'CardioWorkout' failed to initialize correctly", dod.getRandomCardioWorkout());
        CardioWorkout obj = dod.getNewTransientCardioWorkout(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'CardioWorkout' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'CardioWorkout' identifier to be null", obj.getId());
        obj.persist();
        obj.flush();
        Assert.assertNotNull("Expected 'CardioWorkout' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testRemove() {
        CardioWorkout obj = dod.getRandomCardioWorkout();
        Assert.assertNotNull("Data on demand for 'CardioWorkout' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'CardioWorkout' failed to provide an identifier", id);
        obj = CardioWorkout.findCardioWorkout(id);
        obj.remove();
        obj.flush();
        Assert.assertNull("Failed to remove 'CardioWorkout' with identifier '" + id + "'", CardioWorkout.findCardioWorkout(id));
    }
}
