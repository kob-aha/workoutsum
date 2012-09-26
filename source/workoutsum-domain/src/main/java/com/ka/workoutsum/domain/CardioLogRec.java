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
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;

@Configurable
@Entity
@Table(name = "T_CARDIO_LOG_REC")
public class CardioLogRec {

    @NotNull
    @ManyToOne
    @JoinColumn(name = "WORKOUT_ID")
    private CardioWorkout workout;

    @Column(name = "AVG_HEART_RATE")
    @Digits(integer = 2, fraction = 2)
    private Float avgHeartRate;

    @Column(name = "AVG_SPEED")
    @Digits(integer = 2, fraction = 2)
    private Float avgSpeed;

    private Boolean indoor;

    @Digits(integer = 2, fraction = 2)
    private Float km;

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
        EntityManager em = new CardioLogRec().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countCardioLogRecs() {
        return entityManager().createQuery("SELECT COUNT(o) FROM CardioLogRec o", Long.class).getSingleResult();
    }

	public static List<CardioLogRec> findAllCardioLogRecs() {
        return entityManager().createQuery("SELECT o FROM CardioLogRec o", CardioLogRec.class).getResultList();
    }

	public static CardioLogRec findCardioLogRec(Long id) {
        if (id == null) return null;
        return entityManager().find(CardioLogRec.class, id);
    }

	public static List<CardioLogRec> findCardioLogRecEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM CardioLogRec o", CardioLogRec.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            CardioLogRec attached = CardioLogRec.findCardioLogRec(this.id);
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
    public CardioLogRec merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        CardioLogRec merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

	public CardioWorkout getWorkout() {
        return this.workout;
    }

	public void setWorkout(CardioWorkout workout) {
        this.workout = workout;
    }

	public Float getAvgHeartRate() {
        return this.avgHeartRate;
    }

	public void setAvgHeartRate(Float avgHeartRate) {
        this.avgHeartRate = avgHeartRate;
    }

	public Float getAvgSpeed() {
        return this.avgSpeed;
    }

	public void setAvgSpeed(Float avgSpeed) {
        this.avgSpeed = avgSpeed;
    }

	public Boolean getIndoor() {
        return this.indoor;
    }

	public void setIndoor(Boolean indoor) {
        this.indoor = indoor;
    }

	public Float getKm() {
        return this.km;
    }

	public void setKm(Float km) {
        this.km = km;
    }
}
