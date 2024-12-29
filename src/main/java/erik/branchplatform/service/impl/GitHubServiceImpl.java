package erik.branchplatform.service.impl;

import erik.branchplatform.model.GitHubRepo;
import erik.branchplatform.model.GitHubUser;
import erik.branchplatform.model.UserResponse;
import erik.branchplatform.model.UserResponseRepo;
import erik.branchplatform.service.GitHubApiClient;
import erik.branchplatform.service.GitHubService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;
import java.util.function.Function;

@Service
public class GitHubServiceImpl implements GitHubService {

    private static final Function<Throwable, UserResponse> NOT_FOUND_HANDLER = throwable -> {
        if (throwable.getCause() instanceof WebClientResponseException &&
                ((WebClientResponseException) throwable.getCause()).getStatusCode() == HttpStatus.NOT_FOUND) {
            return null;
        }
        throw new RuntimeException(throwable);
    };

    private final GitHubApiClient apiClient;

    public GitHubServiceImpl(GitHubApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public UserResponse getUser(String username) {
        var userFuture = apiClient.getUser(username);
        var repoFuture = apiClient.getRepos(username);

        return userFuture.thenCombine(repoFuture, this::mapResponse).exceptionally(NOT_FOUND_HANDLER).join();
    }

    private UserResponse mapResponse(GitHubUser user, List<GitHubRepo> githubRepos) {
        List<UserResponseRepo> responseRepos = githubRepos.stream()
                .map(gitHubRepo -> new UserResponseRepo(gitHubRepo.name(), gitHubRepo.url()))
                .toList();
        return new UserResponse(
                user.login(),
                user.name(),
                user.avatarUrl(),
                user.location(),
                user.email(),
                user.url(),
                user.createdAt(),
                responseRepos);
    }
}
