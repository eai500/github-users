package erik.branchplatform.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.ZonedDateTime;
import java.util.List;

public record UserResponse(
        @JsonProperty("user_name") String username,
        @JsonProperty("display_name") String displayName,
        @JsonProperty("avatar") String avatarUrl,
        @JsonProperty("geo_location") String geoLocation,
        @JsonProperty("email") String email,
        @JsonProperty("url") String url,
        @JsonProperty("created_at") ZonedDateTime createdAt,
        @JsonProperty("repos") List<UserResponseRepo> repos) {
}
