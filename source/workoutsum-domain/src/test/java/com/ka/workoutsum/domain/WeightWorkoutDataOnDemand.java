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

import com.ka.workoutsum.domain.repository.WeightWorkoutRepository;

@Component
@Configurable
public class WeightWorkoutDataOnDemand {

	private Random rnd = new SecureRandom();

	private List<WeightWorkout> data;

	@Autowired
	private WeightWorkoutRepository weightWorkoutRepository;

	public WeightWorkout getNewTransientWeightWorkout(int index) {
        WeightWorkout obj = new WeightWorkout();
        setCreateTime(obj, index);
        setGoal(obj, index);
        return obj;
    }

	public void setCreateTime(WeightWorkout obj, int index) {
        Long createTime = new Integer(index).longValue();
        obj.setCreateTime(createTime);
    }

	public void setGoal(WeightWorkout obj, int index) {
        String goal = "goal_" + index;
        obj.setGoal(goal);
    }

	public WeightWorkout getSpecificWeightWorkout(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        WeightWorkout obj = data.get(index);
        Long id = obj.getId();
        return weightWorkoutRepository.findOne(id);
    }

	public WeightWorkout getRandomWeightWorkout() {
        init();
        WeightWorkout obj = data.get(rnd.nextInt(data.size()));
        Long id = obj.getId();
        return weightWorkoutRepository.findOne(id);
    }

	public boolean modifyWeightWorkout(WeightWorkout obj) {
        return false;
    }

	public void init() {
        int from = 0;
        int to = 10;
        data = weightWorkoutRepository.findAll(new PageRequest(from, to)).getContent();
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'WeightWorkout' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<WeightWorkout>();
        for (int i = 0; i < 10; i++) {
            WeightWorkout obj = getNewTransientWeightWorkout(i);
            try {
            	weightWorkoutRepository.save(obj);
            } catch (ConstraintViolationException e) {
                StringBuilder msg = new StringBuilder();
                for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                    ConstraintViolation<?> cv = iter.next();
                    msg.append("[").append(cv.getConstraintDescriptor()).append(":").append(cv.getMessage()).append("=").append(cv.getInvalidValue()).append("]");
                }
                throw new RuntimeException(msg.toString(), e);
            }
            weightWorkoutRepository.flush();
            data.add(obj);
        }
    }
}
