package com.links.linkservice.service;

import com.links.linkservice.model.Link;
import com.links.linkservice.repository.LinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class LinkService {

    @Autowired
    private LinkRepository linkRepository;

    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int ALIAS_LENGTH = 6;
    private static final int MAX_RETRIES = 10;


    public Link createShortLink(String url) {
        if (!isValidUrl(url)) {
            throw new IllegalArgumentException("Invalid URL format");
        }

        String alias = generateUniqueAlias();
        Link link = new Link(url, alias);
        return linkRepository.save(link);
    }

    
    public Link createShortLinkWithCustomAlias(String url, String customAlias) {
        if (!isValidUrl(url)) {
            throw new IllegalArgumentException("Invalid URL format");
        }

        if (customAlias == null || customAlias.trim().isEmpty()) {
            throw new IllegalArgumentException("Custom alias cannot be empty");
        }

        if (linkRepository.findByAlias(customAlias).isPresent()) {
            throw new IllegalArgumentException("Alias already exists");
        }

        Link link = new Link(url, customAlias);
        return linkRepository.save(link);
    }

    
    public Optional<Link> getLinkByAlias(String alias) {
        return linkRepository.findByAlias(alias);
    }

  
    public List<Link> getAllLinks() {
        return linkRepository.findAll();
    }

  
    public void deleteLink(Long id) {
        linkRepository.deleteById(id);
    }

    
    private String generateUniqueAlias() {
        for (int i = 0; i < MAX_RETRIES; i++) {
            String alias = generateRandomAlias();
            if (linkRepository.findByAlias(alias).isEmpty()) {
                return alias;
            }
        }
        throw new RuntimeException("Failed to generate unique alias after " + MAX_RETRIES + " attempts");
    }

    
    private String generateRandomAlias() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(ALIAS_LENGTH);
        
        for (int i = 0; i < ALIAS_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }
        
        return sb.toString();
    }

  
    private boolean isValidUrl(String url) {
        if (url == null || url.trim().isEmpty()) {
            return false;
        }
        
        String urlPattern = "^(https?://)?(www\\.)?[a-zA-Z0-9-]+(\\.[a-zA-Z]{2,})+(/.*)?$";
        return url.matches(urlPattern);
    }
}
