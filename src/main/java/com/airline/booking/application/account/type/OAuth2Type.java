package com.airline.booking.application.account.type;

public enum OAuth2Type {
    GOOGLE("GOOGLE");

    private final String type;
    OAuth2Type(String type) {
        this.type = type;
    }
    public String getValue(){
        return this.type;
    }
}
