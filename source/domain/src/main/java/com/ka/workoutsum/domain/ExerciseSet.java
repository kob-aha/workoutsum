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
}
