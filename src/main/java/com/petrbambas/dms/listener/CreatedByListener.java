package com.petrbambas.dms.listener;

import com.petrbambas.dms.model.Document;
import com.petrbambas.dms.model.Protocol;
import jakarta.persistence.PrePersist;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


// class sets variable createdBy automatically from the SecurityContext - userName
public class CreatedByListener {

    @PrePersist
    public void setCreatedBy(Object entity) {
        if (entity instanceof Document || entity instanceof Protocol) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            if (entity instanceof Document) {
                ((Document) entity).setCreatedBy(userName);
                return;
            }
            if (entity instanceof Protocol) {
                ((Protocol) entity).setCreatedBy(userName);
            }
        }
    }
}