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

	@PersistenceContext
    transient EntityManager entityManager;

	public static final EntityManager entityManager() {
        EntityManager em = new WeightWorkoutLogRec().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countWeightWorkoutLogRecs() {
        return entityManager().createQuery("SELECT COUNT(o) FROM WeightWorkoutLogRec o", Long.class).getSingleResult();
    }

	public static List<WeightWorkoutLogRec> findAllWeightWorkoutLogRecs() {
        return entityManager().createQuery("SELECT o FROM WeightWorkoutLogRec o", WeightWorkoutLogRec.class).getResultList();
    }

	public static WeightWorkoutLogRec findWeightWorkoutLogRec(Long id) {
        if (id == null) return null;
        return entityManager().find(WeightWorkoutLogRec.class, id);
    }

	public static List<WeightWorkoutLogRec> findWeightWorkoutLogRecEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM WeightWorkoutLogRec o", WeightWorkoutLogRec.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            WeightWorkoutLogRec attached = WeightWorkoutLogRec.findWeightWorkoutLogRec(this.id);
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
    public WeightWorkoutLogRec merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        WeightWorkoutLogRec merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
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
