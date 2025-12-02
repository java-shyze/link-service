package com.links.linkservice.controller;

import com.links.linkservice.dto.CreateLinkRequest;
import com.links.linkservice.dto.LinkResponse;
import com.links.linkservice.dto.UpdateLinkRequest;
import com.links.linkservice.model.Link;
import com.links.linkservice.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


@RestController
@RequestMapping("/api")
public class LinkController {

    @Autowired
    private LinkService linkService;

    /**
     * Создание короткой ссылки с автоматическим alias
     * POST /api/links
     */
    @PostMapping("/links")
    public ResponseEntity<LinkResponse> createShortLink(@RequestBody CreateLinkRequest request) {
        try {
            Link link;
            
            if (request.getAlias() != null && !request.getAlias().isEmpty()) {
                link = linkService.createShortLinkWithCustomAlias(request.getUrl(), request.getAlias());
            } else {
                link = linkService.createShortLink(request.getUrl());
            }
            
            LinkResponse response = new LinkResponse(
                link.getId(),
                link.getUrl(),
                link.getAlias(),
                getShortUrl(link.getAlias()),
                link.getCreatedAt(),
                link.getUpdatedAt()
            );
            
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Получение всех ссылок
     * GET /api/links
     */
    @GetMapping("/links")
    public ResponseEntity<Page<LinkResponse>> getAllLinks(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "20") int size,
        @RequestParam(required = false) String search
    ) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Link> linkPage = linkService.getAllLinks(pageable, search);

        Page<LinkResponse> response = linkPage.map(link -> new LinkResponse(
            link.getId(),
            link.getUrl(),
            link.getAlias(),
            getShortUrl(link.getAlias()),
            link.getCreatedAt(),
            link.getUpdatedAt()
        ));

        return ResponseEntity.ok(response);
    }

    /**
     * Получение информации о ссылке по alias
     * GET /api/links/{alias}
     */
    @GetMapping("/links/{alias}")
    public ResponseEntity<LinkResponse> getLinkByAlias(@PathVariable String alias) {
        return linkService.getLinkByAlias(alias)
            .map(link -> {
                LinkResponse response = new LinkResponse(
                    link.getId(),
                    link.getUrl(),
                    link.getAlias(),
                    getShortUrl(link.getAlias()),
                    link.getCreatedAt(),
                    link.getUpdatedAt()
                );
                return ResponseEntity.ok(response);
            })
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Удаление ссылки
     * DELETE /api/links/{id}
     */
    @DeleteMapping("/links/{id}")
    public ResponseEntity<Void> deleteLink(@PathVariable Long id) {
        linkService.deleteLink(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Редактирование по id
     * PATCH /links/{id}
     */

    @PatchMapping("/links/{id}")
    public ResponseEntity<LinkResponse> updateLink(
        @PathVariable Long id,
        @RequestBody UpdateLinkRequest request
    ) {
        if (request.isEmpty()) {
            throw new IllegalArgumentException("No fields to update");
        }

        Link updated = linkService.updateLink(id, request.getUrl(), request.getAlias());

        LinkResponse response = new LinkResponse(
            updated.getId(),    
            updated.getUrl(),
            updated.getAlias(),
            getShortUrl(updated.getAlias()),
            updated.getCreatedAt(),
            updated.getUpdatedAt()
        );

        return ResponseEntity.ok(response);
    }

    /**
     * Вспомогательный метод для формирования короткого URL
     */
    private String getShortUrl(String alias) {
        return "http://localhost:8080/" + alias;
    }

    /**
     * Обработка исключений
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleException(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}
