package com.sairic.example.simplejwt.controller;

public class LoggedInUser {

    public LoggedInUser() {
    }

    public LoggedInUser(String loggedInUser, String customClaim) {
        this.loggedInUser = loggedInUser;
        this.customClaim = customClaim;
    }

    private String loggedInUser;
    private String customClaim;

    public String getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(String loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public String getCustomClaim() {
        return customClaim;
    }

    public void setCustomClaim(String customClaim) {
        this.customClaim = customClaim;
    }
}
