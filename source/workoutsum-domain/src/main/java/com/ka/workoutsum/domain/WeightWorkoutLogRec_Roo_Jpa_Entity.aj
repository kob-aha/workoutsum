// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.ka.workoutsum.domain;

import com.ka.workoutsum.domain.WeightWorkoutLogRec;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

privileged aspect WeightWorkoutLogRec_Roo_Jpa_Entity {
    
    declare @type: WeightWorkoutLogRec: @Entity;
    
    declare @type: WeightWorkoutLogRec: @Table(name = "T_WEIGHT_WORKOUT_LOG_REC");
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long WeightWorkoutLogRec.id;
    
    @Version
    @Column(name = "version")
    private Integer WeightWorkoutLogRec.version;
    
    public Long WeightWorkoutLogRec.getId() {
        return this.id;
    }
    
    public void WeightWorkoutLogRec.setId(Long id) {
        this.id = id;
    }
    
    public Integer WeightWorkoutLogRec.getVersion() {
        return this.version;
    }
    
    public void WeightWorkoutLogRec.setVersion(Integer version) {
        this.version = version;
    }
    
}
