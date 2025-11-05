package com.links.linkservice.repository;

import com.links.linkservice.model.Link;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

// repository methods are provided by Spring Data JPA
@Repository
public interface LinkRepository extends JpaRepository<Link, Long> {
    // Link save(Link link);
    // Optional<Link> findById(Long id);        
    // List<Link> findAll();                    
    // void deleteById(Long id);                
}