package com.knoldus.codeinsights.dto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Values {

    private String state;
    private int id;
    private  String updated_on;
    Author author;
    Destination destination;
    Source source;
    Summary summary;

    public Values(String state, int id, String updated_on, Author author, Destination destination, Source source,Summary summary) {
        this.state = state;
        this.id = id;
        this.updated_on = updated_on;
        this.author = author;
        this.destination = destination;
        this.source = source;
        this.summary = summary;
    }

    public Values() {
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUpdated_on() {
        return updated_on;
    }

    public void setUpdated_on(String updated_on) {
        this.updated_on = updated_on;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public Summary getSummary() {
        return summary;
    }

    public void setSummary(Summary summary) {
        this.summary = summary;
    }

}
