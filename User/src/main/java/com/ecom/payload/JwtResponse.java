package com.ecom.payload;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
public class JwtResponse {
    private String token;
    private String refreshToken;
    private String username;
    private List<String> roles;

    // Default constructor
    public JwtResponse() {}

    // Constructor with parameters
    public JwtResponse(String token, String refreshToken, String username, List<String> roles) {
        this.token = token;
        this.refreshToken = refreshToken;
        this.username = username;
        this.roles = roles;
    }

    // Getters and Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    // Builder pattern for easy construction of JwtResponse
    public static JwtResponseBuilder builder() {
        return new JwtResponseBuilder();
    }

    public static class JwtResponseBuilder {

        private String token;
        private String refreshToken;
        private String username;
        private List<String> roles;

        public JwtResponseBuilder token(String token) {
            this.token = token;
            return this;
        }

        public JwtResponseBuilder refreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
            return this;
        }

        public JwtResponseBuilder username(String username) {
            this.username = username;
            return this;
        }

        public JwtResponseBuilder roles(List<String> roles) {
            this.roles = roles;
            return this;
        }

        public JwtResponse build() {
            return new JwtResponse(token, refreshToken, username, roles);
        }
    }
}
