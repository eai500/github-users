package erik.branchplatform.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.ZonedDateTime;


public record GitHubUser(
        @JsonProperty("login") String login,
        @JsonProperty("id") String id,
        @JsonProperty("avatar_url") String avatarUrl,
        @JsonProperty("url") String url,
        @JsonProperty("name") String name,
        @JsonProperty("location") String location,
        @JsonProperty("email") String email,
        @JsonProperty("created_at") ZonedDateTime createdAt) {
}