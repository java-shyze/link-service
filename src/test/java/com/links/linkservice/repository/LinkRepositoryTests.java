package com.links.linkservice.repository;

import com.links.linkservice.config.EnvConfig;
import com.links.linkservice.model.Link;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.annotation.Commit;

import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class LinkRepositoryTests {

    static {
        EnvConfig.loadEnv();
    }

    @Autowired
    private LinkRepository linkRepository;

    @Test
    @Commit
    void CRUD() {
        linkRepository.deleteAll();

        // ~~~ CREATE ~~~
        Link link1 = new Link("https://example1.com", "Alias 1");
        Link link2 = new Link("https://example2.com", "Alias 2");

        Link savedLink1 = linkRepository.save(link1);
        Link savedLink2 = linkRepository.save(link2);

        assertThat(savedLink1.getId()).isNotNull();
        assertThat(savedLink2.getId()).isNotNull();

        assertThat(savedLink1.getUrl()).isEqualTo("https://example1.com");
        assertThat(savedLink1.getAlias()).isEqualTo("Alias 1");
        assertThat(savedLink2.getUrl()).isEqualTo("https://example2.com");
        assertThat(savedLink2.getAlias()).isEqualTo("Alias 2");

        // ~~~ READ ~~~
        Link foundLink1 = linkRepository.findById(savedLink1.getId())
                .orElseThrow(() -> new RuntimeException("URL 1 not found."));
        Link foundLink2 = linkRepository.findById(savedLink2.getId())
                .orElseThrow(() -> new RuntimeException("URL 2 not found."));

        assertThat(foundLink1).isEqualTo(savedLink1);
        assertThat(foundLink2).isEqualTo(savedLink2);

        List<Link> allLinks = linkRepository.findAll();

        assertThat(allLinks).hasSize(2);
        assertThat(allLinks).containsExactly(savedLink1, savedLink2);

        // ~~~ UPDATE ~~~
        foundLink1.setUrl("https://updated-example1.com");
        foundLink1.setAlias("Updated Alias 1");
        Link updatedLink = linkRepository.save(foundLink1);

        assertThat(updatedLink.getId()).isEqualTo(savedLink1.getId());
        assertThat(updatedLink.getUrl()).isEqualTo("https://updated-example1.com");
        assertThat(updatedLink.getAlias()).isEqualTo("Updated Alias 1");

        // additional check "read"
        Link retrievedAfterUpdate = linkRepository.findById(savedLink1.getId())
                .orElseThrow(() -> new RuntimeException("URL not found after update"));
        assertThat(retrievedAfterUpdate.getUrl()).isEqualTo("https://updated-example1.com");
        assertThat(retrievedAfterUpdate.getAlias()).isEqualTo("Updated Alias 1");

        // ~~~ DELETE ~~~
        linkRepository.deleteById(savedLink2.getId());

        assertThat(linkRepository.findById(savedLink2.getId())).isEmpty();

        List<Link> linksAfterDelete = linkRepository.findAll();
        assertThat(linksAfterDelete).hasSize(1);
        assertThat(linksAfterDelete).containsExactly(updatedLink);
    }
}