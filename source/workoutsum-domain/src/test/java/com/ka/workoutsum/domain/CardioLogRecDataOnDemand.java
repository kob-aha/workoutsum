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
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.ka.workoutsum.domain.repository.CardioLogRecRepository;

@Configurable
@Component
public class CardioLogRecDataOnDemand {

	private Random rnd = new SecureRandom();

	private List<CardioLogRec> data;
	
	@Autowired
	private CardioLogRecRepository cardioLogRecRepository;

	@Autowired
    private CardioWorkoutDataOnDemand cardioWorkoutDataOnDemand;

	public CardioLogRec getNewTransientCardioLogRec(int index) {
        CardioLogRec obj = new CardioLogRec();
        setAvgHeartRate(obj, index);
        setAvgSpeed(obj, index);
        setIndoor(obj, index);
        setKm(obj, index);
        setWorkout(obj, index);
        return obj;
    }

	public void setAvgHeartRate(CardioLogRec obj, int index) {
        Float avgHeartRate = new Integer(index).floatValue();
        if (avgHeartRate > 99.99F) {
            avgHeartRate = 99.99F;
        }
        obj.setAvgHeartRate(avgHeartRate);
    }

	public void setAvgSpeed(CardioLogRec obj, int index) {
        Float avgSpeed = new Integer(index).floatValue();
        if (avgSpeed > 99.99F) {
            avgSpeed = 99.99F;
        }
        obj.setAvgSpeed(avgSpeed);
    }

	public void setIndoor(CardioLogRec obj, int index) {
        Boolean indoor = Boolean.TRUE;
        obj.setIndoor(indoor);
    }

	public void setKm(CardioLogRec obj, int index) {
        Float km = new Integer(index).floatValue();
        if (km > 99.99F) {
            km = 99.99F;
        }
        obj.setKm(km);
    }

	public void setWorkout(CardioLogRec obj, int index) {
        CardioWorkout workout = cardioWorkoutDataOnDemand.getRandomCardioWorkout();
        obj.setWorkout(workout);
    }

	public CardioLogRec getSpecificCardioLogRec(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        CardioLogRec obj = data.get(index);
        Long id = obj.getId();
//        return CardioLogRec.findCardioLogRec(id);  
        return cardioLogRecRepository.findOne(id);
    }

	public CardioLogRec getRandomCardioLogRec() {
        init();
        CardioLogRec obj = data.get(rnd.nextInt(data.size()));
        Long id = obj.getId();
//        return CardioLogRec.findCardioLogRec(id);
        return cardioLogRecRepository.findOne(id);
    }

	public boolean modifyCardioLogRec(CardioLogRec obj) {
        return false;
    }

	public void init() {
        final int from = 0;
        final int to = 10;
//        data = CardioLogRec.findCardioLogRecEntries(from, to);
        data = cardioLogRecRepository.findAll(new Pageable() {
			
			@Override
			public Sort getSort() {
				return null;
			}
			
			@Override
			public int getPageSize() {
				return to;
			}
			
			@Override
			public int getPageNumber() {
				return 1;
			}
			
			@Override
			public int getOffset() {
				return from;
			}
		}).getContent();
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'CardioLogRec' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<CardioLogRec>();
        for (int i = 0; i < 10; i++) {
            CardioLogRec obj = getNewTransientCardioLogRec(i);
            try {
//                obj.persist();
                cardioLogRecRepository.save(obj);
            } catch (ConstraintViolationException e) {
                StringBuilder msg = new StringBuilder();
                for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                    ConstraintViolation<?> cv = iter.next();
                    msg.append("[").append(cv.getConstraintDescriptor()).append(":").append(cv.getMessage()).append("=").append(cv.getInvalidValue()).append("]");
                }
                throw new RuntimeException(msg.toString(), e);
            }
//            obj.flush();
            cardioLogRecRepository.flush();
            data.add(obj);
        }
    }
}
