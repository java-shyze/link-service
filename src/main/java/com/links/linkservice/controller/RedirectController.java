package com.links.linkservice.controller;

import com.links.linkservice.dto.LinkClickEvent;
import com.links.linkservice.model.Link;
import com.links.linkservice.service.KafkaProducerService;
import com.links.linkservice.service.LinkService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.view.RedirectView;

@Tag(name = "Redirection API")
@RestController
@CrossOrigin(origins = "*")
public class RedirectController {

    @Autowired
    private LinkService linkService;

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @Operation(
        summary = "Перенаправление",
        description = "Перенаправляет на оригинальную ссылку и отправляет аналитику"
    )
    @GetMapping("/{alias}")
    public RedirectView redirect(
            @PathVariable String alias,
            HttpServletRequest request) {

        Link link = linkService.getLinkByAlias(alias)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Short link '" + alias + "' not found"
            ));

        linkService.incrementVisits(alias);

        sendClickEvent(link, request);

        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(link.getUrl());
        return redirectView;
    }

    /**
     * Формирование и отправка события клика
     */
    private void sendClickEvent(Link link, HttpServletRequest request) {
        try {
            LinkClickEvent event = new LinkClickEvent(
                link.getId(),
                link.getAlias(),
                link.getUrl(),
                getClientIp(request),
                request.getHeader("User-Agent"),
                request.getHeader("Referer")
            );

            kafkaProducerService.sendLinkClickEvent(event);
        } catch (Exception e) {
            System.err.println("Failed to send analytics event: " + e.getMessage());
        }
    }

    /**
     * Получение IP адреса клиента (с учетом прокси)
     */
    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // Если через прокси, берем первый IP
        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }
        return ip;
    }
}
