package com.links.linkservice.controller;

import com.links.linkservice.service.LinkService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class RedirectController { 

    @Autowired
    private LinkService linkService;

    @GetMapping("/{alias}")
    public RedirectView redirect(
            @PathVariable String alias,
            HttpServletRequest request) {

        return linkService.getLinkByAlias(alias)
            .map(link -> {
                RedirectView redirectView = new RedirectView();
                redirectView.setUrl(link.getUrl());
                return redirectView;
            })
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "Short link '" + alias + "' not found"
            ));
    }
}
