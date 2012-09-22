package com.ka.workoutsum.domain;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(table = "T_WEIGHT_WORKOUT_LOG_REC")
public class WeightWorkoutLogRec {

    @NotNull
    @ManyToOne
    @JoinColumn(name = "WORKOUT_ID")
    private WeightWorkout workout;

    @Column(name = "WORKOUT_TIME")
    @Digits(integer = 20, fraction = 0)
    private Long workoutTime;

    private String workoutDuration;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "workoutLogRec")
    private Set<ExerciseLogRec> exerciseLogRecs = new HashSet<ExerciseLogRec>();
}
