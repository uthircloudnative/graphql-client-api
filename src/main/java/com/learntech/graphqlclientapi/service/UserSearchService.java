package com.learntech.graphqlclientapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.learntech.graphqlclientapi.model.*;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * UserSearchService
 *
 * @author Uthiraraj Saminathan
 */
public interface UserSearchService {

    Mono<List<User>> searchUsers(SearchInput searchInput) throws JsonProcessingException;

    Mono<User> searchById(Integer id);

}
