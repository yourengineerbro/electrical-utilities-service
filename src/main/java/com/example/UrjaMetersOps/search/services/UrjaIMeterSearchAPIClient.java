package com.example.UrjaMetersOps.search.services;

import com.example.UrjaMetersOps.search.config.UrjaAPIAuthService;
import com.example.UrjaMetersOps.search.config.UrjaAPISession;
import com.example.UrjaMetersOps.search.constants.UrjaAPIConstants;
import com.example.UrjaMetersOps.search.dtos.MeterSearchResponseDto;
import com.example.UrjaMetersOps.search.dtos.UrjaAPIMeterSearchResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Slf4j
@Service
public class UrjaIMeterSearchAPIClient implements IMeterSearchService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UrjaAPISession urjaAPISession;

    @Autowired
    private UrjaAPIAuthService urjaAPIAuthService;

    @Override
    public List<MeterSearchResponseDto> getMeters(String query, Integer page) {



        if (urjaAPISession.getCookie() == null || urjaAPISession.getCookie().isBlank()) {
           urjaAPIAuthService.loginAndGetCookie();
        }

        try {
            return execute(query, page);
        } catch (HttpClientErrorException.Unauthorized ex) {

            urjaAPIAuthService.loginAndGetCookie();
            return execute(query, page);
        }

    }


    private List<MeterSearchResponseDto> execute(String query, Integer page) {

        String url = UriComponentsBuilder.fromUriString(UrjaAPIConstants.BASE_URL)
                .path("/portal/meters/search")
                .queryParam("q", query)
                .queryParam("page", page)
                .toUriString();


        HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie", urjaAPISession.getCookie());

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<UrjaAPIMeterSearchResponseDto> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                UrjaAPIMeterSearchResponseDto.class
        );

        if (response.getBody() != null && response.getBody().getData() != null) {
            return response.getBody().getData();
        }

        return List.of();
    }
}
