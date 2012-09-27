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

import com.ka.workoutsum.domain.repository.ExerciseSetRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml")
@Transactional
@Configurable
public class ExerciseSetIntegrationTest {

	@Autowired
    private ExerciseSetDataOnDemand dod;
	
	@Autowired
	private ExerciseSetRepository exerciseSetRepository;

	@Test
    public void testCountExerciseSets() {
        Assert.assertNotNull("Data on demand for 'ExerciseSet' failed to initialize correctly", dod.getRandomExerciseSet());
        long count = exerciseSetRepository.count();
        Assert.assertTrue("Counter for 'ExerciseSet' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFindExerciseSet() {
        ExerciseSet obj = dod.getRandomExerciseSet();
        Assert.assertNotNull("Data on demand for 'ExerciseSet' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'ExerciseSet' failed to provide an identifier", id);
        obj = exerciseSetRepository.findOne(id);
        Assert.assertNotNull("Find method for 'ExerciseSet' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'ExerciseSet' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAllExerciseSets() {
        Assert.assertNotNull("Data on demand for 'ExerciseSet' failed to initialize correctly", dod.getRandomExerciseSet());
        long count = exerciseSetRepository.count();
        Assert.assertTrue("Too expensive to perform a find all test for 'ExerciseSet', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<ExerciseSet> result = exerciseSetRepository.findAll();
        Assert.assertNotNull("Find all method for 'ExerciseSet' illegally returned null", result);
        Assert.assertTrue("Find all method for 'ExerciseSet' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindExerciseSetEntries() {
        Assert.assertNotNull("Data on demand for 'ExerciseSet' failed to initialize correctly", dod.getRandomExerciseSet());
        long count = exerciseSetRepository.count();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<ExerciseSet> result = exerciseSetRepository.findAll(new PageRequest(firstResult, maxResults)).getContent();
        Assert.assertNotNull("Find entries method for 'ExerciseSet' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'ExerciseSet' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testFlush() {
        ExerciseSet obj = dod.getRandomExerciseSet();
        Assert.assertNotNull("Data on demand for 'ExerciseSet' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'ExerciseSet' failed to provide an identifier", id);
        obj = exerciseSetRepository.findOne(id);
        Assert.assertNotNull("Find method for 'ExerciseSet' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyExerciseSet(obj);
        Integer currentVersion = obj.getVersion();
        exerciseSetRepository.flush();
        Assert.assertTrue("Version for 'ExerciseSet' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testMergeUpdate() {
        ExerciseSet obj = dod.getRandomExerciseSet();
        Assert.assertNotNull("Data on demand for 'ExerciseSet' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'ExerciseSet' failed to provide an identifier", id);
        obj = exerciseSetRepository.findOne(id);
        boolean modified =  dod.modifyExerciseSet(obj);
        Integer currentVersion = obj.getVersion();
        ExerciseSet merged = exerciseSetRepository.save(obj);
        exerciseSetRepository.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'ExerciseSet' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testPersist() {
        Assert.assertNotNull("Data on demand for 'ExerciseSet' failed to initialize correctly", dod.getRandomExerciseSet());
        ExerciseSet obj = dod.getNewTransientExerciseSet(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'ExerciseSet' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'ExerciseSet' identifier to be null", obj.getId());
        exerciseSetRepository.save(obj);
        exerciseSetRepository.flush();
        Assert.assertNotNull("Expected 'ExerciseSet' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testRemove() {
        ExerciseSet obj = dod.getRandomExerciseSet();
        Assert.assertNotNull("Data on demand for 'ExerciseSet' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'ExerciseSet' failed to provide an identifier", id);
        obj = exerciseSetRepository.findOne(id);
        exerciseSetRepository.delete(obj);
        exerciseSetRepository.flush();
        Assert.assertNull("Failed to remove 'ExerciseSet' with identifier '" + id + "'", exerciseSetRepository.findOne(id));
    }
}
