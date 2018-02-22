package com.sairic.example.simplejwt.config.security;

public enum SecurityEnum {

    UserNameHeader("X-VC-Auth-Username"),
    PasswordHeader("X-VC-Auth-Password"),
    AuthHeader("Authorization"),
    Bearer("Bearer "),
    Scopes("scopes"),
    CustomClaim1("CustomClaim1"),
    CustomClaim2("CustomClaim2");

    private String securityValue;

    SecurityEnum(String securityValue) {
        this.securityValue = securityValue;
    }

    public String val() {
        return securityValue;
    }
}
