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
public class ExerciseSetIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    private ExerciseSetDataOnDemand dod;

	@Test
    public void testCountExerciseSets() {
        Assert.assertNotNull("Data on demand for 'ExerciseSet' failed to initialize correctly", dod.getRandomExerciseSet());
        long count = ExerciseSet.countExerciseSets();
        Assert.assertTrue("Counter for 'ExerciseSet' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFindExerciseSet() {
        ExerciseSet obj = dod.getRandomExerciseSet();
        Assert.assertNotNull("Data on demand for 'ExerciseSet' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'ExerciseSet' failed to provide an identifier", id);
        obj = ExerciseSet.findExerciseSet(id);
        Assert.assertNotNull("Find method for 'ExerciseSet' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'ExerciseSet' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAllExerciseSets() {
        Assert.assertNotNull("Data on demand for 'ExerciseSet' failed to initialize correctly", dod.getRandomExerciseSet());
        long count = ExerciseSet.countExerciseSets();
        Assert.assertTrue("Too expensive to perform a find all test for 'ExerciseSet', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<ExerciseSet> result = ExerciseSet.findAllExerciseSets();
        Assert.assertNotNull("Find all method for 'ExerciseSet' illegally returned null", result);
        Assert.assertTrue("Find all method for 'ExerciseSet' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindExerciseSetEntries() {
        Assert.assertNotNull("Data on demand for 'ExerciseSet' failed to initialize correctly", dod.getRandomExerciseSet());
        long count = ExerciseSet.countExerciseSets();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<ExerciseSet> result = ExerciseSet.findExerciseSetEntries(firstResult, maxResults);
        Assert.assertNotNull("Find entries method for 'ExerciseSet' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'ExerciseSet' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testFlush() {
        ExerciseSet obj = dod.getRandomExerciseSet();
        Assert.assertNotNull("Data on demand for 'ExerciseSet' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'ExerciseSet' failed to provide an identifier", id);
        obj = ExerciseSet.findExerciseSet(id);
        Assert.assertNotNull("Find method for 'ExerciseSet' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyExerciseSet(obj);
        Integer currentVersion = obj.getVersion();
        obj.flush();
        Assert.assertTrue("Version for 'ExerciseSet' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testMergeUpdate() {
        ExerciseSet obj = dod.getRandomExerciseSet();
        Assert.assertNotNull("Data on demand for 'ExerciseSet' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'ExerciseSet' failed to provide an identifier", id);
        obj = ExerciseSet.findExerciseSet(id);
        boolean modified =  dod.modifyExerciseSet(obj);
        Integer currentVersion = obj.getVersion();
        ExerciseSet merged = obj.merge();
        obj.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'ExerciseSet' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testPersist() {
        Assert.assertNotNull("Data on demand for 'ExerciseSet' failed to initialize correctly", dod.getRandomExerciseSet());
        ExerciseSet obj = dod.getNewTransientExerciseSet(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'ExerciseSet' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'ExerciseSet' identifier to be null", obj.getId());
        obj.persist();
        obj.flush();
        Assert.assertNotNull("Expected 'ExerciseSet' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testRemove() {
        ExerciseSet obj = dod.getRandomExerciseSet();
        Assert.assertNotNull("Data on demand for 'ExerciseSet' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'ExerciseSet' failed to provide an identifier", id);
        obj = ExerciseSet.findExerciseSet(id);
        obj.remove();
        obj.flush();
        Assert.assertNull("Failed to remove 'ExerciseSet' with identifier '" + id + "'", ExerciseSet.findExerciseSet(id));
    }
}
