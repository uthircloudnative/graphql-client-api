package com.learntech.graphqlclientapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * SearchUserResponse
 *
 * @author Uthiraraj Saminathan
 */
@Data
public class SearchUserResponse {
      private List<User> users;
      private User user;
}
