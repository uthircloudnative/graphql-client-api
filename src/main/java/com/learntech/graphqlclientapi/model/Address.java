package com.learntech.graphqlclientapi.model;

import lombok.Data;

/**
 * Address
 *
 * @author Uthiraraj Saminathan
 */
@Data
public class Address {

    private String type;
    private String street1;
    private String street2;
    private String city;
    private String state;
    private Integer zip;

}
