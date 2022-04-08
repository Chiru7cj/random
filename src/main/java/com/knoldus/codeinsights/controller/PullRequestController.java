package com.knoldus.codeinsights.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.knoldus.codeinsights.dto.FilteredResponse;
import com.knoldus.codeinsights.dto.Response;
import com.knoldus.codeinsights.dto.Value;
import com.knoldus.codeinsights.dto.Values;
import com.knoldus.codeinsights.service.PullRequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/codeInsights")
public class PullRequestController {

    private static final Logger log = LoggerFactory.getLogger(PullRequestController.class);

    @Autowired
    private PullRequestService pullRequestService;
    List<Values> actualResponse;

    //List pull requests for a specific user
    @GetMapping(value = "/specificUser")
    private ResponseEntity<List<Values>> getAllPrForSpecificUser(@RequestParam(required = true) String user) throws JsonProcessingException {
        actualResponse = pullRequestService.getAllPrForSpecificUser(user);
        return new ResponseEntity<>(actualResponse, new HttpHeaders(), HttpStatus.OK);
    }

    //List pull requests for a specific repo
    @GetMapping(value = "/specificRepo")
    private ResponseEntity<List<Values>> getOpenPrForSpecificRepo(@RequestParam(required = true) String user, @RequestParam(required = true) String repo) throws JsonProcessingException {
        actualResponse = pullRequestService.getOpenPrForSpecificRepo(user, repo);
        return new ResponseEntity<>(actualResponse, new HttpHeaders(), HttpStatus.OK);
    }

    // List pull requests that contain a commit
    @GetMapping(value = "/commits")
    public ResponseEntity<String> getPullRequestsContainCommit(@RequestParam(required = true) String workspace, @RequestParam(required = true) String repoSlug, @RequestParam(required = true) String commitId) throws JsonProcessingException {
        JsonNode response = pullRequestService.getPullRequestsContainCommit(workspace, repoSlug, commitId);
        return new ResponseEntity(response, HttpStatus.OK);
    }

    //  List of default reviewers
    @GetMapping(value = "/defaultReviewers")
    public ResponseEntity<List<Response>> getDefaultReviewers(@RequestParam(required = false) String workspace, @RequestParam(required = true) String repoSlug) throws JsonProcessingException {
        List response = pullRequestService.getDefaultReviewers(workspace, repoSlug);
        return new ResponseEntity(response, HttpStatus.OK);
    }

    //This Api will provide the activity log of pull requests raised by a particular pull request id

    @GetMapping(value = "/logs")
    public ResponseEntity<List<Response>> getPRActivityLogForPullRequestId(@RequestParam(required = true) String workspace, @RequestParam(required = true) String repoSlug, @RequestParam(required = true) Integer pullRequestId) {
        log.info("Inside getPRActivityLogForPullRequestId method will provide activity log for specific PR id");
        List<Response> response = pullRequestService.getActivityLogForParticularPR(workspace, repoSlug, pullRequestId);
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);

    }

    //This Api will provide the Comments of pull requests raised by a particular pull request id

    @GetMapping(value = "/comments")
    public ResponseEntity<List<FilteredResponse>> getCommentsForPR(@RequestParam(required = true) String workspace, @RequestParam(required = true) String repoSlug, @RequestParam(required = true) int pullRequestId) {
        log.info("inside method getCommentsForPR we get the comments for PR ");
        List<FilteredResponse> response = pullRequestService.getComments(workspace, repoSlug, pullRequestId);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    //Get a pull request for a particular user
    @GetMapping(value = "/user")
    public ResponseEntity<Response> getPRForParticularUser(@RequestParam(required = true) String workspace, @RequestParam(required = true) String repoSlug, @RequestParam(required = true) String pullRequestId) throws JsonProcessingException {
        Response resp = pullRequestService.getPRForParticularUser(workspace, repoSlug, pullRequestId);
        return new ResponseEntity<Response>(resp, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping(value = "/activityLog")
    public ResponseEntity<List<Value>> getPRActivityLogForPullRequestId(@RequestParam(required = true) String workspace, @RequestParam(required = true) String repoSlug) throws JsonProcessingException {
        List<Value> resp = pullRequestService.getActivityLog(workspace, repoSlug);
        List<Value> responseList = new ArrayList<>();
        for (int i = 0; i < resp.size(); i++)
            responseList.add(resp.get(i));
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }
}

