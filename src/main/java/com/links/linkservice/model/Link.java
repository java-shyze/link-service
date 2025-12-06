package com.links.linkservice.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "links", indexes = {
    @Index(name = "idx_alias", columnList = "alias", unique = true),
    @Index(name = "idx_created_at", columnList = "createdAt")
})
public class Link {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "url", nullable = false, length = 2048)
    private String url;

    @Column(name = "alias", nullable = false, unique = true, length = 50)
    private String alias;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "visits", nullable = false, columnDefinition = "bigint default 0")
    private Long visits = 0L;

    public Link() {}

    public Link(String url, String alias) {
        this.url = url;
        this.alias = alias;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    public String getAlias() { return alias; }
    public void setAlias(String alias) { this.alias = alias; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public Long getVisits() {return visits;}
    public void setVisits(Long visits) {this.visits = visits;}
}
