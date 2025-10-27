package com.shyze.linkservice.repository;

import com.shyze.linkservice.model.Link;
import org.springframework.data.jpa.repository.JpaRepository;

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
    // ...
}