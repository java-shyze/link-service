package com.links.linkservice.repository;

import com.links.linkservice.model.Link;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

// repository methods are provided by Spring Data JPA
@Repository
public interface LinkRepository extends JpaRepository<Link, Long> {
    // Link save(Link link);
    // Optional<Link> findById(Long id);        
    // List<Link> findAll();                    
    // void deleteById(Long id);    
    
    Optional<Link> findByAlias(String alias);
    boolean existsByAlias(String alias);
}
