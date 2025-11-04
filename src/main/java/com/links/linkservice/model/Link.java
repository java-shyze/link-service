package com.links.linkservice.model;

import jakarta.persistence.*;

@Entity
@Table(name = "links")
public class Link {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "url", nullable = false) 
    private String url;

    @Column(name = "alias", nullable = false)
    private String alias;
   
    public Link () {}

    public Link(String url, String alias) {
        this.url = url;
        this.alias = alias;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Link [url=" + url + ", alias=" + alias + "]";
    }
}