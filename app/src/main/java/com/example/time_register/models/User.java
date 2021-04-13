package com.example.time_register.models;

import com.example.time_register.enums.UserRoles;

public class User {
    public String email;
    public boolean isNew;
    public UserRoles role;
    public User(){

    }
    public User(String email, boolean isNew){
        new User(email, UserRoles.USER, isNew);
    }

    public User(String email, UserRoles role, boolean isNew){
        this.email = email;
        this.role = role;
        this.isNew= isNew;
    }
}