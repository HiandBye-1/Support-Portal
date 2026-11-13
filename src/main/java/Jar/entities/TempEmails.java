package Jar.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;

import java.time.LocalDateTime;

@Entity 
@Table(name = "temp_emails")
public class TempEmails {
    
    @OneToOne
    @MapsId 
    @JoinColumn(name = "user_id", nullable = false)
    private NormalUser user;

    @Column(name = "email", nullable = false)
    private String email;

    @Id 
    private Long userId;

    @Column(name = "expires", nullable = false)
    private LocalDateTime expires;

    @Column(name = "attempts", nullable = false)
    private int attempts = 0;

    @Column(name = "code_hashed", nullable = false)
    private String codeHashed;


    public NormalUser getUser() {
        return user;
    }  

    public String getEmail() {
        return email;
    }

    public LocalDateTime getExpires() {
        return expires;
    }

    public int getAttempts() {
        return attempts;
    }

    public String getCodeHashed() {
        return codeHashed;
    }

    public void setExpires(LocalDateTime expires) {
        this.expires = expires;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    public Long getUserId() {
        return userId;
    }


    public void setUser(NormalUser user) {
        this.user = user;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCodeHashed(String codeHashed) {
        this.codeHashed = codeHashed;
    }





}
