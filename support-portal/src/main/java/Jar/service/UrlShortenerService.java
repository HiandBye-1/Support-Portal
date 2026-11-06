package Jar.service;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import Jar.repository.UrlMappingRepository;
import Jar.util.ShortCodeGenerator;
import Jar.entities.UrlMapping;
import Jar.entities.NormalUser;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.context.SecurityContextHolder;
import Jar.repository.NormalUserRepository;
import Jar.InputValidator.*;
import org.hibernate.exception.ConstraintViolationException;
import Jar.exception.DuplicateShortCodeException;
import Jar.dto.UrlMappingResponse;
import org.springframework.beans.factory.annotation.Value;


@Service
public class UrlShortenerService {

    @Value("${app.base-url}")
    private String baseUrl;

    private final UrlMappingRepository urlMappingRepository;
    private final ShortCodeGenerator shortCodeGenerator;
    private final NormalUserRepository normalUserRepository;
    private final InputValidator inputValidator;
    

    public UrlShortenerService(UrlMappingRepository urlMappingRepository,
                               ShortCodeGenerator shortCodeGenerator,
                               NormalUserRepository normalUserRepository,
                               InputValidator inputValidator
                               ) {
        this.urlMappingRepository = urlMappingRepository;
        this.shortCodeGenerator = shortCodeGenerator;
        this.normalUserRepository = normalUserRepository;
        this.inputValidator = inputValidator;
        
    }

    public UrlMappingResponse createMapping(String longUrl) {

        if(!inputValidator.linkIsValid(longUrl)){
            throw new IllegalArgumentException("link is not valid");
        }
        
        UrlMapping urlMapping = new UrlMapping();
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        NormalUser owner =  normalUserRepository.findByUsername(user.getUsername()).orElseThrow(() -> 
                                                                new IllegalStateException("login first"));
        urlMapping.setOwner(owner);
        urlMapping.setLongUrl(longUrl);


        for(int i = 0; i < 10; i++){ //try 10 times to generate a unique short code
            urlMapping.setShortUrl(shortCodeGenerator.generateShortCode(6)); //generate a short code of 6 characters
            try{
                urlMappingRepository.saveAndFlush(urlMapping); //save immediately
                
                return new UrlMappingResponse(urlMapping.getLongUrl(), baseUrl+""+urlMapping.getShortUrl());

            }catch(DataIntegrityViolationException e){
                if(e.getCause() instanceof ConstraintViolationException cve){
                    if ("PRIMARY".equals(cve.getConstraintName())){//is primary
                        if(i == 9) throw new DuplicateShortCodeException("Short code already exists");
                    }else throw e;//if not primary 
                }else throw e;
            }

        }


        throw new DuplicateShortCodeException("Unable to generate a unique short code");


        
    }



}
