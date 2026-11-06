package com.example.UrjaMetersOps.search.config;

import com.example.UrjaMetersOps.search.constants.UrjaAPIConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class UrjaAPIAuthService implements IAPIAuthService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UrjaAPISession urjaAPISession;

    @Autowired
    private UrjaAPIAuthCredentials urjaAPIAuthCredentials;

    @Override
    public String loginAndGetCookie() {

        String loginUrl = UrjaAPIConstants.BASE_URL + "/login";

        MultiValueMap<String, String> formBody = new LinkedMultiValueMap<>();
        formBody.add("email", urjaAPIAuthCredentials.getEmail());
        formBody.add("password", urjaAPIAuthCredentials.getPassword());

        HttpHeaders headers = new HttpHeaders();
        headers.set("origin", "https://urja-ops.flockenergy.tech");

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(formBody, headers);

 try {
     ResponseEntity<Void> response = restTemplate.postForEntity(
             loginUrl,
             requestEntity,
             Void.class
     );

     String setCookieHeader = response.getHeaders().getFirst(HttpHeaders.SET_COOKIE);

     if(setCookieHeader != null) {
         urjaAPISession.setCookie(setCookieHeader);
         return setCookieHeader;
     }
 } catch (HttpClientErrorException e) {
     log.error("Status: {}", e.getStatusCode());
     log.error("Response: {}", e.getResponseBodyAsString());
 }

        throw new RuntimeException("Login failed: No SET_COOKIE header returned from server.");
    }
}
