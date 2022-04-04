package com.knoldus.codeinsights.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.knoldus.codeinsights.dto.*;
import com.knoldus.codeinsights.service.PullRequestService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {PullRequestController.class})
@ExtendWith(SpringExtension.class)
@RunWith(SpringRunner.class)
@SpringBootTest()
@AutoConfigureMockMvc
class PullRequestControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PullRequestController pullRequestController;
    @MockBean
    private PullRequestService pullRequestService;

    private static final String WORKSPACE = "chiru_7cj";
    private static final String REPO_SLUG = "knolduslab";
    private static final String PULL_REQUEST_ID = "3";
    private static final String PR_USER_API = "/codeInsights/user";
    private static final String ACTIVITY_LOG_API = "/codeInsights/activityLog";
    private static final String SPECIFIC_REPO_API="/codeInsights/specificRepo";
    private static final String SPECIFIC_USER_API="/codeInsights/specificUser";
    private static final String DEFAULT_REVIEWER_API="/codeInsights/defaultReviewers";
    private static final String CONTAIN_COMMIT_ID_API="/codeInsights/commits";
    private static final String COMMIT_ID="12345";

    Values values = new Values("open",3,"2d"
            , new Author("Harsh Vardhan"),new Destination("2-3-99"
            , new Repository("Harsh vardhan")),new Source(new Repository("chiru_7cj/knolduslab"))
            , new Summary("README.md edited online with Bitbucket"));
    Response actualResponseForGetPRForParticularUser = new Response(new Author("Bhavya Garg")
            , new Destination("", new Repository("chiru_7cj/knolduslab")), "MERGED");
    Response response = new Response("Harsh vardhan", new Author("Harsh vardhan")
            , new Destination("2022-03-07T07:38:09.526182+00:00", new Repository("Harsh vardhan"))
            , "open");
    String json = "{\"id\":1,\"type\":\"pull_request\"}";
    ObjectMapper mapper = new ObjectMapper();
    JsonNode node = mapper.readTree(json);
    PullRequestControllerTest() throws JsonProcessingException {
    }

    @Test
        // Test case for success
    void testGetPullRequestsContainCommit() throws Exception {
        when(this.pullRequestService.getPullRequestsContainCommit((String) any(), (String) any(), (String) any()))
                .thenReturn(node);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(CONTAIN_COMMIT_ID_API);
        MockMvcBuilders.standaloneSetup(this.pullRequestController)
                .build()
                .perform(requestBuilder
                        .queryParam("workspace",WORKSPACE)
                        .queryParam("repoSlug",REPO_SLUG)
                        .queryParam("commitId",COMMIT_ID))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type").value("pull_request"));
    }

    @Test
        // Test case for failure
    void testGetPullRequestsContainCommit2() throws Exception {
        when(this.pullRequestService.getPullRequestsContainCommit((String) any(), (String) any(), (String) any()))
                .thenReturn(node);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(CONTAIN_COMMIT_ID_API);
        MockMvcBuilders.standaloneSetup(this.pullRequestController)
                .build()
                .perform(requestBuilder
                        .queryParam("workspace",WORKSPACE)
                        .queryParam("repoSlug",REPO_SLUG))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
        // Test case for success
    void testGetDefaultReviewers() throws Exception {

        when(this.pullRequestService.getDefaultReviewers((String) any(), (String) any()))
                .thenReturn(new ArrayList<>(Arrays.asList(response)));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(DEFAULT_REVIEWER_API);
        MockMvcBuilders.standaloneSetup(this.pullRequestController)
                .build()
                .perform(requestBuilder
                        .queryParam("workspace",WORKSPACE)
                        .queryParam("repoSlug",REPO_SLUG))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].state").value("open"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].author.nickname").value("Harsh vardhan"));
    }

    @Test
        // Test case for failure
    void testGetDefaultReviewers2() throws Exception {

        when(this.pullRequestService.getDefaultReviewers((String) any(), (String) any()))
                .thenReturn(new ArrayList<>(Arrays.asList(response)));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(DEFAULT_REVIEWER_API);
        MockMvcBuilders.standaloneSetup(this.pullRequestController)
                .build()
                .perform(requestBuilder
                        .queryParam("workspace",WORKSPACE))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void testGetPRActivityLogForPullRequestId() throws Exception {
        List<Value> response = getPullRequestId();
        when(this.pullRequestService.getActivityLog((String) any(), (String) any())).thenReturn(response);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(ACTIVITY_LOG_API);
        MockMvcBuilders.standaloneSetup(this.pullRequestController)
                .build()
                .perform(requestBuilder.queryParam("workspace", WORKSPACE)
                        .queryParam("repoSlug", REPO_SLUG))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].pull_request.id").value(3));
    }

    @Test
    public void failTestGetPRActivityLogForPullRequestId() throws Exception {
        List<Value> response = getPullRequestId();
        when(this.pullRequestService.getActivityLog((String) any(), (String) any())).thenReturn(response);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(ACTIVITY_LOG_API).queryParam("workspace", "chiru_7cj");
        MockMvcBuilders.standaloneSetup(this.pullRequestController)
                .build()
                .perform(requestBuilder.queryParam("workspace", WORKSPACE))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void testGetPRForParticularUser() throws Exception {
        when(this.pullRequestService.getPRForParticularUser((String) any(), (String) any(), (String) any()))
                .thenReturn(actualResponseForGetPRForParticularUser);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(PR_USER_API);
        MockMvcBuilders.standaloneSetup(this.pullRequestController)
                .build()
                .perform(requestBuilder.queryParam("workspace", WORKSPACE)
                        .queryParam("repoSlug", REPO_SLUG)
                        .queryParam("pullRequestId", PULL_REQUEST_ID))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.state").value("MERGED"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.destination.repository.full_name").value("chiru_7cj/knolduslab"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author.nickname").value("Bhavya Garg"));
    }

    @Test
    public void failTestGetPRForParticularUser() throws Exception {
        when(this.pullRequestService.getPRForParticularUser((String) any(), (String) any(), (String) any()))
                .thenReturn(actualResponseForGetPRForParticularUser);
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get(PR_USER_API);
        MockMvcBuilders.standaloneSetup(this.pullRequestController)
                .build()
                .perform(getResult.queryParam("workspace", WORKSPACE))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    private List<Value> getPullRequestId() {
        List<Value> response = new ArrayList<>();
        Value resp = new Value();

        PullRequest pullRequest = new PullRequest(3);
        resp.setPull_request(pullRequest);

        response.add(resp);
        return response;
    }
    @Test
    void testGetAllPrForSpecificUser() throws Exception {
        when(this.pullRequestService.getAllPrForSpecificUser((String) any())).thenReturn(new ArrayList<>(Arrays.asList(values)));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(SPECIFIC_USER_API);
        MockMvcBuilders.standaloneSetup(this.pullRequestController)
                .build()
                .perform(requestBuilder.queryParam("user",""))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].author.nickname").value("Harsh Vardhan"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].state").value("open"));
    }

    @Test
    void testGetAllPrForSpecificUser2() throws Exception {
        when(this.pullRequestService.getAllPrForSpecificUser((String) any())).thenReturn(new ArrayList<>(Arrays.asList(values)));
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get(SPECIFIC_USER_API);
        MockMvcBuilders.standaloneSetup(this.pullRequestController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void testGetOpenPrForSpecificRepo() throws Exception {
        when(this.pullRequestService.getOpenPrForSpecificRepo((String) any(), (String) any()))
                .thenReturn(new ArrayList<>(Arrays.asList(values)));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(SPECIFIC_REPO_API);
        MockMvcBuilders.standaloneSetup(this.pullRequestController)
                .build()
                .perform(requestBuilder.queryParam("user","chiru_7cj")
                        .queryParam("repo","knolduslab"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testGetOpenPrForSpecificRepo2() throws Exception {
        when(this.pullRequestService.getOpenPrForSpecificRepo((String) any(), (String) any()))
                .thenReturn(new ArrayList<>(Arrays.asList(values)));
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get(SPECIFIC_REPO_API);
        MockMvcBuilders.standaloneSetup(this.pullRequestController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}

