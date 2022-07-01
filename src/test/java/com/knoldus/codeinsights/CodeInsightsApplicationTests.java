package com.knoldus.codeinsights;

import com.knoldus.codeinsights.dto.*;
import com.knoldus.codeinsights.service.PullRequestService;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest()
@AutoConfigureMockMvc
public class CodeInsightsApplicationTests {

    private static final String WORKSPACE = "chiru_7cj";
    private static final String REPO_SLUG = "knolduslab";
    private static final String PULL_REQUEST_ID = "3";


    @MockBean
    private PullRequestService pullRequestService;

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void testPRActivityLogForPullRequestId() throws Exception {
        List<Response> response = getActivityLogDetails();
        when(pullRequestService.getActivityLogForParticularPR(any(), any(), any())).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/codeInsights/logs")
                        .queryParam("workspace", WORKSPACE)
                        .queryParam("repoSlug", REPO_SLUG)
                        .queryParam("pullRequestId", PULL_REQUEST_ID))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].pull_request.id").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].pull_request.title").value("README.md edited online with Bitbucket"))
                .andReturn().getResponse();

    }

    @Test
    public void failPRActivityLogForPullRequestId() throws Exception {
        List<Response> response = getActivityLogDetails();
        when(pullRequestService.getActivityLogForParticularPR(any(), any(), any())).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/codeInsights/logs")
                        .queryParam("repoSlug", REPO_SLUG)
                        .queryParam("pullRequestId", PULL_REQUEST_ID))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].pull_request.id").doesNotHaveJsonPath())
                .andReturn().getResponse();

    }

    @Test
    public void testCommentsForPR() throws Exception {
        List<FilteredResponse> response = getCommentDetails();
        when(pullRequestService.getComments(any(), any(), any())).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/codeInsights/comments")
                        .queryParam("workspace", WORKSPACE)
                        .queryParam("repoSlug", REPO_SLUG)
                        .queryParam("pullRequestId", PULL_REQUEST_ID))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].user.display_name").value("Harsh vardhan"))
                .andReturn().getResponse();

    }

    @Test
    public void failTestCommentsForPR() throws Exception {
        List<FilteredResponse> response = getCommentDetails();
        when(pullRequestService.getComments(any(), any(), any())).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/codeInsights/comments")
                        .queryParam("repoSlug", WORKSPACE)
                        .queryParam("pullRequestId", PULL_REQUEST_ID))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].user.display_name").doesNotHaveJsonPath())

                .andReturn().getResponse();

    }


    private List<Response> getActivityLogDetails() {
        List<Response> response = new ArrayList<>();
        Response resp = new Response();
        Author author = new Author("Harsh vardhan");
        PullRequest pullRequest = new PullRequest(3, "README.md edited online with Bitbucket");
        resp.setPull_request(pullRequest);
        resp.setAuthor(author);
        response.add(resp);
        return response;
    }


    private List<FilteredResponse> getCommentDetails() {
        List<FilteredResponse> response = new ArrayList<>();
        FilteredResponse resp = new FilteredResponse();
        User user = new User("Harsh vardhan");
        PullRequest pullRequest = new PullRequest(3, "README.md edited online with Bitbucket");
        resp.setUser(user);
        response.add(resp);
        return response;
    }


}

