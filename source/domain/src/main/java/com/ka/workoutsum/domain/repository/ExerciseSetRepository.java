package com.ka.workoutsum.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ka.workoutsum.domain.CardioLogRec;
import com.ka.workoutsum.domain.CardioWorkout;
import com.ka.workoutsum.domain.ExerciseSet;

public interface ExerciseSetRepository extends JpaRepository<ExerciseSet, Long> {

}
