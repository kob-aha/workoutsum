package com.ka.workoutsum.domain;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(table = "T_CARDIO_LOG_REC")
public class CardioLogRec {

    @NotNull
    @ManyToOne
    @JoinColumn(name = "WORKOUT_ID")
    private CardioWorkout workout;

    @Column(name = "AVG_HEART_RATE")
    @Digits(integer = 2, fraction = 2)
    private Float avgHeartRate;

    @Column(name = "AVG_SPEED")
    @Digits(integer = 2, fraction = 2)
    private Float avgSpeed;

    private Boolean indoor;

    @Digits(integer = 2, fraction = 2)
    private Float km;
}
