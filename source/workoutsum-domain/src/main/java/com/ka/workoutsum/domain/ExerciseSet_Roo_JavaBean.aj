// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.ka.workoutsum.domain;

import com.ka.workoutsum.domain.ExerciseLogRec;
import com.ka.workoutsum.domain.ExerciseSet;
import java.math.BigDecimal;

privileged aspect ExerciseSet_Roo_JavaBean {
    
    public Integer ExerciseSet.getRepsNum() {
        return this.repsNum;
    }
    
    public void ExerciseSet.setRepsNum(Integer repsNum) {
        this.repsNum = repsNum;
    }
    
    public Integer ExerciseSet.getPlatesNum() {
        return this.platesNum;
    }
    
    public void ExerciseSet.setPlatesNum(Integer platesNum) {
        this.platesNum = platesNum;
    }
    
    public BigDecimal ExerciseSet.getWeight() {
        return this.weight;
    }
    
    public void ExerciseSet.setWeight(BigDecimal weight) {
        this.weight = weight;
    }
    
    public String ExerciseSet.getRemarks() {
        return this.remarks;
    }
    
    public void ExerciseSet.setRemarks(String remarks) {
        this.remarks = remarks;
    }
    
    public ExerciseLogRec ExerciseSet.getExerciseLogRec() {
        return this.exerciseLogRec;
    }
    
    public void ExerciseSet.setExerciseLogRec(ExerciseLogRec exerciseLogRec) {
        this.exerciseLogRec = exerciseLogRec;
    }
    
    public Integer ExerciseSet.getTest() {
        return this.test;
    }
    
    public void ExerciseSet.setTest(Integer test) {
        this.test = test;
    }
    
    public Integer ExerciseSet.getSetsNum() {
        return this.setsNum;
    }
    
    public void ExerciseSet.setSetsNum(Integer setsNum) {
        this.setsNum = setsNum;
    }
    
}
