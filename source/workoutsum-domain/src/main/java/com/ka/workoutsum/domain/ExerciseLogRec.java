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

	@PersistenceContext
    transient EntityManager entityManager;

	public static final EntityManager entityManager() {
        EntityManager em = new ExerciseLogRec().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countExerciseLogRecs() {
        return entityManager().createQuery("SELECT COUNT(o) FROM ExerciseLogRec o", Long.class).getSingleResult();
    }

	public static List<ExerciseLogRec> findAllExerciseLogRecs() {
        return entityManager().createQuery("SELECT o FROM ExerciseLogRec o", ExerciseLogRec.class).getResultList();
    }

	public static ExerciseLogRec findExerciseLogRec(Long id) {
        if (id == null) return null;
        return entityManager().find(ExerciseLogRec.class, id);
    }

	public static List<ExerciseLogRec> findExerciseLogRecEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM ExerciseLogRec o", ExerciseLogRec.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            ExerciseLogRec attached = ExerciseLogRec.findExerciseLogRec(this.id);
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
    public ExerciseLogRec merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        ExerciseLogRec merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
}
