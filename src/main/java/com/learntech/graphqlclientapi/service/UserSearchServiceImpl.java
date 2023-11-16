package com.learntech.graphqlclientapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learntech.graphqlclientapi.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;


import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * UserSearchServiceImpl
 *
 * @author Uthiraraj Saminathan
 */
@Service
public class UserSearchServiceImpl implements UserSearchService{

    private static final Logger logger = LoggerFactory.getLogger(UserSearchServiceImpl.class);

    private static final String USER_SEARCH_URL = "http://localhost:8080/graphql";

    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    public UserSearchServiceImpl(WebClient webClient,
                                 ObjectMapper objectMapper) {
        this.webClient    = webClient;
        this.objectMapper = objectMapper;
    }

    /**
     * @param searchInput
     * @return
     */
    @Override
    public Mono<SearchUser> searchUser(SearchInput searchInput) throws JsonProcessingException {

       MultiValueMap<String, String> header = new LinkedMultiValueMap<>();
       header.add("trace-id", UUID.randomUUID().toString());
       header.add("Content-Type", "application/json");

       String query = getQuery("/query/","userSearch.graphql");

       Map<String, Object> request = new HashMap<>();
       request.put("query",query);

        String variable = objectMapper.writeValueAsString(searchInput);
        request.put("searchInput",variable);

       return webClient.post()
                       .uri(USER_SEARCH_URL)
                       .headers(headers -> headers.addAll(header))
                       .body(BodyInserters.fromValue(request))
                       .retrieve()
                       .bodyToMono(UserResponse.class)
                       .flatMap(resp -> transform(resp));
    }

    private Mono<SearchUser> transform(UserResponse userResponse) {
        SearchUser searchUser = new SearchUser();
        searchUser.setUsers(userResponse.getData().getSearchUser().getUsers());
        return Mono.just(searchUser);
    }


    /**
     * @param id
     * @return
     */
    @Override
    public User searchById(Integer id) {
        return null;
    }

    private String getQuery(String filePath, String queryName)  {

        ClassPathResource classPathResource = new ClassPathResource(filePath+queryName);
        try {
           return new String(FileCopyUtils.copyToByteArray(classPathResource.getInputStream()), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
