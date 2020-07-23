package de.clerkvest.api.entity.project;

import de.clerkvest.api.entity.SendNotificationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

@Component
public class ProjectListener {

    private SendNotificationsService sendNotificationsService;

    @Autowired
    public void setMyService(SendNotificationsService sendNotificationsService) {
        this.sendNotificationsService = sendNotificationsService;
    }

    @PrePersist
    void preCreate(Project project) {
        sendNotificationsService.freshProjectAvailable(project);
    }

    @PreUpdate
    void preUpdate(Project project) {
        if (project.isReached()) {
            sendNotificationsService.projectFunded(project.getEmployee(), project);
        }
    }

    @PreRemove
    void preRemove(Project project) {

    }

}
