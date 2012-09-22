package com.ka.workoutsum.domain;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(table = "T_EXERCISE_LOG_REC")
public class ExerciseLogRec {

    @NotNull
    @ManyToOne
    @JoinColumn(name = "WORKOUT_LOG_REC_ID")
    private WeightWorkoutLogRec workoutLogRec;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "EXERCISE_ID")
    private Exercise exercise;
}
