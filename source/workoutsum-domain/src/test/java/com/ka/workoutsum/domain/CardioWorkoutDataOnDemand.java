package com.ka.workoutsum.domain;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

@Component
@Configurable
public class CardioWorkoutDataOnDemand {

	private Random rnd = new SecureRandom();

	private List<CardioWorkout> data;

	public CardioWorkout getNewTransientCardioWorkout(int index) {
        CardioWorkout obj = new CardioWorkout();
        setType(obj, index);
        return obj;
    }

	public void setType(CardioWorkout obj, int index) {
        String type = "type_" + index;
        if (type.length() > 100) {
            type = type.substring(0, 100);
        }
        obj.setType(type);
    }

	public CardioWorkout getSpecificCardioWorkout(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        CardioWorkout obj = data.get(index);
        Long id = obj.getId();
        return CardioWorkout.findCardioWorkout(id);
    }

	public CardioWorkout getRandomCardioWorkout() {
        init();
        CardioWorkout obj = data.get(rnd.nextInt(data.size()));
        Long id = obj.getId();
        return CardioWorkout.findCardioWorkout(id);
    }

	public boolean modifyCardioWorkout(CardioWorkout obj) {
        return false;
    }

	public void init() {
        int from = 0;
        int to = 10;
        data = CardioWorkout.findCardioWorkoutEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'CardioWorkout' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<CardioWorkout>();
        for (int i = 0; i < 10; i++) {
            CardioWorkout obj = getNewTransientCardioWorkout(i);
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
