package com.ka.workoutsum.domain;

import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.ka.workoutsum.domain.repository.CardioLogRecRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml")
@Transactional
@Configurable
public class CardioLogRecIntegrationTest {

	@Autowired
    private CardioLogRecDataOnDemand dod;
	
	@Autowired
	private CardioLogRecRepository cardioLogRecRepository;

	@Test
    public void testCountCardioLogRecs() {
        Assert.assertNotNull("Data on demand for 'CardioLogRec' failed to initialize correctly", dod.getRandomCardioLogRec());
        long count = cardioLogRecRepository.count();
        Assert.assertTrue("Counter for 'CardioLogRec' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFindCardioLogRec() {
        CardioLogRec obj = dod.getRandomCardioLogRec();
        Assert.assertNotNull("Data on demand for 'CardioLogRec' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'CardioLogRec' failed to provide an identifier", id);
        obj = cardioLogRecRepository.findOne(id);
        Assert.assertNotNull("Find method for 'CardioLogRec' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'CardioLogRec' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAllCardioLogRecs() {
        Assert.assertNotNull("Data on demand for 'CardioLogRec' failed to initialize correctly", dod.getRandomCardioLogRec());
        long count = cardioLogRecRepository.count();
        Assert.assertTrue("Too expensive to perform a find all test for 'CardioLogRec', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<CardioLogRec> result = cardioLogRecRepository.findAll();
        Assert.assertNotNull("Find all method for 'CardioLogRec' illegally returned null", result);
        Assert.assertTrue("Find all method for 'CardioLogRec' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindCardioLogRecEntries() {
        Assert.assertNotNull("Data on demand for 'CardioLogRec' failed to initialize correctly", dod.getRandomCardioLogRec());
        long count = cardioLogRecRepository.count();
        if (count > 20) count = 20;
        final int firstResult = 0;
        final int maxResults = (int) count;
        List<CardioLogRec> result = cardioLogRecRepository.findAll(new PageRequest(firstResult, maxResults)).getContent();//, maxResults);
        Assert.assertNotNull("Find entries method for 'CardioLogRec' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'CardioLogRec' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testFlush() {
        CardioLogRec obj = dod.getRandomCardioLogRec();
        Assert.assertNotNull("Data on demand for 'CardioLogRec' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'CardioLogRec' failed to provide an identifier", id);
        obj = cardioLogRecRepository.findOne(id);
        Assert.assertNotNull("Find method for 'CardioLogRec' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyCardioLogRec(obj);
        Integer currentVersion = obj.getVersion();
        cardioLogRecRepository.flush();
        Assert.assertTrue("Version for 'CardioLogRec' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testMergeUpdate() {
        CardioLogRec obj = dod.getRandomCardioLogRec();
        Assert.assertNotNull("Data on demand for 'CardioLogRec' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'CardioLogRec' failed to provide an identifier", id);
        obj = cardioLogRecRepository.findOne(id);
        boolean modified =  dod.modifyCardioLogRec(obj);
        Integer currentVersion = obj.getVersion();
        CardioLogRec merged = cardioLogRecRepository.save(obj);
        cardioLogRecRepository.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'CardioLogRec' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testPersist() {
        Assert.assertNotNull("Data on demand for 'CardioLogRec' failed to initialize correctly", dod.getRandomCardioLogRec());
        CardioLogRec obj = dod.getNewTransientCardioLogRec(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'CardioLogRec' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'CardioLogRec' identifier to be null", obj.getId());
        cardioLogRecRepository.save(obj);
        cardioLogRecRepository.flush();
        Assert.assertNotNull("Expected 'CardioLogRec' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testRemove() {
        CardioLogRec obj = dod.getRandomCardioLogRec();
        Assert.assertNotNull("Data on demand for 'CardioLogRec' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'CardioLogRec' failed to provide an identifier", id);
        obj = cardioLogRecRepository.findOne(id);
        cardioLogRecRepository.delete(obj);
        cardioLogRecRepository.flush();
        Assert.assertNull("Failed to remove 'CardioLogRec' with identifier '" + id + "'", cardioLogRecRepository.findOne(id));
    }
}
