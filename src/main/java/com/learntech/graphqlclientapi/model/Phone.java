package com.learntech.graphqlclientapi.model;

import lombok.*;

import java.util.Objects;

/**
 * Phone
 *
 * @author Uthiraraj Saminathan
 */
@Data
public class Phone {

    private String type;
    private String number;
    private String countryCode;

}
