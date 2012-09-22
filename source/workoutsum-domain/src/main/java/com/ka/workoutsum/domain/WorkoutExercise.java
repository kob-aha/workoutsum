package com.ka.workoutsum.domain;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(table = "T_WORKOUT_EXERCISE")
public class WorkoutExercise {

    private String remarks;

    @Column(name = "SETS_NUM")
    private Integer setsNum;

    @NotNull
    @Column(name = "REPS_NUM")
    private Integer repsNum;

    @ManyToOne
    @JoinColumn(name = "EXERCISE_ID")
    private Exercise exercise;

    @ManyToOne
    @JoinColumn(name = "WEIGHT_WORKOUT_ID")
    private WeightWorkout workout;
}
