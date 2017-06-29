package me.sjtumeow.meow.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    protected static final String NOT_DELETED = "deleted_on > CURRENT_TIMESTAMP OR deleted_on IS NULL";

    protected LocalDateTime createdAt;

    protected LocalDateTime updatedAt;

    protected LocalDateTime deletedAt;

}