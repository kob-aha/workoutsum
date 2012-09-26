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
public class MuscleIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    private MuscleDataOnDemand dod;

	@Test
    public void testCountMuscles() {
        Assert.assertNotNull("Data on demand for 'Muscle' failed to initialize correctly", dod.getRandomMuscle());
        long count = Muscle.countMuscles();
        Assert.assertTrue("Counter for 'Muscle' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFindMuscle() {
        Muscle obj = dod.getRandomMuscle();
        Assert.assertNotNull("Data on demand for 'Muscle' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Muscle' failed to provide an identifier", id);
        obj = Muscle.findMuscle(id);
        Assert.assertNotNull("Find method for 'Muscle' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'Muscle' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAllMuscles() {
        Assert.assertNotNull("Data on demand for 'Muscle' failed to initialize correctly", dod.getRandomMuscle());
        long count = Muscle.countMuscles();
        Assert.assertTrue("Too expensive to perform a find all test for 'Muscle', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<Muscle> result = Muscle.findAllMuscles();
        Assert.assertNotNull("Find all method for 'Muscle' illegally returned null", result);
        Assert.assertTrue("Find all method for 'Muscle' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindMuscleEntries() {
        Assert.assertNotNull("Data on demand for 'Muscle' failed to initialize correctly", dod.getRandomMuscle());
        long count = Muscle.countMuscles();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<Muscle> result = Muscle.findMuscleEntries(firstResult, maxResults);
        Assert.assertNotNull("Find entries method for 'Muscle' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'Muscle' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testFlush() {
        Muscle obj = dod.getRandomMuscle();
        Assert.assertNotNull("Data on demand for 'Muscle' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Muscle' failed to provide an identifier", id);
        obj = Muscle.findMuscle(id);
        Assert.assertNotNull("Find method for 'Muscle' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyMuscle(obj);
        Integer currentVersion = obj.getVersion();
        obj.flush();
        Assert.assertTrue("Version for 'Muscle' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testMergeUpdate() {
        Muscle obj = dod.getRandomMuscle();
        Assert.assertNotNull("Data on demand for 'Muscle' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Muscle' failed to provide an identifier", id);
        obj = Muscle.findMuscle(id);
        boolean modified =  dod.modifyMuscle(obj);
        Integer currentVersion = obj.getVersion();
        Muscle merged = obj.merge();
        obj.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'Muscle' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testPersist() {
        Assert.assertNotNull("Data on demand for 'Muscle' failed to initialize correctly", dod.getRandomMuscle());
        Muscle obj = dod.getNewTransientMuscle(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'Muscle' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'Muscle' identifier to be null", obj.getId());
        obj.persist();
        obj.flush();
        Assert.assertNotNull("Expected 'Muscle' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testRemove() {
        Muscle obj = dod.getRandomMuscle();
        Assert.assertNotNull("Data on demand for 'Muscle' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Muscle' failed to provide an identifier", id);
        obj = Muscle.findMuscle(id);
        obj.remove();
        obj.flush();
        Assert.assertNull("Failed to remove 'Muscle' with identifier '" + id + "'", Muscle.findMuscle(id));
    }
}
