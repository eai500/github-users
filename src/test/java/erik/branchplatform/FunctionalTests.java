package erik.branchplatform;

import erik.branchplatform.model.UserResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FunctionalTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getGitHubUser_WithValidUser_ReturnsDataAndOkResponse() {
        ResponseEntity<UserResponse> response = restTemplate.getForEntity("/github-users/octocat", UserResponse.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("octocat", response.getBody().username());
    }

    @Test
    public void getGitHubUser_WithNonExistentUser_ReturnsNotFoundResponse() {
        ResponseEntity<UserResponse> response =
                restTemplate.getForEntity("/github-users/surelynoonewilleverhavethisnameright30303", UserResponse.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }
}
