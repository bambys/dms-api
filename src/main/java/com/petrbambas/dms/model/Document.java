package com.petrbambas.dms.model;

import com.petrbambas.dms.annotations.CreatedBy;
import com.petrbambas.dms.listener.CreatedByListener;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "document")
@EntityListeners({AuditingEntityListener.class, CreatedByListener.class})
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int Id;

    @Column(name = "name")
    private String name;

    // @CreatedDate annotation provides setting of created_at automatically
    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdTimestamp;

    // custom annotation for getting the name of API user
    @CreatedBy
    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "type")
    private String type;

    public Document() {
    }

    public Document(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getCreatedTimestamp() {
        return createdTimestamp;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public String toString() {
        return "Document{" +
                "Id=" + Id +
                ", name='" + name + '\'' +
                ", createdTimestamp=" + createdTimestamp +
                ", createdBy='" + createdBy + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
