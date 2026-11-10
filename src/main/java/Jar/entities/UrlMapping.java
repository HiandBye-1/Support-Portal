package Jar.entities;

import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity 
@Table(name = "urlShortener")
public class UrlMapping {

    @Id 
    @Column(name = "short_url")
    private String shortUrl;

    @Column(name = "long_url")
    private String longUrl;

    @ManyToOne 
    @JoinColumn(name = "user_id", nullable = false)
    private NormalUser owner;
    
    public String getShortUrl(){
        return shortUrl;
    }

    public void setShortUrl(String short_url){
        this.shortUrl = short_url;
    }

    public NormalUser getOwner(){
        return owner;
    }

    public void setOwner(NormalUser owner){
        this.owner = owner;
    }


    public String getLongUrl(){
        return longUrl;
    }

    public void setLongUrl(String long_url){
        this.longUrl = long_url;
    }
}
