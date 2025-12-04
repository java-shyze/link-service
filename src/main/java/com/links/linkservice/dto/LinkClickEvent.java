package com.links.linkservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public class LinkClickEvent {
    private Long linkId;
    private String alias;
    private String originalUrl;
    
    private String ipAddress;
    private String userAgent;
    private String referer;
    
    private String country;
    private String city;
    
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime clickedAt;

    public LinkClickEvent() {
        this.clickedAt = LocalDateTime.now();
    }

    public LinkClickEvent(Long linkId, String alias, String originalUrl, 
                         String ipAddress, String userAgent, String referer) {
        this.linkId = linkId;
        this.alias = alias;
        this.originalUrl = originalUrl;
        this.ipAddress = ipAddress;
        this.userAgent = userAgent;
        this.referer = referer;
        this.clickedAt = LocalDateTime.now();
    }

    public Long getLinkId() { return linkId; }
    public void setLinkId(Long linkId) { this.linkId = linkId; }
    
    public String getAlias() { return alias; }
    public void setAlias(String alias) { this.alias = alias; }
    
    public String getOriginalUrl() { return originalUrl; }
    public void setOriginalUrl(String originalUrl) { this.originalUrl = originalUrl; }
    
    public String getIpAddress() { return ipAddress; }
    public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }
    
    public String getUserAgent() { return userAgent; }
    public void setUserAgent(String userAgent) { this.userAgent = userAgent; }
    
    public String getReferer() { return referer; }
    public void setReferer(String referer) { this.referer = referer; }
    
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
    
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    
    public LocalDateTime getClickedAt() { return clickedAt; }
    public void setClickedAt(LocalDateTime clickedAt) { this.clickedAt = clickedAt; }

    @Override
    public String toString() {
        return "LinkClickEvent{" +
                "linkId=" + linkId +
                ", alias='" + alias + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                ", clickedAt=" + clickedAt +
                '}';
    }
}
