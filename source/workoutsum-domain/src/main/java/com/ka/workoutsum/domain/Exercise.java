package com.ka.workoutsum.domain;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(table = "T_EXERCISE")
public class Exercise {

    @NotNull
    private String name;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "MUSCLE_ID")
    private Muscle targetedMuscle;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "exercise")
    private Set<ExerciseLogRec> weightWorkoutLogRecs = new HashSet<ExerciseLogRec>();
}
