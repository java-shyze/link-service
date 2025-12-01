package com.links.linkservice.dto;

public class LinkResponse {
    private Long id;
    private String originalUrl;
    private String alias;
    private String shortUrl;

    public LinkResponse() {}

    public LinkResponse(Long id, String originalUrl, String alias, String shortUrl) {
        this.id = id;
        this.originalUrl = originalUrl;
        this.alias = alias;
        this.shortUrl = shortUrl;
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
}
