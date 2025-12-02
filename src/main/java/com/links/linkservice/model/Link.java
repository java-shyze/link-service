package com.links.linkservice.model;

import jakarta.persistence.*;

@Entity
@Table(name = "links", indexes = {
    @Index(name = "idx_alias", columnList = "alias", unique = true)
})
public class Link {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "url", nullable = false, length = 2048) 
    private String url;

    @Column(name = "alias", nullable = false, unique = true, length = 50)
    private String alias;
   
    public Link() {}

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
        return "Link [id=" + id + ", url=" + url + ", alias=" + alias + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Link link = (Link) o;
        return id != null && id.equals(link.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
