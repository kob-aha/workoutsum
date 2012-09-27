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
import javax.validation.constraints.NotNull;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;

@Configurable
@Entity
@Table(name = "T_EXERCISE")
public class Exercise {

    @NotNull
    private String name;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "MUSCLE_ID")
    private Muscle targetedMuscle;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "exercise")
    private Set<ExerciseLogRec> weightWorkoutLogRecs = new HashSet<ExerciseLogRec>();

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	public String getName() {
        return this.name;
    }

	public void setName(String name) {
        this.name = name;
    }

	public Muscle getTargetedMuscle() {
        return this.targetedMuscle;
    }

	public void setTargetedMuscle(Muscle targetedMuscle) {
        this.targetedMuscle = targetedMuscle;
    }

	public Set<ExerciseLogRec> getWeightWorkoutLogRecs() {
        return this.weightWorkoutLogRecs;
    }

	public void setWeightWorkoutLogRecs(Set<ExerciseLogRec> weightWorkoutLogRecs) {
        this.weightWorkoutLogRecs = weightWorkoutLogRecs;
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
}
