package de.clerkvest.api.entity.project.comment;

import de.clerkvest.api.entity.SendNotificationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

@Component
public class ProjectCommentListener {
    private SendNotificationsService sendNotificationsService;

    @Autowired
    public void setMyService(SendNotificationsService sendNotificationsService) {
        this.sendNotificationsService = sendNotificationsService;
    }

    @PrePersist
    void preCreate(ProjectComment projectComment) {
        //Only Notify User if he didn't comment on his own Project
        if (projectComment.getEmployee() != projectComment.getProject().getEmployee()) {
            sendNotificationsService.freshProjectComment(projectComment.getProject().getEmployee(), projectComment);
        }
    }

    @PreUpdate
    void preUpdate(ProjectComment projectComment) {

    }

    @PreRemove
    void preRemove(ProjectComment projectComment) {

    }
}
