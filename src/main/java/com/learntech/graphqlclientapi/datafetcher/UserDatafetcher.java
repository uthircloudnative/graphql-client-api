package com.learntech.graphqlclientapi.datafetcher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.learntech.graphqlclientapi.model.*;
import com.learntech.graphqlclientapi.service.UserSearchService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * UserDatafetcher
 *
 * @author Uthiraraj Saminathan
 */
@DgsComponent
@Slf4j
public class UserDatafetcher {

    private final UserSearchService userSearchService;

    public UserDatafetcher(UserSearchService userSearchService) {
        this.userSearchService = userSearchService;
    }

    @DgsQuery
    public Mono<List<User>> users(@InputArgument SearchInput searchInput) throws JsonProcessingException {
        log.info("users() Starts");
        return userSearchService.searchUsers(searchInput);
    }

    @DgsQuery
    public Mono<User> user(@InputArgument Integer id){
        log.info("user() Starts");
        return userSearchService.searchById(id);
    }

}
