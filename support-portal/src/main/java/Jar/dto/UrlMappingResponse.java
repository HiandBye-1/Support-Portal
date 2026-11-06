package Jar.dto;

public class UrlMappingResponse {
    private final String longUrl;
    private final String shortUrl;
    
    public UrlMappingResponse(String longUrl, String shortUrl) {
        this.longUrl = longUrl;
        this.shortUrl = shortUrl;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }


}
