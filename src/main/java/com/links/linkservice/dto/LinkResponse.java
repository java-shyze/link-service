package com.links.linkservice.dto;

import java.time.LocalDateTime;

public class LinkResponse {
    private Long id;
    private String originalUrl;
    private String alias;
    private String shortUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public LinkResponse() {}

    public LinkResponse(Long id, String originalUrl, String alias, String shortUrl, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.originalUrl = originalUrl;
        this.alias = alias;
        this.shortUrl = shortUrl;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public LocalDateTime getCreatedAt(){
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt){
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt(){
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt){
        this.updatedAt = updatedAt;
    }
}
