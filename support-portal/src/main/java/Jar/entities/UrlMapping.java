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
    private String short_url;

    @Column(name = "long_url")
    private String long_url;

    @ManyToOne 
    @JoinColumn(name = "user_id", nullable = false)
    private NormalUser owner;
    
    public String getShortUrl(){
        return short_url;
    }

    public void setShortUrl(String short_url){
        this.short_url = short_url;
    }

    public NormalUser getOwner(){
        return owner;
    }

    public void setOwner(NormalUser owner){
        this.owner = owner;
    }


    public String getLongUrl(){
        return long_url;
    }

    public void setLongUrl(String long_url){
        this.long_url = long_url;
    }
}
