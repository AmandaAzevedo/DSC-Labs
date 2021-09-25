package me.amandaam.lab1.api.discipline;

import lombok.Getter;

import java.io.Serializable;
import java.util.LinkedList;

@Getter
public class DisciplineCreateDTO implements Serializable {
    private String name;
    private Long likes = 0L;
    private LinkedList<Double> notes = new LinkedList<Double>();
}
