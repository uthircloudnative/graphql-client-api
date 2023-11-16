package com.learntech.graphqlclientapi.model;

import lombok.Data;

import java.util.List;

/**
 * SearchUser
 *
 * @author Uthiraraj Saminathan
 */
@Data
public class SearchUser {

    private List<User> users;

}
