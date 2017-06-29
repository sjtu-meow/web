package me.sjtumeow.meow.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    protected LocalDateTime createdAt;


    protected LocalDateTime updatedAt;


    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    protected LocalDateTime deletedAt;

}