package com.links.linkservice.controller;

import com.links.linkservice.dto.CreateLinkRequest;
import com.links.linkservice.dto.LinkResponse;
import com.links.linkservice.model.Link;
import com.links.linkservice.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.stream.Collectors;

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
            
            if (request.getCustomAlias() != null && !request.getCustomAlias().isEmpty()) {
                link = linkService.createShortLinkWithCustomAlias(request.getUrl(), request.getCustomAlias());
            } else {
                link = linkService.createShortLink(request.getUrl());
            }
            
            LinkResponse response = new LinkResponse(
                link.getId(),
                link.getUrl(),
                link.getAlias(),
                getShortUrl(link.getAlias())
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
    public ResponseEntity<List<LinkResponse>> getAllLinks() {
        List<Link> links = linkService.getAllLinks();
        List<LinkResponse> responses = links.stream()
            .map(link -> new LinkResponse(
                link.getId(),
                link.getUrl(),
                link.getAlias(),
                getShortUrl(link.getAlias())
            ))
            .collect(Collectors.toList());
        
        return ResponseEntity.ok(responses);
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
                    getShortUrl(link.getAlias())
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
     * Перенаправление по короткой ссылке
     * GET /{alias}
     */
    @GetMapping("/{alias}")
    public RedirectView redirect(@PathVariable String alias) {
        return linkService.getLinkByAlias(alias)
            .map(link -> {
                RedirectView redirectView = new RedirectView();
                redirectView.setUrl(link.getUrl());
                return redirectView;
            })
            .orElseThrow(() -> new RuntimeException("Link not found"));
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
