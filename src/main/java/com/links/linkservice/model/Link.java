package com.links.linkservice.model;

import jakarta.persistence.*;

@Entity
@Table(name = "links")
public class Link {

    // fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "url", nullable = false)
    private String url;

    // CTORs
    public Link () {}

    public Link(String url) { 
        this.url = url;
    }

    public Link(String title, String url) {
        this.title = title;
        this.url = url;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Link [title=" + title + ", url=" + url + "]";
    }
}