package com.knoldus.codeinsights.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    private String display_name;

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public User(String display_name) {
        this.display_name = display_name;
    }

    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "display_name='" + display_name + '\'' +
                '}';
    }
}