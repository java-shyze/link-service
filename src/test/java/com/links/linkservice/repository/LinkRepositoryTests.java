package com.links.linkservice.repository;

import com.links.linkservice.model.Link;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
@Transactional
public class LinkRepositoryTests {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine")
        .withDatabaseName("testdb")
        .withUsername("testuser")
        .withPassword("testpass");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
    }

    @Autowired
    private LinkRepository linkRepository;

    @Test
    void shouldSaveAndFindLink() {
        // Given
        Link link = new Link("https://example.com", "Test Title");

        // When
        Link savedLink = linkRepository.save(link);
        Optional<Link> foundLink = linkRepository.findById(savedLink.getId());

        // Then
        assertThat(foundLink).isPresent();
        assertThat(foundLink.get().getUrl()).isEqualTo("https://example.com");
        assertThat(foundLink.get().getTitle()).isEqualTo("Test Title");
    }

    @Test
    void shouldFindAllLinks() {
        // Given
        Link link1 = new Link("https://example1.com", "Title 1");
        Link link2 = new Link("https://example2.com", "Title 2");
        
        linkRepository.save(link1);
        linkRepository.save(link2);

        // When
        List<Link> links = linkRepository.findAll();

        // Then
        assertThat(links).hasSize(2);
        assertThat(links).extracting(Link::getUrl)
                        .containsExactlyInAnyOrder("https://example1.com", "https://example2.com");
    }

    @Test
    void shouldUpdateLink() {
        // Given
        Link link = new Link("https://original.com", "Original Title");
        Link savedLink = linkRepository.save(link);

        // When
        savedLink.setUrl("https://updated.com");
        savedLink.setTitle("Updated Title");
        Link updatedLink = linkRepository.save(savedLink);

        // Then
        assertThat(updatedLink.getUrl()).isEqualTo("https://updated.com");
        assertThat(updatedLink.getTitle()).isEqualTo("Updated Title");
    }

    @Test
    void shouldDeleteLink() {
        // Given
        Link link = new Link("https://delete.com", "To Delete");
        Link savedLink = linkRepository.save(link);
        
        Long id = savedLink.getId();
        assertThat(linkRepository.findById(id)).isPresent();

        // When
        linkRepository.deleteById(id);

        // Then
        assertThat(linkRepository.findById(id)).isEmpty();
    }

    @Test
    void shouldFindLinkByTitle() {
        // Given
        String title = "Unique Title";
        Link link = new Link("https://unique.com", title);
        linkRepository.save(link);

        // When
        Optional<Link> foundLink = linkRepository.findByTitle(title);

        // Then
        assertThat(foundLink).isPresent();
        assertThat(foundLink.get().getUrl()).isEqualTo("https://unique.com");
        assertThat(foundLink.get().getTitle()).isEqualTo(title);
    }

    @Test
    void shouldReturnEmptyWhenTitleNotFound() {
        // When
        Optional<Link> foundLink = linkRepository.findByTitle("NonExistentTitle");

        // Then
        assertThat(foundLink).isEmpty();
    }
}