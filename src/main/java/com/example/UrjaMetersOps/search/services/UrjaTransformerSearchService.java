package com.example.UrjaMetersOps.search.services;

import com.example.UrjaMetersOps.search.config.UrjaAPIAuthService;
import com.example.UrjaMetersOps.search.config.UrjaAPISession;
import com.example.UrjaMetersOps.search.constants.UrjaAPIConstants;
import com.example.UrjaMetersOps.search.dtos.TransformerResponseDto;
import com.example.UrjaMetersOps.search.dtos.UrjaAPITransformerSearchResponseDto;
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

@Service
@Slf4j
public class UrjaTransformerSearchService implements ITransformerSearchService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UrjaAPISession urjaAPISession;

    @Autowired
    private UrjaAPIAuthService urjaAPIAuthService;

    @Override
    public List<TransformerResponseDto> listTransformers(int page) {

        if (urjaAPISession.getCookie() == null || urjaAPISession.getCookie().isBlank()) {
            urjaAPIAuthService.loginAndGetCookie();
        }

        try {
            return execute(page);
        } catch (HttpClientErrorException.Unauthorized ex) {

            urjaAPIAuthService.loginAndGetCookie();
            return execute(page);
        }
    }

    private List<TransformerResponseDto> execute(int page) {


        String url = UriComponentsBuilder.fromUriString(UrjaAPIConstants.BASE_URL)
                .path("/portal/dts")
                .queryParam("page", page)
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie", urjaAPISession.getCookie());


        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<UrjaAPITransformerSearchResponseDto> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                UrjaAPITransformerSearchResponseDto.class
        );

        if (response.getBody() != null && response.getBody().getData() != null) {
            return response.getBody().getData();
        }

        return List.of();
    }
}
