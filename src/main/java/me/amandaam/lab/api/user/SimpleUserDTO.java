package me.amandaam.lab.api.user;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Builder
public class SimpleUserDTO implements Serializable {

    private Long id;
    private String name;
    private String email;

    public static SimpleUserDTO convertToUserDTO(User u) {
        return SimpleUserDTO.builder().id(u.getId()).name(u.getName()).email(u.getEmail()).build();
    }
}
