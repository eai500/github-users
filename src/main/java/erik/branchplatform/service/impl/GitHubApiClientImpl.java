package erik.branchplatform.service.impl;

import erik.branchplatform.model.GitHubRepo;
import erik.branchplatform.model.GitHubUser;
import erik.branchplatform.service.GitHubApiClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class GitHubApiClientImpl implements GitHubApiClient {

    private static final String BASE_URL = "https://api.github.com";
    private static final String USER_PATH = "/users/{username}";
    private static final String USER_REPO_PATH = "/users/{username}/repos";

    private final WebClient webClient;

    public GitHubApiClientImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(BASE_URL).build();
    }

    public CompletableFuture<GitHubUser> getUser(String username) {
        return webClient.get()
                .uri(USER_PATH, username)
                .retrieve()
                .bodyToMono(GitHubUser.class)
                .toFuture();
    }

    public CompletableFuture<List<GitHubRepo>> getRepos(String username) {
        return webClient.get()
                .uri(USER_REPO_PATH, username)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<GitHubRepo>>() {
                }).toFuture();
    }
}
