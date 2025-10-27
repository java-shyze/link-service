package com.shyze.linkservice.model;

import jakarta.persistence.*;

@Entity
@Table(name = "links")
public class Link {

    // fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "title", nullable = false)
    private String title;

    @Override
    public String toString() {
        return "Link [url=" + url + ", title=" + title + "]";
    }

    // CTORs
    public Link () {}

    public Link(String url) { 
        this.url = url;
    }

    public Link(String url, String title) {
        this.url = url;
        this.title = title;
    }

    // Getters and Setters
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}