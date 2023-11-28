package com.learntech.graphqlclientapi.model;


import lombok.Data;

import java.util.List;
import java.util.UUID;

/**
 * User
 *
 * @author Uthiraraj Saminathan
 */
@Data
public class User {

    private UUID id;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String gender;
    private List<Address> address;
    private List<Phone> phone;

}
