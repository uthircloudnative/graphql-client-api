package com.learntech.graphqlclientapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.learntech.graphqlclientapi.model.*;
import reactor.core.publisher.Mono;

/**
 * UserSearchService
 *
 * @author Uthiraraj Saminathan
 */
public interface UserSearchService {

    Mono<SearchUser> searchUser(SearchInput searchInput) throws JsonProcessingException;

    User searchById(Integer id);

}
