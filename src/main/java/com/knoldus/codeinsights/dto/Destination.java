package com.knoldus.codeinsights.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Destination {
    private String created_on;
    private Repository repository;

    public String getCreated_on() {
        return created_on;
    }

    public void setCreated_on(String created_on) {
        this.created_on = created_on;
    }

    public Repository getRepository() {
        return repository;
    }

    public void setRepository(Repository repository) {
        this.repository = repository;
    }

    public Destination(String created_on, Repository repository) {
        this.created_on = created_on;
        this.repository = repository;
    }
    public Destination() {
    }
}
