package me.amandaam.lab.api.user;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Builder
public class UserDTO implements Serializable {

    private Long id;
    private String name;
    private String email;
    private String password;

    public static UserDTO convertToUserDTO(User u) {
        return UserDTO.builder().id(u.getId()).name(u.getName()).email(u.getEmail()).password(u.getPassword()).build();
    }
}
