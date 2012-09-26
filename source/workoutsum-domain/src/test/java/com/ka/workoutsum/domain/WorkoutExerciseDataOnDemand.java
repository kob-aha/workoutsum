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
import org.springframework.stereotype.Component;

@Component
@Configurable
public class WorkoutExerciseDataOnDemand {

	private Random rnd = new SecureRandom();

	private List<WorkoutExercise> data;

	@Autowired
    private ExerciseDataOnDemand exerciseDataOnDemand;

	@Autowired
    private WeightWorkoutDataOnDemand weightWorkoutDataOnDemand;

	public WorkoutExercise getNewTransientWorkoutExercise(int index) {
        WorkoutExercise obj = new WorkoutExercise();
        setRemarks(obj, index);
        setRepsNum(obj, index);
        setSetsNum(obj, index);
        return obj;
    }

	public void setRemarks(WorkoutExercise obj, int index) {
        String remarks = "remarks_" + index;
        obj.setRemarks(remarks);
    }

	public void setRepsNum(WorkoutExercise obj, int index) {
        Integer repsNum = new Integer(index);
        obj.setRepsNum(repsNum);
    }

	public void setSetsNum(WorkoutExercise obj, int index) {
        Integer setsNum = new Integer(index);
        obj.setSetsNum(setsNum);
    }

	public WorkoutExercise getSpecificWorkoutExercise(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        WorkoutExercise obj = data.get(index);
        Long id = obj.getId();
        return WorkoutExercise.findWorkoutExercise(id);
    }

	public WorkoutExercise getRandomWorkoutExercise() {
        init();
        WorkoutExercise obj = data.get(rnd.nextInt(data.size()));
        Long id = obj.getId();
        return WorkoutExercise.findWorkoutExercise(id);
    }

	public boolean modifyWorkoutExercise(WorkoutExercise obj) {
        return false;
    }

	public void init() {
        int from = 0;
        int to = 10;
        data = WorkoutExercise.findWorkoutExerciseEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'WorkoutExercise' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<WorkoutExercise>();
        for (int i = 0; i < 10; i++) {
            WorkoutExercise obj = getNewTransientWorkoutExercise(i);
            try {
                obj.persist();
            } catch (ConstraintViolationException e) {
                StringBuilder msg = new StringBuilder();
                for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                    ConstraintViolation<?> cv = iter.next();
                    msg.append("[").append(cv.getConstraintDescriptor()).append(":").append(cv.getMessage()).append("=").append(cv.getInvalidValue()).append("]");
                }
                throw new RuntimeException(msg.toString(), e);
            }
            obj.flush();
            data.add(obj);
        }
    }
}
