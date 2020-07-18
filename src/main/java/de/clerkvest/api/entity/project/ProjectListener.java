package de.clerkvest.api.entity.project;

import org.springframework.stereotype.Component;

import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

@Component
public class ProjectListener {

    @PrePersist
    void preCreate(Project project) {

    }

    @PreUpdate
    void preUpdate(Project project) {

    }

    @PreRemove
    void preRemove(Project project) {

    }

}
