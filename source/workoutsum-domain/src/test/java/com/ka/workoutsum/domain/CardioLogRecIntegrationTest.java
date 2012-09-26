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
public class CardioLogRecIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    private CardioLogRecDataOnDemand dod;

	@Test
    public void testCountCardioLogRecs() {
        Assert.assertNotNull("Data on demand for 'CardioLogRec' failed to initialize correctly", dod.getRandomCardioLogRec());
        long count = CardioLogRec.countCardioLogRecs();
        Assert.assertTrue("Counter for 'CardioLogRec' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFindCardioLogRec() {
        CardioLogRec obj = dod.getRandomCardioLogRec();
        Assert.assertNotNull("Data on demand for 'CardioLogRec' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'CardioLogRec' failed to provide an identifier", id);
        obj = CardioLogRec.findCardioLogRec(id);
        Assert.assertNotNull("Find method for 'CardioLogRec' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'CardioLogRec' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAllCardioLogRecs() {
        Assert.assertNotNull("Data on demand for 'CardioLogRec' failed to initialize correctly", dod.getRandomCardioLogRec());
        long count = CardioLogRec.countCardioLogRecs();
        Assert.assertTrue("Too expensive to perform a find all test for 'CardioLogRec', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<CardioLogRec> result = CardioLogRec.findAllCardioLogRecs();
        Assert.assertNotNull("Find all method for 'CardioLogRec' illegally returned null", result);
        Assert.assertTrue("Find all method for 'CardioLogRec' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindCardioLogRecEntries() {
        Assert.assertNotNull("Data on demand for 'CardioLogRec' failed to initialize correctly", dod.getRandomCardioLogRec());
        long count = CardioLogRec.countCardioLogRecs();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<CardioLogRec> result = CardioLogRec.findCardioLogRecEntries(firstResult, maxResults);
        Assert.assertNotNull("Find entries method for 'CardioLogRec' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'CardioLogRec' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testFlush() {
        CardioLogRec obj = dod.getRandomCardioLogRec();
        Assert.assertNotNull("Data on demand for 'CardioLogRec' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'CardioLogRec' failed to provide an identifier", id);
        obj = CardioLogRec.findCardioLogRec(id);
        Assert.assertNotNull("Find method for 'CardioLogRec' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyCardioLogRec(obj);
        Integer currentVersion = obj.getVersion();
        obj.flush();
        Assert.assertTrue("Version for 'CardioLogRec' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testMergeUpdate() {
        CardioLogRec obj = dod.getRandomCardioLogRec();
        Assert.assertNotNull("Data on demand for 'CardioLogRec' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'CardioLogRec' failed to provide an identifier", id);
        obj = CardioLogRec.findCardioLogRec(id);
        boolean modified =  dod.modifyCardioLogRec(obj);
        Integer currentVersion = obj.getVersion();
        CardioLogRec merged = obj.merge();
        obj.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'CardioLogRec' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testPersist() {
        Assert.assertNotNull("Data on demand for 'CardioLogRec' failed to initialize correctly", dod.getRandomCardioLogRec());
        CardioLogRec obj = dod.getNewTransientCardioLogRec(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'CardioLogRec' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'CardioLogRec' identifier to be null", obj.getId());
        obj.persist();
        obj.flush();
        Assert.assertNotNull("Expected 'CardioLogRec' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testRemove() {
        CardioLogRec obj = dod.getRandomCardioLogRec();
        Assert.assertNotNull("Data on demand for 'CardioLogRec' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'CardioLogRec' failed to provide an identifier", id);
        obj = CardioLogRec.findCardioLogRec(id);
        obj.remove();
        obj.flush();
        Assert.assertNull("Failed to remove 'CardioLogRec' with identifier '" + id + "'", CardioLogRec.findCardioLogRec(id));
    }
}
