package erik.branchplatform.service;

import erik.branchplatform.model.GitHubRepo;
import erik.branchplatform.model.GitHubUser;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface GitHubApiClient {
    CompletableFuture<GitHubUser> getUser(String username);

    CompletableFuture<List<GitHubRepo>> getRepos(String username);
}
