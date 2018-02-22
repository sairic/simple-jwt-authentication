package com.sairic.example.simplejwt.controller;

import java.util.List;
import java.util.Map;

public class LoggedInUser {

    public LoggedInUser() {
    }

    public LoggedInUser(String loggedInUser,  Map<String, Object> customClaim, List<String> roles) {
        this.loggedInUser = loggedInUser;
        this.customClaims = customClaim;
        this.roles = roles;
    }

    private String loggedInUser;
    private Map<String, Object> customClaims;
    private List<String> roles;

    public String getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(String loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public  Map<String, Object> getCustomClaims() {
        return customClaims;
    }

    public void setCustomClaims( Map<String, Object> customClaims) {
        this.customClaims = customClaims;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
