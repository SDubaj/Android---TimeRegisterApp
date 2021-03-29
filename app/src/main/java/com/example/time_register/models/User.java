package com.example.time_register.models;

import com.example.time_register.enums.UserRoles;

public class User {
    public String email;
    public UserRoles role;
    public User(){

    }
    public User(String email){
        new User(email, UserRoles.USER);
    }

    public User(String email, UserRoles role){
        this.email = email;
        this.role = role;
    }
}
