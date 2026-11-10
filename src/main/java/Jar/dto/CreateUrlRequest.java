package Jar.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


public class CreateUrlRequest {

    private final String longUrl;

    @JsonCreator
    public CreateUrlRequest(@JsonProperty("longUrl") String longUrl) {
        this.longUrl = longUrl;
    }

    public String getLongUrl() {
        return longUrl;
    }

}
