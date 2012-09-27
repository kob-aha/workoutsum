package com.ka.workoutsum.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ka.workoutsum.domain.CardioLogRec;
import com.ka.workoutsum.domain.CardioWorkout;

public interface CardioWorkoutRepository extends JpaRepository<CardioWorkout, Long> {

}
