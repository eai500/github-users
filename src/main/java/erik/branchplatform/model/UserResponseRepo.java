package erik.branchplatform.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserResponseRepo(
        @JsonProperty("name") String name,
        @JsonProperty("url") String url) {
}
