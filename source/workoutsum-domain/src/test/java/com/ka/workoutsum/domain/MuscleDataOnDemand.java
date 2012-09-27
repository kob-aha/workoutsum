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

import com.ka.workoutsum.domain.repository.MuscleRepository;

@Configurable
@Component
public class MuscleDataOnDemand {

	private Random rnd = new SecureRandom();

	private List<Muscle> data;

	@Autowired
	private MuscleRepository muscleRepository;

	public Muscle getNewTransientMuscle(int index) {
        Muscle obj = new Muscle();
        setName(obj, index);
        return obj;
    }

	public void setName(Muscle obj, int index) {
        String name = "name_" + index;
        obj.setName(name);
    }

	public Muscle getSpecificMuscle(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        Muscle obj = data.get(index);
        Long id = obj.getId();
        return muscleRepository.findOne(id);
    }

	public Muscle getRandomMuscle() {
        init();
        Muscle obj = data.get(rnd.nextInt(data.size()));
        Long id = obj.getId();
        return muscleRepository.findOne(id);
    }

	public boolean modifyMuscle(Muscle obj) {
        return false;
    }

	public void init() {
        int from = 0;
        int to = 10;
        data = muscleRepository.findAll(new PageRequest(from, to)).getContent();
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'Muscle' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<Muscle>();
        for (int i = 0; i < 10; i++) {
            Muscle obj = getNewTransientMuscle(i);
            try {
            	muscleRepository.save(obj);
            } catch (ConstraintViolationException e) {
                StringBuilder msg = new StringBuilder();
                for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                    ConstraintViolation<?> cv = iter.next();
                    msg.append("[").append(cv.getConstraintDescriptor()).append(":").append(cv.getMessage()).append("=").append(cv.getInvalidValue()).append("]");
                }
                throw new RuntimeException(msg.toString(), e);
            }
            muscleRepository.flush();
            data.add(obj);
        }
    }
}
