package com.knoldus.codeinsights.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FilteredResponse {
    private Date updated_on;
    private Date created_on;
    private User user;
    private Content content;

    public FilteredResponse() {
    }

    public FilteredResponse(Date updated_on, Date created_on, User user, Content content) {
        this.updated_on = updated_on;
        this.created_on = created_on;
        this.user = user;
        this.content = content;
    }

    public Date getUpdated_on() {
        return updated_on;
    }

    public void setUpdated_on(Date updated_on) {
        this.updated_on = updated_on;
    }

    public Date getCreated_on() {
        return created_on;
    }

    public void setCreated_on(Date created_on) {
        this.created_on = created_on;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "FilteredResponse{" +
                "updated_on=" + updated_on +
                ", created_on=" + created_on +
                ", user=" + user +
                ", content=" + content +
                '}';
    }
}
