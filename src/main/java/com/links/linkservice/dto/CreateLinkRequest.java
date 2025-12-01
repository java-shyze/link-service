package com.links.linkservice.dto;

public class CreateLinkRequest {
    
    private String url;
    private String customAlias;

    public CreateLinkRequest() {}

    public CreateLinkRequest(String url, String customAlias) {
        this.url = url;
        this.customAlias = customAlias;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCustomAlias() {
        return customAlias;
    }

    public void setCustomAlias(String customAlias) {
        this.customAlias = customAlias;
    }
}
