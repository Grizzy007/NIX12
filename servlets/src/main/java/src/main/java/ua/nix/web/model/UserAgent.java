package ua.nix.web.model;

import java.time.LocalDateTime;

public class UserAgent {
    private String ipAdress;
    private String userAgent;
    private LocalDateTime time;

    public UserAgent() {
    }

    public UserAgent(String ipAdress, String userAgent, LocalDateTime time) {
        this.ipAdress = ipAdress;
        this.userAgent = userAgent;
        this.time = time;
    }

    public String getIpAdress() {
        return ipAdress;
    }

    public void setIpAdress(String ipAdress) {
        this.ipAdress = ipAdress;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
