package com.knoldus.codeinsights.dto;

public class Request {

    String workspace;
    String repoSlug;
    int pullRequestId;

    public Request(String workspace, String repoSlug, int pullRequestId) {
        this.workspace = workspace;
        this.repoSlug = repoSlug;
        this.pullRequestId = pullRequestId;
    }

    public Request() {
    }

    public String getWorkspace() {
        return workspace;
    }

    public void setWorkspace(String workspace) {
        this.workspace = workspace;
    }

    public String getRepoSlug() {
        return repoSlug;
    }

    public void setRepoSlug(String repoSlug) {
        this.repoSlug = repoSlug;
    }

    public int getPullRequestId() {
        return pullRequestId;
    }

    public void setPullRequestId(int pullRequestId) {
        this.pullRequestId = pullRequestId;
    }
}
