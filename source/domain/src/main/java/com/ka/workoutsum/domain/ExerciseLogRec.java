package com.ka.workoutsum.domain;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;

@Entity
@Table(name = "T_EXERCISE_LOG_REC")
@Configurable
public class ExerciseLogRec {

    @NotNull
    @ManyToOne
    @JoinColumn(name = "WORKOUT_LOG_REC_ID")
    private WeightWorkoutLogRec workoutLogRec;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "EXERCISE_ID")
    private Exercise exercise;

	public WeightWorkoutLogRec getWorkoutLogRec() {
        return this.workoutLogRec;
    }

	public void setWorkoutLogRec(WeightWorkoutLogRec workoutLogRec) {
        this.workoutLogRec = workoutLogRec;
    }

	public Exercise getExercise() {
        return this.exercise;
    }

	public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

	@Version
    @Column(name = "version")
    private Integer version;

	public Long getId() {
        return this.id;
    }

	public void setId(Long id) {
        this.id = id;
    }

	public Integer getVersion() {
        return this.version;
    }

	public void setVersion(Integer version) {
        this.version = version;
    }

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
