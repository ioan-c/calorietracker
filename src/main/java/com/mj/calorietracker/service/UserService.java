package com.mj.calorietracker.service;

import com.mj.calorietracker.dto.User;
import com.mj.calorietracker.exception.ResourceNotFoundException;
import com.mj.calorietracker.exception.ServiceUnreachableException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import static com.mj.calorietracker.enums.ExceptionMessages.USER_MANAGEMENT_NOT_REACHABLE;
import static com.mj.calorietracker.enums.ExceptionMessages.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UserService {

    @Value( "${external-services.user-management-uri}" )
    private String userManagementBaseUri;

    public final User findByUsername(final String username) {
        return findUser(username).getBody();
    }

    private ResponseEntity<User> findUser(String username) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            return restTemplate.exchange(
                    "%s/find/%s".formatted(userManagementBaseUri, username),
                    HttpMethod.GET,
                    getHttpRequestEntity(),
                    User.class
            );
        } catch (HttpClientErrorException | ResourceAccessException exception) {
            throw handleException(exception);
        }
    }

    private static RuntimeException handleException(RuntimeException exception) {
        if (exception instanceof HttpClientErrorException && ((HttpClientErrorException) exception).getStatusCode().equals(HttpStatus.NOT_FOUND)) {
            return new ResourceNotFoundException(USER_NOT_FOUND.getText());
        }
        return new ServiceUnreachableException(USER_MANAGEMENT_NOT_REACHABLE.getText());
    }

    private HttpEntity<Object> getHttpRequestEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Accept", "*/*");
        return new HttpEntity<>(null, headers);
    }
}
