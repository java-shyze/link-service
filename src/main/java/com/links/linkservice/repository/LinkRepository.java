package com.links.linkservice.repository;

import com.links.linkservice.model.Link;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Repository
public interface LinkRepository extends JpaRepository<Link, Long> {

    // !this methods are provided by Spring Data JPA!
    /*
    *
    Link save(Link link);               // save(Link link) works like an update(Link link)
    Optional<Link> findById(Long id);        
    List<Link> findAll();                    
    void deleteById(Long id);                
    *
    */
    
    // add here specific methods if necessary
    Optional<Link> findByTitle(String title);
}