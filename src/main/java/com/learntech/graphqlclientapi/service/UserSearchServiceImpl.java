package com.learntech.graphqlclientapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learntech.graphqlclientapi.model.SearchInput;
import com.learntech.graphqlclientapi.model.User;
import com.learntech.graphqlclientapi.model.UserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * UserSearchServiceImpl
 *
 * @author Uthiraraj Saminathan
 */
@Service
@Slf4j
public class UserSearchServiceImpl implements UserSearchService{
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
    public Mono<List<User>> searchUsers(SearchInput searchInput) throws JsonProcessingException {

       MultiValueMap<String, String> header = new LinkedMultiValueMap<>();
       header.add("trace-id", UUID.randomUUID().toString());
       header.add("Content-Type", "application/json");

       String query = getQuery("/query/","userSearch.graphql");

       Map<String, Object> request = new HashMap<>();
       request.put("query",query);

        //String searchInputReq = objectMapper.writeValueAsString(searchInput);
        Map<String, Object> variableMap = new HashMap<>();
        variableMap.put("searchInput", Map.of("firstName",searchInput.getFirstName(),"lastName",searchInput.getLastName()));

        request.put("variables",variableMap);

        return webClient.post()
                       .uri(USER_SEARCH_URL)
                       .headers(headers -> headers.addAll(header))
                       .body(BodyInserters.fromValue(request))
                       .retrieve()
                       .bodyToMono(UserResponse.class)
                       .flatMap(resp -> transform(resp));
    }

    private Mono<List<User>> transform(UserResponse userResponse) {
        return Mono.just(userResponse.getData().getUsers());
    }


    /**
     * @param id
     * @return
     */
    @Override
    public Mono<User> searchById(Integer id) {
        log.info("searchById() Starts");
        MultiValueMap<String, String> header = new LinkedMultiValueMap<>();
        header.add("trace-id", UUID.randomUUID().toString());
        header.add("Content-Type", "application/json");

        String query = getQuery("/query/","searchByUserId.graphql");

        Map<String, Object> request = new HashMap<>();
        request.put("query",query);

        Map<String, Object> variablesMap = new HashMap<>();
        variablesMap.put("id", id);

        request.put("variables", variablesMap);

        return webClient.post()
                .uri(USER_SEARCH_URL)
                .headers(headers -> headers.addAll(header))
                .body(BodyInserters.fromValue(request))
                .retrieve()
                .bodyToMono(UserResponse.class)
                .flatMap(resp -> getUserData(resp));
    }

    private Mono<User> getUserData(UserResponse userResponse){
       return Mono.just(userResponse.getData().getUser());
    }

    private String getQuery(String filePath, String queryName)  {
        ClassPathResource classPathResource = new ClassPathResource(filePath+queryName);
        try {
           return new String(FileCopyUtils.copyToByteArray(classPathResource.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
