package com.ka.workoutsum.domain;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(table = "T_EXERCISE_SET")
public class ExerciseSet {

    @NotNull
    @Column(name = "REPS_NUM")
    private Integer repsNum;

    @Column(name = "PLATES_NUM")
    private Integer platesNum;

    private BigDecimal weight;

    private String remarks;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "EXERCISE_LOG_REC_ID")
    private ExerciseLogRec exerciseLogRec;

    @Max(99L)
    private Integer test;

    @NotNull
    @Column(name = "SETS_NUM")
    @Max(99L)
    @Digits(integer = 2, fraction = 0)
    private Integer setsNum;
}
