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
public class WeightWorkoutLogRecDataOnDemand {

	private Random rnd = new SecureRandom();

	private List<WeightWorkoutLogRec> data;

	@Autowired
    private WeightWorkoutDataOnDemand weightWorkoutDataOnDemand;

	public WeightWorkoutLogRec getNewTransientWeightWorkoutLogRec(int index) {
        WeightWorkoutLogRec obj = new WeightWorkoutLogRec();
        setWorkout(obj, index);
        setWorkoutDuration(obj, index);
        setWorkoutTime(obj, index);
        return obj;
    }

	public void setWorkout(WeightWorkoutLogRec obj, int index) {
        WeightWorkout workout = weightWorkoutDataOnDemand.getRandomWeightWorkout();
        obj.setWorkout(workout);
    }

	public void setWorkoutDuration(WeightWorkoutLogRec obj, int index) {
        String workoutDuration = "workoutDuration_" + index;
        obj.setWorkoutDuration(workoutDuration);
    }

	public void setWorkoutTime(WeightWorkoutLogRec obj, int index) {
        Long workoutTime = new Integer(index).longValue();
        obj.setWorkoutTime(workoutTime);
    }

	public WeightWorkoutLogRec getSpecificWeightWorkoutLogRec(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        WeightWorkoutLogRec obj = data.get(index);
        Long id = obj.getId();
        return WeightWorkoutLogRec.findWeightWorkoutLogRec(id);
    }

	public WeightWorkoutLogRec getRandomWeightWorkoutLogRec() {
        init();
        WeightWorkoutLogRec obj = data.get(rnd.nextInt(data.size()));
        Long id = obj.getId();
        return WeightWorkoutLogRec.findWeightWorkoutLogRec(id);
    }

	public boolean modifyWeightWorkoutLogRec(WeightWorkoutLogRec obj) {
        return false;
    }

	public void init() {
        int from = 0;
        int to = 10;
        data = WeightWorkoutLogRec.findWeightWorkoutLogRecEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'WeightWorkoutLogRec' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<WeightWorkoutLogRec>();
        for (int i = 0; i < 10; i++) {
            WeightWorkoutLogRec obj = getNewTransientWeightWorkoutLogRec(i);
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
