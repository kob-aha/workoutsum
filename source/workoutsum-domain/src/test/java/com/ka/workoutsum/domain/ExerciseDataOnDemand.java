package com.ka.workoutsum.domain;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import com.ka.workoutsum.domain.repository.ExerciseRepository;

@Component
@Configurable
public class ExerciseDataOnDemand {

	private Random rnd = new SecureRandom();

	private List<Exercise> data;

	@Autowired
    private MuscleDataOnDemand muscleDataOnDemand;
	
	@Autowired
	private ExerciseRepository exerciseRepository;

	public Exercise getNewTransientExercise(int index) {
        Exercise obj = new Exercise();
        setName(obj, index);
        setTargetedMuscle(obj, index);
        return obj;
    }

	public void setName(Exercise obj, int index) {
        String name = "name_" + index;
        obj.setName(name);
    }

	public void setTargetedMuscle(Exercise obj, int index) {
        Muscle targetedMuscle = muscleDataOnDemand.getRandomMuscle();
        obj.setTargetedMuscle(targetedMuscle);
    }

	public Exercise getSpecificExercise(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        Exercise obj = data.get(index);
        Long id = obj.getId();
        return exerciseRepository.findOne(id);
    }

	public Exercise getRandomExercise() {
        init();
        Exercise obj = data.get(rnd.nextInt(data.size()));
        Long id = obj.getId();
        return exerciseRepository.findOne(id);
    }

	public boolean modifyExercise(Exercise obj) {
        return false;
    }

	public void init() {
        int from = 0;
        int to = 10;
        data = exerciseRepository.findAll(new PageRequest(from, to)).getContent();
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'Exercise' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<Exercise>();
        for (int i = 0; i < 10; i++) {
            Exercise obj = getNewTransientExercise(i);
            try {
            	exerciseRepository.save(obj);
            } catch (ConstraintViolationException e) {
                StringBuilder msg = new StringBuilder();
                for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                    ConstraintViolation<?> cv = iter.next();
                    msg.append("[").append(cv.getConstraintDescriptor()).append(":").append(cv.getMessage()).append("=").append(cv.getInvalidValue()).append("]");
                }
                throw new RuntimeException(msg.toString(), e);
            }
            exerciseRepository.flush();
            data.add(obj);
        }
    }
}
