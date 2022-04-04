package com.knoldus.codeinsights.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Repository {

    private String full_name;

    public Repository(String full_name) {
        this.full_name = full_name;
    }

    public Repository() {
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    @Override
    public String toString() {
        return "Repository{" +
                "full_name='" + full_name + '\'' +
                '}';
    }



}