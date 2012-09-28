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

import com.ka.workoutsum.domain.repository.ExerciseLogRecRepository;

@Component
@Configurable
public class ExerciseLogRecDataOnDemand {

	private Random rnd = new SecureRandom();

	private List<ExerciseLogRec> data;

	@Autowired
    private ExerciseDataOnDemand exerciseDataOnDemand;

	@Autowired
    private WeightWorkoutLogRecDataOnDemand weightWorkoutLogRecDataOnDemand;
	
	@Autowired
	private ExerciseLogRecRepository exerciseLogRecRepository;

	public ExerciseLogRec getNewTransientExerciseLogRec(int index) {
        ExerciseLogRec obj = new ExerciseLogRec();
        setExercise(obj, index);
        setWorkoutLogRec(obj, index);
        return obj;
    }

	public void setExercise(ExerciseLogRec obj, int index) {
        Exercise exercise = exerciseDataOnDemand.getRandomExercise();
        obj.setExercise(exercise);
    }

	public void setWorkoutLogRec(ExerciseLogRec obj, int index) {
        WeightWorkoutLogRec workoutLogRec = weightWorkoutLogRecDataOnDemand.getRandomWeightWorkoutLogRec();
        obj.setWorkoutLogRec(workoutLogRec);
    }

	public ExerciseLogRec getSpecificExerciseLogRec(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        ExerciseLogRec obj = data.get(index);
        Long id = obj.getId();
        return exerciseLogRecRepository.findOne(id);
    }

	public ExerciseLogRec getRandomExerciseLogRec() {
        init();
        ExerciseLogRec obj = data.get(rnd.nextInt(data.size()));
        Long id = obj.getId();
        return exerciseLogRecRepository.findOne(id);
    }

	public boolean modifyExerciseLogRec(ExerciseLogRec obj) {
        return false;
    }

	public void init() {
        int from = 0;
        int to = 10;
        data = exerciseLogRecRepository.findAll(new PageRequest(from, to)).getContent();
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'ExerciseLogRec' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<ExerciseLogRec>();
        for (int i = 0; i < 10; i++) {
            ExerciseLogRec obj = getNewTransientExerciseLogRec(i);
            try {
            	exerciseLogRecRepository.save(obj);
            } catch (ConstraintViolationException e) {
                StringBuilder msg = new StringBuilder();
                for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                    ConstraintViolation<?> cv = iter.next();
                    msg.append("[").append(cv.getConstraintDescriptor()).append(":").append(cv.getMessage()).append("=").append(cv.getInvalidValue()).append("]");
                }
                throw new RuntimeException(msg.toString(), e);
            }
            exerciseLogRecRepository.flush();
            data.add(obj);
        }
    }
}
