package me.amandaam.lab.api.user;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class UserCreateDTO implements Serializable {
    private String name;
    private String email;
    private String password;
}
