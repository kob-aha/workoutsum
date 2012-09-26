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
public class ExerciseLogRecIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    private ExerciseLogRecDataOnDemand dod;

	@Test
    public void testCountExerciseLogRecs() {
        Assert.assertNotNull("Data on demand for 'ExerciseLogRec' failed to initialize correctly", dod.getRandomExerciseLogRec());
        long count = ExerciseLogRec.countExerciseLogRecs();
        Assert.assertTrue("Counter for 'ExerciseLogRec' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFindExerciseLogRec() {
        ExerciseLogRec obj = dod.getRandomExerciseLogRec();
        Assert.assertNotNull("Data on demand for 'ExerciseLogRec' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'ExerciseLogRec' failed to provide an identifier", id);
        obj = ExerciseLogRec.findExerciseLogRec(id);
        Assert.assertNotNull("Find method for 'ExerciseLogRec' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'ExerciseLogRec' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAllExerciseLogRecs() {
        Assert.assertNotNull("Data on demand for 'ExerciseLogRec' failed to initialize correctly", dod.getRandomExerciseLogRec());
        long count = ExerciseLogRec.countExerciseLogRecs();
        Assert.assertTrue("Too expensive to perform a find all test for 'ExerciseLogRec', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<ExerciseLogRec> result = ExerciseLogRec.findAllExerciseLogRecs();
        Assert.assertNotNull("Find all method for 'ExerciseLogRec' illegally returned null", result);
        Assert.assertTrue("Find all method for 'ExerciseLogRec' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindExerciseLogRecEntries() {
        Assert.assertNotNull("Data on demand for 'ExerciseLogRec' failed to initialize correctly", dod.getRandomExerciseLogRec());
        long count = ExerciseLogRec.countExerciseLogRecs();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<ExerciseLogRec> result = ExerciseLogRec.findExerciseLogRecEntries(firstResult, maxResults);
        Assert.assertNotNull("Find entries method for 'ExerciseLogRec' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'ExerciseLogRec' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testFlush() {
        ExerciseLogRec obj = dod.getRandomExerciseLogRec();
        Assert.assertNotNull("Data on demand for 'ExerciseLogRec' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'ExerciseLogRec' failed to provide an identifier", id);
        obj = ExerciseLogRec.findExerciseLogRec(id);
        Assert.assertNotNull("Find method for 'ExerciseLogRec' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyExerciseLogRec(obj);
        Integer currentVersion = obj.getVersion();
        obj.flush();
        Assert.assertTrue("Version for 'ExerciseLogRec' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testMergeUpdate() {
        ExerciseLogRec obj = dod.getRandomExerciseLogRec();
        Assert.assertNotNull("Data on demand for 'ExerciseLogRec' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'ExerciseLogRec' failed to provide an identifier", id);
        obj = ExerciseLogRec.findExerciseLogRec(id);
        boolean modified =  dod.modifyExerciseLogRec(obj);
        Integer currentVersion = obj.getVersion();
        ExerciseLogRec merged = obj.merge();
        obj.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'ExerciseLogRec' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testPersist() {
        Assert.assertNotNull("Data on demand for 'ExerciseLogRec' failed to initialize correctly", dod.getRandomExerciseLogRec());
        ExerciseLogRec obj = dod.getNewTransientExerciseLogRec(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'ExerciseLogRec' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'ExerciseLogRec' identifier to be null", obj.getId());
        obj.persist();
        obj.flush();
        Assert.assertNotNull("Expected 'ExerciseLogRec' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testRemove() {
        ExerciseLogRec obj = dod.getRandomExerciseLogRec();
        Assert.assertNotNull("Data on demand for 'ExerciseLogRec' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'ExerciseLogRec' failed to provide an identifier", id);
        obj = ExerciseLogRec.findExerciseLogRec(id);
        obj.remove();
        obj.flush();
        Assert.assertNull("Failed to remove 'ExerciseLogRec' with identifier '" + id + "'", ExerciseLogRec.findExerciseLogRec(id));
    }
}
