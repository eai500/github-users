package erik.branchplatform.service;

import erik.branchplatform.model.UserResponse;

public interface GitHubService {

    /**
     * Compiles the user's profile and repo information
     *
     * @param username - The username of the GitHub user to fetch
     * @return - Returns the UserResponse containing GitHub user profile and repo details
     */
    UserResponse getUser(String username);
}
