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
public class WeightWorkoutLogRecIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    private WeightWorkoutLogRecDataOnDemand dod;

	@Test
    public void testCountWeightWorkoutLogRecs() {
        Assert.assertNotNull("Data on demand for 'WeightWorkoutLogRec' failed to initialize correctly", dod.getRandomWeightWorkoutLogRec());
        long count = WeightWorkoutLogRec.countWeightWorkoutLogRecs();
        Assert.assertTrue("Counter for 'WeightWorkoutLogRec' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFindWeightWorkoutLogRec() {
        WeightWorkoutLogRec obj = dod.getRandomWeightWorkoutLogRec();
        Assert.assertNotNull("Data on demand for 'WeightWorkoutLogRec' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'WeightWorkoutLogRec' failed to provide an identifier", id);
        obj = WeightWorkoutLogRec.findWeightWorkoutLogRec(id);
        Assert.assertNotNull("Find method for 'WeightWorkoutLogRec' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'WeightWorkoutLogRec' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAllWeightWorkoutLogRecs() {
        Assert.assertNotNull("Data on demand for 'WeightWorkoutLogRec' failed to initialize correctly", dod.getRandomWeightWorkoutLogRec());
        long count = WeightWorkoutLogRec.countWeightWorkoutLogRecs();
        Assert.assertTrue("Too expensive to perform a find all test for 'WeightWorkoutLogRec', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<WeightWorkoutLogRec> result = WeightWorkoutLogRec.findAllWeightWorkoutLogRecs();
        Assert.assertNotNull("Find all method for 'WeightWorkoutLogRec' illegally returned null", result);
        Assert.assertTrue("Find all method for 'WeightWorkoutLogRec' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindWeightWorkoutLogRecEntries() {
        Assert.assertNotNull("Data on demand for 'WeightWorkoutLogRec' failed to initialize correctly", dod.getRandomWeightWorkoutLogRec());
        long count = WeightWorkoutLogRec.countWeightWorkoutLogRecs();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<WeightWorkoutLogRec> result = WeightWorkoutLogRec.findWeightWorkoutLogRecEntries(firstResult, maxResults);
        Assert.assertNotNull("Find entries method for 'WeightWorkoutLogRec' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'WeightWorkoutLogRec' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testFlush() {
        WeightWorkoutLogRec obj = dod.getRandomWeightWorkoutLogRec();
        Assert.assertNotNull("Data on demand for 'WeightWorkoutLogRec' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'WeightWorkoutLogRec' failed to provide an identifier", id);
        obj = WeightWorkoutLogRec.findWeightWorkoutLogRec(id);
        Assert.assertNotNull("Find method for 'WeightWorkoutLogRec' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyWeightWorkoutLogRec(obj);
        Integer currentVersion = obj.getVersion();
        obj.flush();
        Assert.assertTrue("Version for 'WeightWorkoutLogRec' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testMergeUpdate() {
        WeightWorkoutLogRec obj = dod.getRandomWeightWorkoutLogRec();
        Assert.assertNotNull("Data on demand for 'WeightWorkoutLogRec' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'WeightWorkoutLogRec' failed to provide an identifier", id);
        obj = WeightWorkoutLogRec.findWeightWorkoutLogRec(id);
        boolean modified =  dod.modifyWeightWorkoutLogRec(obj);
        Integer currentVersion = obj.getVersion();
        WeightWorkoutLogRec merged = obj.merge();
        obj.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'WeightWorkoutLogRec' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testPersist() {
        Assert.assertNotNull("Data on demand for 'WeightWorkoutLogRec' failed to initialize correctly", dod.getRandomWeightWorkoutLogRec());
        WeightWorkoutLogRec obj = dod.getNewTransientWeightWorkoutLogRec(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'WeightWorkoutLogRec' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'WeightWorkoutLogRec' identifier to be null", obj.getId());
        obj.persist();
        obj.flush();
        Assert.assertNotNull("Expected 'WeightWorkoutLogRec' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testRemove() {
        WeightWorkoutLogRec obj = dod.getRandomWeightWorkoutLogRec();
        Assert.assertNotNull("Data on demand for 'WeightWorkoutLogRec' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'WeightWorkoutLogRec' failed to provide an identifier", id);
        obj = WeightWorkoutLogRec.findWeightWorkoutLogRec(id);
        obj.remove();
        obj.flush();
        Assert.assertNull("Failed to remove 'WeightWorkoutLogRec' with identifier '" + id + "'", WeightWorkoutLogRec.findWeightWorkoutLogRec(id));
    }
}
