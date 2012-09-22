package com.ka.workoutsum.domain;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(table = "T_WEIGHT_WORKOUT")
public class WeightWorkout {

    private String goal;

    @NotNull
    @Column(name = "CREATE_TIME")
    private Long createTime;
}
