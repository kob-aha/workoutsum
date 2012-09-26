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
import javax.validation.constraints.Size;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;

@Entity
@Table(name = "T_CARDIO_WORKOUT")
@Configurable
public class CardioWorkout {

    @Size(max = 100)
    private String type;

	@PersistenceContext
    transient EntityManager entityManager;

	public static final EntityManager entityManager() {
        EntityManager em = new CardioWorkout().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countCardioWorkouts() {
        return entityManager().createQuery("SELECT COUNT(o) FROM CardioWorkout o", Long.class).getSingleResult();
    }

	public static List<CardioWorkout> findAllCardioWorkouts() {
        return entityManager().createQuery("SELECT o FROM CardioWorkout o", CardioWorkout.class).getResultList();
    }

	public static CardioWorkout findCardioWorkout(Long id) {
        if (id == null) return null;
        return entityManager().find(CardioWorkout.class, id);
    }

	public static List<CardioWorkout> findCardioWorkoutEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM CardioWorkout o", CardioWorkout.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            CardioWorkout attached = CardioWorkout.findCardioWorkout(this.id);
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
    public CardioWorkout merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        CardioWorkout merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
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

	public String getType() {
        return this.type;
    }

	public void setType(String type) {
        this.type = type;
    }

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
