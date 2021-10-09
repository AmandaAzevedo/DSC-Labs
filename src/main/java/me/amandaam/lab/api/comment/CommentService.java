package me.amandaam.lab.api.comment;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import me.amandaam.lab.api.discipline.Discipline;
import me.amandaam.lab.api.discipline.DisciplineDTO;
import me.amandaam.lab.api.discipline.DisciplineRepository;
import me.amandaam.lab.api.discipline.exception.DisciplineNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private DisciplineRepository disciplineRepository;

    public DisciplineDTO addComment(Long idDiscipline, CommentCreateDTO newComment){
        Optional<Discipline> disciplineOptional = this.disciplineRepository.findById(idDiscipline);
        if (!disciplineOptional.isPresent()) {
            throw new DisciplineNotFoundException("A disciplina de ID " + idDiscipline + " não existe");
        }
        Discipline discipline = disciplineOptional.get();
        Comment comment = Comment.builder().content(newComment.getContent()).discipline(discipline).build();
        comment = commentRepository.save(comment);
        return DisciplineDTO.convertToDisciplineDTO(discipline);

    }
}
