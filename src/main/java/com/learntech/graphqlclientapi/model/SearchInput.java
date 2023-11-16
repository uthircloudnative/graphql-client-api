package com.learntech.graphqlclientapi.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

/**
 * SearchInput
 *
 * @author Uthiraraj Saminathan
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SearchInput {

    private String firstName;
    private String lastName;
    private Date dateOfBirth;
}
