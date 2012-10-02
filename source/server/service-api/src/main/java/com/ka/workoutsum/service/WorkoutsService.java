package com.ka.workoutsum.service;

import com.ka.workoutsum.domain.WeightWorkout;

public interface WorkoutsService {

    WeightWorkout getById(Long id);

    WeightWorkout create(String goal);
}
