package com.knoldus.codeinsights.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.knoldus.codeinsights.dto.FilteredResponse;
import com.knoldus.codeinsights.dto.Response;
import com.knoldus.codeinsights.dto.Value;
import com.knoldus.codeinsights.dto.Values;
import com.knoldus.codeinsights.util.APIConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static com.knoldus.codeinsights.util.APIConstants.*;
import static com.knoldus.codeinsights.util.Constants.COMMENTS;
import static com.knoldus.codeinsights.util.Constants.SLASH;

@Service
public class PullRequestService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();
    List<Values> valueDto = new ArrayList<>();

    private static final Logger log = LoggerFactory.getLogger(PullRequestService.class);

    public List<Value> getActivityLog(@RequestParam String workspace, @RequestParam String repoSlug) throws JsonProcessingException {
        if (workspace == null || repoSlug == null) {
            log.error("Url has some missing parameters");
        }
        List<Value> sampleResponse = new ArrayList<>();
        ResponseEntity<String> response
                = restTemplate.getForEntity(BASE_URL + workspace + repoSlug + URL_TO_APPEND, String.class);
        JsonNode values = objectMapper.readTree(response.getBody()).path("values");
        for (int i = 0; i < values.size(); i++) {
            String filteredResponse = values.get(i).toString();
            sampleResponse.add(objectMapper.readValue(filteredResponse, Value.class));
        }
        log.info("values {}", sampleResponse);
        return sampleResponse;
    }

    public Response getPRForParticularUser(@RequestParam String workspace, @RequestParam String repoSlug, @RequestParam String pullRequestId) throws JsonProcessingException {
        if (workspace == null || repoSlug == null || pullRequestId == null) {
            log.error("Url has some missing parameters");
        }
        ResponseEntity<String> response
                = restTemplate.getForEntity(BASE_URL + workspace + repoSlug + PR + pullRequestId, String.class);
        String filteredResponse = response.getBody();
        Response sampleResponse = objectMapper.readValue(filteredResponse, Response.class);
        log.info("value {}", sampleResponse);
        return sampleResponse;
    }

    public List<Response> getDefaultReviewers(String workspace, String repoSlug) throws JsonProcessingException {

        ArrayList<Response> finalResponse = new ArrayList<>();
        if (workspace == null || repoSlug == null) {
            log.error("Parameters missing");
        } else {
            ResponseEntity<String> response = restTemplate
                    .getForEntity(BASE_URL + workspace + FORWARD_SLASH + repoSlug + BASE_URL_APPEND, String.class);
            JsonNode values = objectMapper.readTree(response.getBody()).path("values");
            for (int i = 0; i < values.size(); i++) {
                String filteredResponse = values.get(i).toString();
                finalResponse.add(objectMapper.readValue(filteredResponse, Response.class));
            }
        }
        return finalResponse;
    }

    public JsonNode getPullRequestsContainCommit(String workspace, String repoSlug, String commitId) throws JsonProcessingException {

        JsonNode values = null;
        if (workspace == null || repoSlug == null || commitId == null) {
            log.error("Parameters missing");
        } else {
            ResponseEntity<String> response = restTemplate
                    .getForEntity(BASE_URL + workspace + FORWARD_SLASH + repoSlug + COMMIT + commitId + BASE_URL_TO_APPEND, String.class);
            values = objectMapper.readTree(response.getBody()).path("values");
        }
        return values;
    }

    private List<Values> filterValues(ResponseEntity<String> response) throws JsonProcessingException {
        JsonNode root = objectMapper.readTree(response.getBody());
        JsonNode values = root.path("values");
        for (int i = 0; i < values.size(); i++) {
            valueDto.add(objectMapper.readValue(values.get(i).toString(), Values.class));
        }
        return valueDto;
    }

    public List<Values> getAllPrForSpecificUser(String user) throws JsonProcessingException {
        if (user == null) {
            log.error("Please enter valid Username. Username is {}", user);
        } else {
            ResponseEntity<String> response
                    = restTemplate.getForEntity(APIConstants.SPECIFIC_USER_URL + user, String.class);
            valueDto = filterValues(response);
        }
        return valueDto;
    }

    public List<Values> getOpenPrForSpecificRepo(String user, String repo) throws JsonProcessingException {
        if (user == null || repo == null) {
            log.error("Please enter valid username or reposlug. Username is {}, Reposlug is {}.", user, repo);
        } else {
            ResponseEntity<String> response
                    = restTemplate.getForEntity(APIConstants.SPECIFIC_REPO_URL + user + FORWARD_SLASH + repo + APIConstants.SPECIFIC_REPO_URL_TO_APPEND, String.class);
            valueDto = filterValues(response);
        }
        return valueDto;
    }

    public List<Response> getActivityLogForParticularPR(String workspace, String repoSlug, Integer pullRequestId) {
        List<Response> valuesDto = new ArrayList<>();
        if (workspace == null || repoSlug == null || pullRequestId == null) {

            log.error("Invalid Parameters plz provide valid parameters");

        } else {
            String url = BASE_URL + workspace + SLASH + repoSlug + PULL_REQUESTS + SLASH + pullRequestId + SLASH + ACTIVITY;
            var response = restTemplate.getForEntity(url, String.class);
            try {
                JsonNode values = getJsonResponse(url);
                for (int i = 0; i < values.size(); i++) {
                    if (values.get(i) != null)
                        valuesDto.add(objectMapper.readValue(values.get(i).toString(), Response.class));
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

        }
        return valuesDto;

    }


    public List<FilteredResponse> getComments(String workspace, String repoSlug, Integer pullRequestId) {
        List<FilteredResponse> valuesDto = new ArrayList<>();
        if (workspace == null || repoSlug == null || pullRequestId == null) {

            log.error("Invalid Parameters");

        } else {
            String url = BASE_URL + workspace + SLASH + repoSlug + PULL_REQUESTS + SLASH + pullRequestId + SLASH + COMMENTS;
            try {
                JsonNode values = getJsonResponse(url);
                for (int i = 0; i < values.size(); i++) {
                    if (values.get(i) != null)
                        valuesDto.add(objectMapper.readValue(values.get(i).toString(), FilteredResponse.class));
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }


        }

        return valuesDto;

    }

    private JsonNode getJsonResponse(String url) throws JsonProcessingException {
        var response = restTemplate.getForEntity(url, String.class);
        JsonNode root = objectMapper.readTree(response.getBody());
        JsonNode values = root.path("values");
        if (values.size() == 0) {
            log.error("Array is null");
        }
        return values;
    }

}
