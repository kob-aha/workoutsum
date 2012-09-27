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

import com.ka.workoutsum.domain.repository.CardioWorkoutRepository;

@Component
@Configurable
public class CardioWorkoutDataOnDemand {

	private Random rnd = new SecureRandom();

	private List<CardioWorkout> data;
	
	@Autowired
	private CardioWorkoutRepository cardioWorkoutRepository;

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
        return cardioWorkoutRepository.findOne(id);
    }

	public CardioWorkout getRandomCardioWorkout() {
        init();
        CardioWorkout obj = data.get(rnd.nextInt(data.size()));
        Long id = obj.getId();
        return cardioWorkoutRepository.findOne(id);
    }

	public boolean modifyCardioWorkout(CardioWorkout obj) {
        return false;
    }

	public void init() {
        int from = 0;
        int to = 10;
        data = cardioWorkoutRepository.findAll(new PageRequest(from, to)).getContent();
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
                obj = cardioWorkoutRepository.save(obj);
            } catch (ConstraintViolationException e) {
                StringBuilder msg = new StringBuilder();
                for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                    ConstraintViolation<?> cv = iter.next();
                    msg.append("[").append(cv.getConstraintDescriptor()).append(":").append(cv.getMessage()).append("=").append(cv.getInvalidValue()).append("]");
                }
                throw new RuntimeException(msg.toString(), e);
            }
            cardioWorkoutRepository.flush();
            data.add(obj);
        }
    }
}
