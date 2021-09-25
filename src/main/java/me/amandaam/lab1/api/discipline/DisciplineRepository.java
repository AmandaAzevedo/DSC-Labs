package me.amandaam.lab1.api.discipline;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@NoArgsConstructor
@AllArgsConstructor
public class DisciplineRepository {
    private Set<Discipline> disciplines = new HashSet<Discipline>();
    private Long lastID = 0L;

    public Discipline save(Discipline d) {
        d.setId(lastID);
        lastID++;
        disciplines.add(d);
        return d;
    }

    public boolean disciplineExistByName(String name){
        for (Discipline d : this.disciplines){
            if (d.getName().equals(name)){
                return true;
            }
        }
        return false;
    }

    public List<Discipline> getAllDisciplines() {
        return new LinkedList<Discipline>(disciplines);
    }

    public Discipline getDisciplineById(Long id) {
        for (Discipline d : this.disciplines) {
            if (d.getId().equals(id)) {
                return d;
            }
        }
        return null;
    }

    public Discipline update(Long id, Discipline discipline) {
        Discipline d = getDisciplineById(id);
        if (d != null) {
            this.disciplines.remove(d);
            if (discipline.getName() != null) {
                d.setName(discipline.getName());
            }
            if (discipline.getNotes() != null) {
                d.setNotes(discipline.getNotes());
            }
            this.disciplines.add(d);
        }

        return null;
    }

    public void removeById(Long id) {
        Discipline d = getDisciplineById(id);
        if (d != null) {
            this.disciplines.remove(d);
        }
    }

    public List<Discipline> rankingDiscipline() {
        List<Discipline> listDiscipline = new LinkedList<Discipline>(this.disciplines);
        Collections.sort(listDiscipline, (a, b) -> a.getDisciplineAverage() < b.getDisciplineAverage() ? -1 : 1);
        return listDiscipline;
    }

}
