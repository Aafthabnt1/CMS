package com.example.cms.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PrePersist;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
@MappedSuperclass

public class BaseClass implements Serializable {
    @Column(name="created_date",updatable = false)
    @CreationTimestamp
    private LocalDateTime createdDate;

    @Column(name="updated_date")
    @UpdateTimestamp
    private LocalDateTime updatedDate;

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }
}
