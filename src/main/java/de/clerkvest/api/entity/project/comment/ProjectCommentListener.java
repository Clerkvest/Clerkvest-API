package de.clerkvest.api.entity.project.comment;

import org.springframework.stereotype.Component;

import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

@Component
public class ProjectCommentListener {

    @PrePersist
    void preCreate(ProjectComment projectComment) {

    }

    @PreUpdate
    void preUpdate(ProjectComment projectComment) {

    }

    @PreRemove
    void preRemove(ProjectComment projectComment) {

    }
}
