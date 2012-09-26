package com.ka.workoutsum.domain;

import java.math.BigDecimal;
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

@Configurable
@Component
public class ExerciseSetDataOnDemand {

	private Random rnd = new SecureRandom();

	private List<ExerciseSet> data;

	@Autowired
    private ExerciseLogRecDataOnDemand exerciseLogRecDataOnDemand;

	public ExerciseSet getNewTransientExerciseSet(int index) {
        ExerciseSet obj = new ExerciseSet();
        setExerciseLogRec(obj, index);
        setPlatesNum(obj, index);
        setRemarks(obj, index);
        setRepsNum(obj, index);
        setSetsNum(obj, index);
        setTest(obj, index);
        setWeight(obj, index);
        return obj;
    }

	public void setExerciseLogRec(ExerciseSet obj, int index) {
        ExerciseLogRec exerciseLogRec = exerciseLogRecDataOnDemand.getRandomExerciseLogRec();
        obj.setExerciseLogRec(exerciseLogRec);
    }

	public void setPlatesNum(ExerciseSet obj, int index) {
        Integer platesNum = new Integer(index);
        obj.setPlatesNum(platesNum);
    }

	public void setRemarks(ExerciseSet obj, int index) {
        String remarks = "remarks_" + index;
        obj.setRemarks(remarks);
    }

	public void setRepsNum(ExerciseSet obj, int index) {
        Integer repsNum = new Integer(index);
        obj.setRepsNum(repsNum);
    }

	public void setSetsNum(ExerciseSet obj, int index) {
        Integer setsNum = new Integer(index);
        if (setsNum > 99) {
            setsNum = 99;
        }
        obj.setSetsNum(setsNum);
    }

	public void setTest(ExerciseSet obj, int index) {
        Integer test = new Integer(index);
        if (test > 99) {
            test = 99;
        }
        obj.setTest(test);
    }

	public void setWeight(ExerciseSet obj, int index) {
        BigDecimal weight = BigDecimal.valueOf(index);
        obj.setWeight(weight);
    }

	public ExerciseSet getSpecificExerciseSet(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        ExerciseSet obj = data.get(index);
        Long id = obj.getId();
        return ExerciseSet.findExerciseSet(id);
    }

	public ExerciseSet getRandomExerciseSet() {
        init();
        ExerciseSet obj = data.get(rnd.nextInt(data.size()));
        Long id = obj.getId();
        return ExerciseSet.findExerciseSet(id);
    }

	public boolean modifyExerciseSet(ExerciseSet obj) {
        return false;
    }

	public void init() {
        int from = 0;
        int to = 10;
        data = ExerciseSet.findExerciseSetEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'ExerciseSet' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<ExerciseSet>();
        for (int i = 0; i < 10; i++) {
            ExerciseSet obj = getNewTransientExerciseSet(i);
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
