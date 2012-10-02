package com.ka.workoutsum.service;

import com.ka.workoutsum.domain.WeightWorkout;
import com.ka.workoutsum.domain.repository.WeightWorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkoutsServiceImpl implements WorkoutsService{

    @Autowired
    private WeightWorkoutRepository weightWorkoutRepository;

    @Override
    public WeightWorkout getById(Long id) {
        return weightWorkoutRepository.findOne(id);
    }

    @Override
    public WeightWorkout create(String goal) {

        WeightWorkout weightWorkout = new WeightWorkout();
        weightWorkout.setGoal(goal);
        weightWorkout.setCreateTime(System.currentTimeMillis());

        return weightWorkoutRepository.save(weightWorkout);
    }
}
