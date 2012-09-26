package com.ka.workoutsum.domain;

import java.math.BigDecimal;
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
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;

@Entity
@Table(name = "T_EXERCISE_SET")
@Configurable
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

	public Integer getRepsNum() {
        return this.repsNum;
    }

	public void setRepsNum(Integer repsNum) {
        this.repsNum = repsNum;
    }

	public Integer getPlatesNum() {
        return this.platesNum;
    }

	public void setPlatesNum(Integer platesNum) {
        this.platesNum = platesNum;
    }

	public BigDecimal getWeight() {
        return this.weight;
    }

	public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

	public String getRemarks() {
        return this.remarks;
    }

	public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

	public ExerciseLogRec getExerciseLogRec() {
        return this.exerciseLogRec;
    }

	public void setExerciseLogRec(ExerciseLogRec exerciseLogRec) {
        this.exerciseLogRec = exerciseLogRec;
    }

	public Integer getTest() {
        return this.test;
    }

	public void setTest(Integer test) {
        this.test = test;
    }

	public Integer getSetsNum() {
        return this.setsNum;
    }

	public void setSetsNum(Integer setsNum) {
        this.setsNum = setsNum;
    }

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
        EntityManager em = new ExerciseSet().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countExerciseSets() {
        return entityManager().createQuery("SELECT COUNT(o) FROM ExerciseSet o", Long.class).getSingleResult();
    }

	public static List<ExerciseSet> findAllExerciseSets() {
        return entityManager().createQuery("SELECT o FROM ExerciseSet o", ExerciseSet.class).getResultList();
    }

	public static ExerciseSet findExerciseSet(Long id) {
        if (id == null) return null;
        return entityManager().find(ExerciseSet.class, id);
    }

	public static List<ExerciseSet> findExerciseSetEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM ExerciseSet o", ExerciseSet.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            ExerciseSet attached = ExerciseSet.findExerciseSet(this.id);
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
    public ExerciseSet merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        ExerciseSet merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
}
