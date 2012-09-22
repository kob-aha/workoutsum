// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.ka.workoutsum.domain;

import com.ka.workoutsum.domain.ExerciseSet;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

privileged aspect ExerciseSet_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext
    transient EntityManager ExerciseSet.entityManager;
    
    public static final EntityManager ExerciseSet.entityManager() {
        EntityManager em = new ExerciseSet().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long ExerciseSet.countExerciseSets() {
        return entityManager().createQuery("SELECT COUNT(o) FROM ExerciseSet o", Long.class).getSingleResult();
    }
    
    public static List<ExerciseSet> ExerciseSet.findAllExerciseSets() {
        return entityManager().createQuery("SELECT o FROM ExerciseSet o", ExerciseSet.class).getResultList();
    }
    
    public static ExerciseSet ExerciseSet.findExerciseSet(Long id) {
        if (id == null) return null;
        return entityManager().find(ExerciseSet.class, id);
    }
    
    public static List<ExerciseSet> ExerciseSet.findExerciseSetEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM ExerciseSet o", ExerciseSet.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void ExerciseSet.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void ExerciseSet.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            ExerciseSet attached = ExerciseSet.findExerciseSet(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void ExerciseSet.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void ExerciseSet.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public ExerciseSet ExerciseSet.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        ExerciseSet merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}
