package erik.branchplatform.service.impl;

import erik.branchplatform.model.GitHubRepo;
import erik.branchplatform.model.GitHubUser;
import erik.branchplatform.model.UserResponse;
import erik.branchplatform.service.GitHubApiClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GitHubServiceImplTest {

    private GitHubServiceImpl gitHubService;
    private GitHubApiClient mockApiClient;

    @BeforeEach
    void setUp() {
        mockApiClient = mock(GitHubApiClient.class);
        gitHubService = new GitHubServiceImpl(mockApiClient);
    }

    @Test
    public void getUser_WithValidUsername_ReturnsUser() {
        GitHubUser user = new GitHubUser("erik", "123", null, null, null, null, null, null);
        GitHubRepo repo = new GitHubRepo("cool-repo", "https://github.com/cool-repo");
        when(mockApiClient.getUser("erik")).thenReturn(CompletableFuture.supplyAsync(() -> user));
        when(mockApiClient.getRepos("erik")).thenReturn(CompletableFuture.supplyAsync(() -> List.of(repo)));

        UserResponse actualResponse = gitHubService.getUser("erik");

        assertNotNull(actualResponse);
        assertEquals("erik", actualResponse.username());
        assertEquals(1, actualResponse.repos().size());
        assertEquals("cool-repo", actualResponse.repos().getFirst().name());
    }

    @Test
    public void getUser_WithGitHubApiUserNotFound_ReturnsNull() {
        var exception = new WebClientResponseException(HttpStatus.NOT_FOUND, "", null, null, null, null);

        GitHubRepo repo = new GitHubRepo("cool-repo", "https://github.com/cool-repo");
        when(mockApiClient.getUser("erik")).thenReturn(CompletableFuture.supplyAsync(() -> {
            throw exception;
        }));
        when(mockApiClient.getRepos("erik")).thenReturn(CompletableFuture.supplyAsync(() -> List.of(repo)));

        UserResponse actualResponse = gitHubService.getUser("erik");

        assertNull(actualResponse);
    }

    @Test
    public void getUser_WithException_ThrowsException() {
        var exception = new WebClientResponseException(HttpStatus.INTERNAL_SERVER_ERROR, "", null, null, null, null);

        GitHubRepo repo = new GitHubRepo("cool-repo", "https://github.com/cool-repo");
        when(mockApiClient.getUser("erik")).thenReturn(CompletableFuture.supplyAsync(() -> {
            throw exception;
        }));
        when(mockApiClient.getRepos("erik")).thenReturn(CompletableFuture.supplyAsync(() -> List.of(repo)));

        assertThrows(Exception.class, () -> gitHubService.getUser("erik"));
    }
}