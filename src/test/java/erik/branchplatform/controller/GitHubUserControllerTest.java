package erik.branchplatform.controller;

import erik.branchplatform.model.UserResponse;
import erik.branchplatform.service.GitHubService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@WebFluxTest(GitHubUserController.class)
class GitHubUserControllerTest {

    @Autowired
    private WebTestClient webClient;

    @MockitoBean
    GitHubService gitHubService;

    @Test
    public void getUser_WithServiceError_ReturnsInternalServerError() {
        var exception = new WebClientResponseException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "oops", null, null, null);
        when(gitHubService.getUser(any())).thenThrow(exception);

        webClient.get()
                .uri("/github-users/machoman")
                .exchange()
                .expectStatus().is5xxServerError();
    }

    @Test
    public void getUser_WithNullUserResponse_ReturnsNotFound() {

        when(gitHubService.getUser("steveaustin")).thenReturn(null);
        webClient.get()
                .uri("/github-users/steveaustin")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    public void getUser_WithValidUser_ReturnsOK() {
        when(gitHubService.getUser("razorramone")).thenReturn(new UserResponse("razor", "", "", "", "", "", null, null));
        webClient.get()
                .uri("/github-users/razorramone")
                .exchange()
                .expectStatus().isOk();
    }
}