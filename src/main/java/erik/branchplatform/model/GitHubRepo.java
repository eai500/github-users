package erik.branchplatform.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GitHubRepo(
        @JsonProperty("full_name") String name,
        @JsonProperty("html_url") String url) {
}