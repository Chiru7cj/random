package com.knoldus.codeinsights.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Update {
    private Date date;
    private Author authorDto;
    private String state;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Author getAuthorDto() {
        return authorDto;
    }

    public void setAuthorDto(Author authorDto) {
        this.authorDto = authorDto;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Update{" +
                "date=" + date +
                ", authorDto=" + authorDto +
                ", state='" + state + '\'' +
                '}';
    }
}
