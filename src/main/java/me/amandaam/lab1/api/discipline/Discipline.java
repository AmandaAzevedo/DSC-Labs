package me.amandaam.lab1.api.discipline;

import lombok.*;
import org.springframework.stereotype.Component;

import java.util.LinkedList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(of = {"id"})
public class Discipline {
    private Long id;
    private String name;
    private Long likes = 0L;
    private LinkedList<Double> notes = new LinkedList<Double>();

    public double getDisciplineAverage() {
        if (this.notes == null){
            return 0;
        }
        if (this.notes.size() == 0) {
            return 0;
        }
        double cont = 0.0;
        for (double d: this.notes){
          cont += d;
        }
        return cont / this.notes.size();
    }

}