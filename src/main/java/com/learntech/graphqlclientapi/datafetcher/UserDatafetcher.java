package com.learntech.graphqlclientapi.datafetcher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.learntech.graphqlclientapi.model.*;
import com.learntech.graphqlclientapi.service.UserSearchService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

/**
 * UserDatafetcher
 *
 * @author Uthiraraj Saminathan
 */
@DgsComponent
public class UserDatafetcher {

    private static Logger logger = LoggerFactory.getLogger(UserDatafetcher.class);
    private final UserSearchService userSearchService;

    public UserDatafetcher(UserSearchService userSearchService) {
        this.userSearchService = userSearchService;
    }

    @DgsQuery
    public Mono<SearchUser> searchUser(@InputArgument SearchInput searchInput) throws JsonProcessingException {
        logger.info("searchUser() Starts");
        return userSearchService.searchUser(searchInput);
    }

    @DgsQuery
    public Mono<User> searchByUserId(@InputArgument Integer id){
        User user = userSearchService.searchById(id);
        return Mono.just(user);
    }

}
