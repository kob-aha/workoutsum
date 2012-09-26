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

@Configurable
@Entity
@Table(name = "T_WORKOUT_EXERCISE")
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

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	public String getRemarks() {
        return this.remarks;
    }

	public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

	public Integer getSetsNum() {
        return this.setsNum;
    }

	public void setSetsNum(Integer setsNum) {
        this.setsNum = setsNum;
    }

	public Integer getRepsNum() {
        return this.repsNum;
    }

	public void setRepsNum(Integer repsNum) {
        this.repsNum = repsNum;
    }

	public Exercise getExercise() {
        return this.exercise;
    }

	public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

	public WeightWorkout getWorkout() {
        return this.workout;
    }

	public void setWorkout(WeightWorkout workout) {
        this.workout = workout;
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

	@PersistenceContext
    transient EntityManager entityManager;

	public static final EntityManager entityManager() {
        EntityManager em = new WorkoutExercise().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countWorkoutExercises() {
        return entityManager().createQuery("SELECT COUNT(o) FROM WorkoutExercise o", Long.class).getSingleResult();
    }

	public static List<WorkoutExercise> findAllWorkoutExercises() {
        return entityManager().createQuery("SELECT o FROM WorkoutExercise o", WorkoutExercise.class).getResultList();
    }

	public static WorkoutExercise findWorkoutExercise(Long id) {
        if (id == null) return null;
        return entityManager().find(WorkoutExercise.class, id);
    }

	public static List<WorkoutExercise> findWorkoutExerciseEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM WorkoutExercise o", WorkoutExercise.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	@Transactional
    public void persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }

	@Transactional
    public void remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            WorkoutExercise attached = WorkoutExercise.findWorkoutExercise(this.id);
            this.entityManager.remove(attached);
        }
    }

	@Transactional
    public void flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }

	@Transactional
    public void clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }

	@Transactional
    public WorkoutExercise merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        WorkoutExercise merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
}
