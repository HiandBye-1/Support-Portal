
package Jar.controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import Jar.dto.CreateUrlRequest;
import Jar.service.UrlShortenerService;
import Jar.dto.UrlMappingResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.ResponseEntity;
import java.net.URI;


@RestController
public class UrlMappingController {
    private final UrlShortenerService urlShortenerService;

    public UrlMappingController(UrlShortenerService urlShortenerService) {
        this.urlShortenerService = urlShortenerService;
    }

    @PostMapping("/url/shorten")
    @PreAuthorize("hasRole('NORMALUSER')")
    @ResponseStatus(HttpStatus.CREATED)
    public UrlMappingResponse shortenUrl(@RequestBody CreateUrlRequest request) {
        return urlShortenerService.createMapping(request.getLongUrl());
    }

    @GetMapping("/r/{urlCode}")
    public ResponseEntity<Void> redirect(@PathVariable String urlCode) {
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(urlShortenerService.redirect(urlCode))).build();
    }
}