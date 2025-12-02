package com.links.linkservice.dto;

public class UpdateLinkRequest {
    private String url;
    private String alias;

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

    public boolean isEmpty() {
        return (url == null || url.isBlank()) && (alias == null || alias.isBlank());
    }
}
