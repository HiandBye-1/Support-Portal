package Jar;
import Jar.service.UrlShortenerService;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import Jar.repository.NormalUserRepository;
import Jar.util.ShortCodeGenerator;
import Jar.InputValidator.InputValidator;
import Jar.repository.UrlMappingRepository;
import Jar.entities.NormalUser;
import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;
import Jar.entities.UrlMapping;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterEach;
import Jar.dto.*;

@ExtendWith(MockitoExtension.class)
public class UrlShortenerServiceTest {
    //test this class
    @InjectMocks private UrlShortenerService urlShortenerService;

    @Mock private ShortCodeGenerator shortCodeGenerator;

    @Mock private NormalUserRepository normalUserRepository;

    @Mock private InputValidator inputValidator;

    @Mock private UrlMappingRepository urlMappingRepository;

    @Value("${app.base-url}")
    private String baseUrl;
    

    @Test 
    void createMappingTest() {
        String baseUrl = "https://localhost:8443/r/";
        NormalUser normalUser = new NormalUser();
        
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_NORMALUSER");//add role
        User principal = new User("username", "password", Collections.singletonList(authority));
        UsernamePasswordAuthenticationToken authentication = 
            new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        normalUser.setUsername("username");
        normalUser.setPassword("password");

        when(inputValidator.linkIsValid("http://example.com")).thenReturn(true);
        when(shortCodeGenerator.generateShortCode(6)).thenReturn("123456");
        when(normalUserRepository.findByUsername("username")).thenReturn(Optional.of(normalUser));

        //invocation use save and flush method's argument, any = accept any argument as long as it's this class
        when(urlMappingRepository.saveAndFlush(any(UrlMapping.class))).thenAnswer(invocation ->invocation.getArgument(0));

        ReflectionTestUtils.setField(urlShortenerService, "baseUrl", baseUrl);
        UrlMappingResponse urlMapping = urlShortenerService.createMapping("http://example.com");


        assertEquals("https://localhost:8443/r/123456", urlMapping.getShortUrl());
        assertEquals("http://example.com", urlMapping.getLongUrl());

    } 

    @Test 
    void InvalidLongUrl(){


        when(inputValidator.linkIsValid("awd")).thenReturn(false);
        assertThrows(IllegalArgumentException.class, () -> urlShortenerService.createMapping("awd"));
        verify(shortCodeGenerator, never()).generateShortCode(6);
    }

    @Test
    void returnUrl() {
        UrlMapping urlMapping = new UrlMapping();

        urlMapping.setShortUrl("123456");
        urlMapping.setLongUrl("https://example.com");

        when(urlMappingRepository.findByShortUrl("123456"))
                .thenReturn(Optional.of(urlMapping));

        String result = urlShortenerService.redirect("123456");

        assertEquals("https://example.com", result);
    }

    @AfterEach
    void clearSecurityContext() {
        SecurityContextHolder.clearContext();
}
}
