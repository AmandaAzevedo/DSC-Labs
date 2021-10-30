package me.amandaam.lab.api.discipline;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import me.amandaam.lab.api.comment.Comment;
import me.amandaam.lab.api.comment.CommentCreateDTO;
import me.amandaam.lab.api.discipline.exception.DisciplineNotFoundException;
import me.amandaam.lab.api.discipline.exception.InvalidLikesNumberException;
import me.amandaam.lab.api.discipline.exception.InvalidNameException;
import me.amandaam.lab.api.discipline.exception.InvalidNoteException;
import me.amandaam.lab.api.jwt.JwtService;
import me.amandaam.lab.api.user.UserDTO;
import me.amandaam.lab.api.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class DisciplineService {
    @Autowired
    private DisciplineRepository disciplineRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;

    public List<DisciplineDTO> getAllDisciplines() {
        List<Discipline> disciplines = disciplineRepository.findAll();
        List<DisciplineDTO> disciplineDTOS = new LinkedList<DisciplineDTO>();
        for (Discipline d : disciplines) {
            disciplineDTOS.add(DisciplineDTO.convertToDisciplineDTO(d));
        }
        return disciplineDTOS;
    }

    public DisciplineDTO createNewDiscipline(DisciplineCreateDTO d) {
        if (d.getName() == null || d.getName().length() < 3) {
            throw new InvalidNameException("Nome da disciplina é nulo ou possui menos de dois caractérs");
        } else if (disciplineRepository.existsByName(d.getName())) {
            throw new InvalidNameException("Nome da disciplina já existe");
        } else {
            Discipline discipline = Discipline.builder().name(d.getName()).build();
            return DisciplineDTO.convertToDisciplineDTO(disciplineRepository.save(discipline));
        }
    }

    public DisciplineDTO getDisciplineByID(Long id) {
        Optional<Discipline> d = disciplineRepository.findById(id);
        if (id < 0 || !d.isPresent()) {
            throw new DisciplineNotFoundException("Não foi encontrado nenhuma disciplina com o ID " + id.toString());
        }
        return DisciplineDTO.convertToDisciplineDTO(d.get());
    }

    public DisciplineDTO addNewNote(Long id, UpdateNoteDTO note,String token) throws ServletException {
        if (!userService.usuarioTemPermissao(token,jwtService.getSujeitoDoToken(token))) {
            throw new ServletException("Usuario nao tem permissao");
        }
        if (id < 0 || id == null) {
            throw new DisciplineNotFoundException("O ID " + id.toString() + " é inválido");
        }
        if (note == null || note.getNote() < 0) {
            throw new InvalidNoteException("A nota deve ser maior ou igual a zero.");
        }
        Optional<Discipline> disciplineOptional = this.disciplineRepository.findById(id);
        if (!disciplineOptional.isPresent()) {
            throw new DisciplineNotFoundException("A disciplina de ID " + id + " não existe");
        }
        Discipline discipline = disciplineOptional.get();
        if (discipline.getNote() == null) {
            discipline.setNote(note.getNote());
        } else {
            discipline.setNote((discipline.getNote() + note.getNote()) / 2);
        }
        return DisciplineDTO.convertToDisciplineDTO(this.disciplineRepository.save(discipline));
    }

    public DisciplineDTO updateDisciplineName(Long id, UpdateNameDTO newName) {
        if (newName == null || newName.getName().length() < 3) {
            throw new InvalidNameException("Nome da disciplina é nulo ou possui menos de dois caractérs");
        }
        Optional<Discipline> disciplineOptional = this.disciplineRepository.findById(id);
        if (!disciplineOptional.isPresent()) {
            throw new DisciplineNotFoundException("A disciplina de ID " + id + " não existe");
        } else {
            Discipline discipline = disciplineOptional.get();
            discipline.setName(newName.getName());
            this.disciplineRepository.save(discipline);
            return DisciplineDTO.convertToDisciplineDTO(discipline);
        }
    }

    public void deleteDiscipline(Long id) {
        if (!disciplineRepository.existsById(id)) {
            throw new DisciplineNotFoundException("Não foi encontrado nenhuma disciplina com o ID " + id.toString());
        }
        disciplineRepository.deleteById(id);
    }

    public List<DisciplineDTO> getDisciplinesOrderedByGrade() {
        List<DisciplineDTO> disciplinesDTOS = new LinkedList<DisciplineDTO>();
        List<Discipline> disciplinesOrdered = disciplineRepository.findAllByOrderByNoteDesc();
        for (Discipline discipline : disciplinesOrdered) {
            DisciplineDTO dto = DisciplineDTO.convertToDisciplineDTO(discipline);
            disciplinesDTOS.add(dto);
        }
        return disciplinesDTOS;
    }

    public List<DisciplineDTO> getDisciplinesOrderedByLikes() {
        List<DisciplineDTO> disciplinesDTOS = new LinkedList<DisciplineDTO>();
        List<Discipline> disciplinesOrdered = disciplineRepository.findAllByOrderByLikesDesc();
        for (Discipline discipline : disciplinesOrdered) {
            DisciplineDTO dto = DisciplineDTO.convertToDisciplineDTO(discipline);
            disciplinesDTOS.add(dto);
        }
        return disciplinesDTOS;
    }


    public DisciplineDTO addLikes(Long id, Long likes, String token) throws ServletException {
        if (!userService.usuarioTemPermissao(token,jwtService.getSujeitoDoToken(token))) {
            throw new ServletException("Usuario nao tem permissao");
        }
        if (likes < 0 || likes == null){
            throw new InvalidLikesNumberException("O número de likes é inválido");
        }
        Optional<Discipline> disciplineOptional = this.disciplineRepository.findById(id);
        if (!disciplineOptional.isPresent()) {
            throw new DisciplineNotFoundException("A disciplina de ID " + id + " não existe");
        }
        Discipline discipline = disciplineOptional.get();
        if (discipline.getLikes() == null){
            discipline.setLikes(likes);
        } else{
            discipline.setLikes(discipline.getLikes()+likes);
        }
        return DisciplineDTO.convertToDisciplineDTO(this.disciplineRepository.save(discipline));
    }

}