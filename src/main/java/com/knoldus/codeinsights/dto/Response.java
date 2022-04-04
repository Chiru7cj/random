package com.knoldus.codeinsights.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Response {
    private String nickname;
    private Author author;
    private Destination destination;
    private String state;
    private PullRequest pull_request;
    private Update update;
    private Approval approval;
    private Comment comment;


    public Response(String nickname, Author author, Destination destination, String state, PullRequest pull_request, Update update, Approval approval, Comment comment) {
        this.nickname = nickname;
        this.author = author;
        this.destination = destination;
        this.state = state;
        this.pull_request = pull_request;
        this.update = update;
        this.approval = approval;
        this.comment = comment;
    }

    public Response(Author author, Destination destination, String state) {
        this.author = author;
        this.destination = destination;
        this.state = state;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "Response{" +
                "nickname='" + nickname + '\'' +
                '}';
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Response(String nickname, Author author, Destination destination, String state) {
        this.nickname = nickname;
        this.author = author;
        this.destination = destination;
        this.state = state;
    }

    public Response() {
    }

    public Response(String nickname, PullRequest pull_request, Update update, Approval approval, Comment comment) {
        this.nickname = nickname;
        this.pull_request = pull_request;
        this.update = update;
        this.approval = approval;
        this.comment = comment;
    }


    public PullRequest getPull_request() {
        return pull_request;
    }

    public void setPull_request(PullRequest pull_request) {
        this.pull_request = pull_request;
    }

    public Update getUpdate() {
        return update;
    }

    public void setUpdate(Update update) {
        this.update = update;
    }

    public Approval getApproval() {
        return approval;
    }

    public void setApproval(Approval approval) {
        this.approval = approval;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }
}



