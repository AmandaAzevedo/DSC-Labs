package me.amandaam.lab1.api.discipline;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.util.LinkedList;

@Getter
@Builder
public class DisciplineDTO implements Serializable {
    private Long id;
    private String name;
    private Long likes = 0L;
    private LinkedList<Double> notes = new LinkedList<Double>();

    public static DisciplineDTO convertToDisciplineDTO(Discipline d) {
        return DisciplineDTO.builder().id(d.getId()).name(d.getName()).likes(d.getLikes()).notes(d.getNotes()).build();
    }
}
