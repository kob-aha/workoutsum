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

import com.ka.workoutsum.domain.repository.WorkoutExerciseRepository;

@Configurable
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml")
@Transactional
public class WorkoutExerciseIntegrationTest {

	@Autowired
    private WorkoutExerciseRepository workoutExerciseRepository;

	@Autowired
    private WorkoutExerciseDataOnDemand dod;

	@Test
    public void testCountWorkoutExercises() {
        Assert.assertNotNull("Data on demand for 'WorkoutExercise' failed to initialize correctly", dod.getRandomWorkoutExercise());
        long count = workoutExerciseRepository.count();
        Assert.assertTrue("Counter for 'WorkoutExercise' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFindWorkoutExercise() {
        WorkoutExercise obj = dod.getRandomWorkoutExercise();
        Assert.assertNotNull("Data on demand for 'WorkoutExercise' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'WorkoutExercise' failed to provide an identifier", id);
        obj = workoutExerciseRepository.findOne(id);
        Assert.assertNotNull("Find method for 'WorkoutExercise' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'WorkoutExercise' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAllWorkoutExercises() {
        Assert.assertNotNull("Data on demand for 'WorkoutExercise' failed to initialize correctly", dod.getRandomWorkoutExercise());
        long count = workoutExerciseRepository.count();
        Assert.assertTrue("Too expensive to perform a find all test for 'WorkoutExercise', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<WorkoutExercise> result = workoutExerciseRepository.findAll();
        Assert.assertNotNull("Find all method for 'WorkoutExercise' illegally returned null", result);
        Assert.assertTrue("Find all method for 'WorkoutExercise' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindWorkoutExerciseEntries() {
        Assert.assertNotNull("Data on demand for 'WorkoutExercise' failed to initialize correctly", dod.getRandomWorkoutExercise());
        long count = workoutExerciseRepository.count();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<WorkoutExercise> result = workoutExerciseRepository.findAll(new PageRequest(firstResult, maxResults)).getContent();
        Assert.assertNotNull("Find entries method for 'WorkoutExercise' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'WorkoutExercise' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testFlush() {
        WorkoutExercise obj = dod.getRandomWorkoutExercise();
        Assert.assertNotNull("Data on demand for 'WorkoutExercise' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'WorkoutExercise' failed to provide an identifier", id);
        obj = workoutExerciseRepository.findOne(id);
        Assert.assertNotNull("Find method for 'WorkoutExercise' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyWorkoutExercise(obj);
        Integer currentVersion = obj.getVersion();
        workoutExerciseRepository.flush();
        Assert.assertTrue("Version for 'WorkoutExercise' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testMergeUpdate() {
        WorkoutExercise obj = dod.getRandomWorkoutExercise();
        Assert.assertNotNull("Data on demand for 'WorkoutExercise' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'WorkoutExercise' failed to provide an identifier", id);
        obj = workoutExerciseRepository.findOne(id);
        boolean modified =  dod.modifyWorkoutExercise(obj);
        Integer currentVersion = obj.getVersion();
        WorkoutExercise merged = workoutExerciseRepository.save(obj);
        workoutExerciseRepository.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'WorkoutExercise' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testPersist() {
        Assert.assertNotNull("Data on demand for 'WorkoutExercise' failed to initialize correctly", dod.getRandomWorkoutExercise());
        WorkoutExercise obj = dod.getNewTransientWorkoutExercise(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'WorkoutExercise' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'WorkoutExercise' identifier to be null", obj.getId());
        workoutExerciseRepository.save(obj);
        workoutExerciseRepository.flush();
        Assert.assertNotNull("Expected 'WorkoutExercise' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testRemove() {
        WorkoutExercise obj = dod.getRandomWorkoutExercise();
        Assert.assertNotNull("Data on demand for 'WorkoutExercise' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'WorkoutExercise' failed to provide an identifier", id);
        obj = workoutExerciseRepository.findOne(id);
        workoutExerciseRepository.delete(obj);
        workoutExerciseRepository.flush();
        Assert.assertNull("Failed to remove 'WorkoutExercise' with identifier '" + id + "'", workoutExerciseRepository.findOne(id));
    }
}
