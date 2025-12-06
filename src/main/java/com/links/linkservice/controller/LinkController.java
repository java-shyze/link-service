package com.links.linkservice.controller;

import com.links.linkservice.dto.CreateLinkRequest;
import com.links.linkservice.dto.LinkResponse;
import com.links.linkservice.dto.UpdateLinkRequest;
import com.links.linkservice.model.Link;
import com.links.linkservice.service.LinkService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


@Tag(name = "Links API", description = "Управление короткими ссылками")
@RestController
@RequestMapping("/api/v1")  
@CrossOrigin(origins = "http://localhost:5173")
public class LinkController {

    @Autowired
    private LinkService linkService;

    @Operation(
        summary = "Создать короткую ссылку",
        description = "Создаёт новую короткую ссылку. Если alias не указан — генерируется автоматически."
    )
    @PostMapping("/links")
    public ResponseEntity<LinkResponse> createShortLink(@RequestBody CreateLinkRequest request) {
        Link link;
        
        if (!request.getAlias().isEmpty()) {
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
    }

    @Operation(
        summary = "Получить все ссылки",
        description = "Получает все ссылки. Ответ содержит пагинацию"
    )
    @GetMapping("/links")
    public ResponseEntity<Page<LinkResponse>> getAllLinks(
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "20") int size,
        @RequestParam(required = false) String search
    ) {

        Pageable pageable = PageRequest.of(page-1, size, Sort.by("createdAt").descending());
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

    @Operation(
        summary = "Получить ссылку по alias",
        description = "Получает дополнительную информацию о ссылке по alias"
    )
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

    @Operation(
        summary = "Удалить ссылку",
        description = "Удаляет ссылку по id"
    )
    @DeleteMapping("/links/{id}")
    public ResponseEntity<Void> deleteLink(@PathVariable Long id) {
        linkService.deleteLink(id);
        return ResponseEntity.noContent().build();
    }


    @Operation(
        summary = "Редактировать ссылку",
        description = "Редактирует ссылку по id"
    )
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
}
