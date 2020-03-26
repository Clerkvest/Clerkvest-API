package de.clerkvest.api.entity.audit;

import de.clerkvest.api.implement.service.IServiceEntity;
import de.clerkvest.api.security.EmployeeUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.persistence.PostPersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

@Component
public class AuditListener {

    private static AuditService auditService;

    @Autowired
    public void setMyService(AuditService auditService) {
        AuditListener.auditService = auditService;
    }

    @PostPersist
    private void postPersist(Object object) {
        beforeAnyOperation(object, "INSERT");
    }

    @PreUpdate
    private void beforeUpdate(Object object) {
        beforeAnyOperation(object, "UPDATE");
    }

    @PreRemove
    private void beforeRemove(Object object) {
        beforeAnyOperation(object, "REMOVE");
    }

    private void beforeAnyOperation(Object object, final String audit) {
        if (object instanceof IServiceEntity) {
            IServiceEntity entity = (IServiceEntity) object;
            Long changedEntityID = entity.getId();
            Class<?> changedEntityClazz = object.getClass();
            Audit audit1 = Audit.builder().audit(audit).clazz(changedEntityClazz.getSimpleName()).entityId(changedEntityID).build();
            if (SecurityContextHolder.getContext() != null && SecurityContextHolder.getContext().getAuthentication() != null) {
                EmployeeUserDetails changedEntityBy = (EmployeeUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                audit1.setEmployeeId(changedEntityBy.getEmployeeId());
                audit1.setCompanyId(changedEntityBy.getCompanyId());
            }
            if (auditService != null) {
                auditService.save(audit1);
            }
        }
    }
}
