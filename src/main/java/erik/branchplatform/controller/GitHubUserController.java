package erik.branchplatform.controller;

import erik.branchplatform.model.UserResponse;
import erik.branchplatform.service.GitHubService;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GitHubUserController {

    private final GitHubService gitHubService;

    public GitHubUserController(GitHubService gitHubService) {
        this.gitHubService = gitHubService;
    }

    /**
     * Fetches GitHub user profile and repo details
     *
     * @param username - The GitHub username to fetch details for.
     * @return - UserResponse containing GitHub user and repository details, or a 404 if Not Found
     */
    @GetMapping("/github-users/{username}")
    public ResponseEntity<UserResponse> getUser(@PathVariable @NotNull String username) {
        UserResponse userResponse = gitHubService.getUser(username);
        if (userResponse == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userResponse);
    }
}
