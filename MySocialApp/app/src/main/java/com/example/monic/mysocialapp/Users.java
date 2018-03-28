package com.example.monic.mysocialapp;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by monic on 11/18/2017.
 */

public class Users implements Serializable{
    String userId;
    String firstName;
    String lastName;
    String email;
    String password;
    String birthday;

    public Users() {
    }

    public Users(String userId, String firstName, String lastName, String email, String password, String birthday) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.birthday = birthday;
    }

    public String getUserId() {
        return userId;
    }

    public String getName(String userId) {
        return firstName + " " + lastName;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}
