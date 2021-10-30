package me.amandaam.lab.api.comment;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import me.amandaam.lab.api.discipline.Discipline;
import me.amandaam.lab.api.discipline.DisciplineDTO;
import me.amandaam.lab.api.discipline.DisciplineRepository;
import me.amandaam.lab.api.discipline.exception.DisciplineNotFoundException;
import me.amandaam.lab.api.jwt.JwtService;
import me.amandaam.lab.api.user.User;
import me.amandaam.lab.api.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import java.util.Optional;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private DisciplineRepository disciplineRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;


    public DisciplineDTO addComment(Long idDiscipline, CommentCreateDTO newComment, String token) throws ServletException {
        String email = jwtService.getSujeitoDoToken(token);
        if (!userService.usuarioTemPermissao(token, email)) {
            throw new ServletException("Usuario nao tem permissao");
        }
        User user = this.userService.getUsuario(email);
        Optional<Discipline> disciplineOptional = this.disciplineRepository.findById(idDiscipline);
        if (!disciplineOptional.isPresent()) {
            throw new DisciplineNotFoundException("A disciplina de ID " + idDiscipline + " não existe");
        }
        Discipline discipline = disciplineOptional.get();
        Comment comment = Comment.builder().content(newComment.getContent()).discipline(discipline).user(user).build();
        comment = commentRepository.save(comment);
        return DisciplineDTO.convertToDisciplineDTO(discipline);

    }
}
