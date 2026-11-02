package Jar.service;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import Jar.repository.UrlMappingRepository;
import Jar.util.ShortCodeGenerator;
import Jar.entities.UrlMapping;
import Jar.entities.NormalUser;
import org.springframework.security.core.context.SecurityContextHolder;
import Jar.repository.NormalUserRepository;
import Jar.InputValidator.*;


@Service
public class UrlShortenerService {
    private final UrlMappingRepository urlMappingRepository;
    private final ShortCodeGenerator shortCodeGenerator;
    private final NormalUserRepository normalUserRepository;
    private final InputValidator inputValidator;

    public UrlShortenerService(UrlMappingRepository urlMappingRepository,
                               ShortCodeGenerator shortCodeGenerator,
                               NormalUserRepository normalUserRepository,
                               InputValidator inputValidator) {
        this.urlMappingRepository = urlMappingRepository;
        this.shortCodeGenerator = shortCodeGenerator;
        this.normalUserRepository = normalUserRepository;
        this.inputValidator = inputValidator;
    }

    public UrlMapping createMapping(String longUrl) {

        if(!inputValidator.linkIsValid(longUrl)){
            throw new IllegalArgumentException("link is not valid");
        }
        
        UrlMapping urlMapping = new UrlMapping();
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        NormalUser owner =  normalUserRepository.findByUsername(user.getUsername()).orElseThrow(() -> 
                                                                new IllegalStateException("login first"));
        urlMapping.setOwner(owner);
        urlMapping.setLongUrl(longUrl);
        urlMapping.setShortUrl(shortCodeHandler());
        return urlMappingRepository.save(urlMapping);
    }

    public String shortCodeHandler() {//backend check for possible collision first
        String shortCode = shortCodeGenerator.generateShortCode(6);
        while(urlMappingRepository.existsById(shortCode)){
            shortCode = shortCodeGenerator.generateShortCode(6);
        }
        return shortCode;
    }

}
