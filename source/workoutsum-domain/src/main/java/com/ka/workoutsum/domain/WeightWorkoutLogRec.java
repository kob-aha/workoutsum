package com.ka.workoutsum.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;

@Configurable
@Entity
@Table(name = "T_WEIGHT_WORKOUT_LOG_REC")
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

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
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

	public WeightWorkout getWorkout() {
        return this.workout;
    }

	public void setWorkout(WeightWorkout workout) {
        this.workout = workout;
    }

	public Long getWorkoutTime() {
        return this.workoutTime;
    }

	public void setWorkoutTime(Long workoutTime) {
        this.workoutTime = workoutTime;
    }

	public String getWorkoutDuration() {
        return this.workoutDuration;
    }

	public void setWorkoutDuration(String workoutDuration) {
        this.workoutDuration = workoutDuration;
    }

	public Set<ExerciseLogRec> getExerciseLogRecs() {
        return this.exerciseLogRecs;
    }

	public void setExerciseLogRecs(Set<ExerciseLogRec> exerciseLogRecs) {
        this.exerciseLogRecs = exerciseLogRecs;
    }
}
