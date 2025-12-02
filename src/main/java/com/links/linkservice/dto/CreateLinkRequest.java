package com.links.linkservice.dto;

public class CreateLinkRequest {
    private String url;
    private String alias;

    public CreateLinkRequest() {}
    public CreateLinkRequest(String url, String alias) {
        this.url = url;
        this.alias = alias;
    }

    public String getUrl() { 
        return url; 
    }

    public void setUrl(String url) { 
        this.url = url; 
    }

    public String getAlias() { 
        return alias; 
    }

    public void setAlias(String alias) { 
        this.alias = alias;
    }
}
