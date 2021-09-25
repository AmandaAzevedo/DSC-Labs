package me.amandaam.lab1.api.discipline;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import me.amandaam.lab1.api.discipline.exception.DisciplineNotFoundException;
import me.amandaam.lab1.api.discipline.exception.InvalidNameException;
import me.amandaam.lab1.api.discipline.exception.InvalidNoteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class DisciplineService {
    @Autowired
    private DisciplineRepository disciplineRepository;

    public List<DisciplineDTO> getAllDisciplines(){
        List<Discipline> disciplines = disciplineRepository.getAllDisciplines();
        List<DisciplineDTO> disciplineDTOS = new LinkedList<DisciplineDTO>();
        for (Discipline d: disciplines){
            disciplineDTOS.add(DisciplineDTO.convertToDisciplineDTO(d));
        }
        return disciplineDTOS;
    }

    public DisciplineDTO createNewDiscipline(DisciplineCreateDTO d){
        if (d.getName() == null || d.getName().length()<3){
            throw new InvalidNameException("Nome da disciplina é nulo ou possui menos de dois caractérs");
        } else if (disciplineRepository.disciplineExistByName(d.getName()) == true){
            throw new InvalidNameException("Nome da disciplina já existe");
        } else{
            Discipline discipline = Discipline.builder().name(d.getName()).build();
            return DisciplineDTO.convertToDisciplineDTO(disciplineRepository.save(discipline));
        }
    }

    public DisciplineDTO getDisciplineByID (Long id){
        Discipline d = disciplineRepository.getDisciplineById(id);
        if (id < 0 || d == null){
            throw new DisciplineNotFoundException("Não foi encontrado nenhuma disciplina com o ID " + id.toString());
        }
        return DisciplineDTO.convertToDisciplineDTO(d);
    }

    public DisciplineDTO addNewNote(Long id, UpdateNoteDTO note){
        if (id < 0 || id == null){
            throw new DisciplineNotFoundException("O ID " + id.toString() + " é inválido");
        }
        if (note == null || note.getNote() < 0 ){
            throw new InvalidNoteException("A nota deve ser maior ou igual a zero.");
        }
        Discipline discipline = this.disciplineRepository.getDisciplineById(id);
        if (discipline == null) {
            throw new DisciplineNotFoundException("A disciplina de ID " + id + " não existe");
        } else if (discipline.getNotes() != null){
            discipline.getNotes().add(note.getNote());
            this.disciplineRepository.update(id, discipline);
        } else{
            discipline.setNotes(new LinkedList<>());
            discipline.getNotes().add(note.getNote());
            this.disciplineRepository.update(id, discipline);
        }
        return DisciplineDTO.convertToDisciplineDTO(discipline);
    }

    public DisciplineDTO updateDisciplineName(Long id, UpdateNameDTO newName){
        if (newName == null || newName.getName().length()<3){
            throw new InvalidNameException("Nome da disciplina é nulo ou possui menos de dois caractérs");
        }
        Discipline discipline = this.disciplineRepository.getDisciplineById(id);
        if (discipline == null){
            throw new DisciplineNotFoundException("A disciplina de ID " + id + " não existe");
        } else {
            discipline.setName(newName.getName());
            this.disciplineRepository.update(id, discipline);
        }
        return DisciplineDTO.convertToDisciplineDTO(discipline);
    }

    public void deleteDiscipline(Long id){
        Discipline d = disciplineRepository.getDisciplineById(id);
        if (id < 0 || d == null){
            throw new DisciplineNotFoundException("Não foi encontrado nenhuma disciplina com o ID " + id.toString());
        }
        disciplineRepository.removeById(id);
    }

    public List<DisciplineDTO> getDisciplinesOrderedByGrade(){
        List<DisciplineDTO> disciplinesDTOS = new LinkedList<DisciplineDTO>();
        List<Discipline> disciplinesOrdered = disciplineRepository.rankingDiscipline();
        for (Discipline discipline: disciplinesOrdered){
            DisciplineDTO dto = DisciplineDTO.convertToDisciplineDTO(discipline);
            disciplinesDTOS.add(dto);
        }
        return disciplinesDTOS;
    }
}
