package com.petrbambas.dms.model;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.petrbambas.dms.annotations.CreatedBy;
import com.petrbambas.dms.listener.CreatedByListener;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "protocol")
@EntityListeners({AuditingEntityListener.class, CreatedByListener.class})
public class Protocol {
    public enum ProtocolStatus {
        NEW,
        PREPARED_FOR_SHIPMENT,
        CANCELLED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int Id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    //@Pattern(regexp = "^(NEW|PREPARED_FOR_SHIPMENT|CANCELLED)$", message = "Status must be NEW, PREPARED_FOR_SHIPMENT or CANCELLED")
    @Column(name = "status")
    private ProtocolStatus status;

    // @CreatedDate annotation provides setting of created_at automatically
    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdTimestamp;

    // custom annotation for getting the name of API user
    @CreatedBy
    @Column(name = "created_by")
    private String createdBy;

    /*
    Protocol has to contain at least one or more document - can't be saved to database without one
    document at minimum
    @JsonIdentityInfo not used finally because all documents attributes should be contained in JSON responses
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
     */
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH})
    @JoinTable(
            name = "protocol_document",
            joinColumns = @JoinColumn(name = "prot_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "doc_id", referencedColumnName = "id")
    )
    @NotEmpty(message = "Protocol must have at least one document")
    private Set<Document> documents = new HashSet<>();

    public Protocol() {
    }

    public Protocol(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(Set<Document> documents) {
        this.documents = documents;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    // no need for setCreatedTimestamp because of use @CreatedDate annotation and @EntityListeners(AuditingEntityListener.class)
    public LocalDateTime getCreatedTimestamp() {
        return createdTimestamp;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public ProtocolStatus getStatus() {
        return status;
    }

    public void setStatus(ProtocolStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Protocol{" +
                "Id=" + Id +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", createdTimestamp=" + createdTimestamp +
                ", createdBy='" + createdBy + '\'' +
                ", documents=" + documents +
                '}';
    }
}
