package com.ka.workoutsum.domain;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;

@Entity
@Table(name = "T_WEIGHT_WORKOUT")
@Configurable
public class WeightWorkout {

    private String goal;

    @NotNull
    @Column(name = "CREATE_TIME")
    private Long createTime;

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
        EntityManager em = new WeightWorkout().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countWeightWorkouts() {
        return entityManager().createQuery("SELECT COUNT(o) FROM WeightWorkout o", Long.class).getSingleResult();
    }

	public static List<WeightWorkout> findAllWeightWorkouts() {
        return entityManager().createQuery("SELECT o FROM WeightWorkout o", WeightWorkout.class).getResultList();
    }

	public static WeightWorkout findWeightWorkout(Long id) {
        if (id == null) return null;
        return entityManager().find(WeightWorkout.class, id);
    }

	public static List<WeightWorkout> findWeightWorkoutEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM WeightWorkout o", WeightWorkout.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            WeightWorkout attached = WeightWorkout.findWeightWorkout(this.id);
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
    public WeightWorkout merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        WeightWorkout merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	public String getGoal() {
        return this.goal;
    }

	public void setGoal(String goal) {
        this.goal = goal;
    }

	public Long getCreateTime() {
        return this.createTime;
    }

	public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}
