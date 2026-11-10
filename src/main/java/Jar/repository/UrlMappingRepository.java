package Jar.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import Jar.entities.UrlMapping;
import java.util.Optional;

public interface UrlMappingRepository extends JpaRepository<UrlMapping, String> {
    public Optional<UrlMapping> findByShortUrl(String shortUrl);
}
