package me.amandaam.lab.api.discipline;

import me.amandaam.lab.api.comment.CommentCreateDTO;
import me.amandaam.lab.api.comment.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/disciplinas")
public class DisciplineController {
    @Autowired
    private DisciplineService disciplineService;

    @Autowired
    private CommentService commentService;


    @GetMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public DisciplineDTO getDisciplineById(@PathVariable("id") Long id){
        return disciplineService.getDisciplineByID(id);
    }


    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<DisciplineDTO> getAllDisciplines(){
        return disciplineService.getAllDisciplines();
    }

    @GetMapping(value = "/ranking/notas")
    @ResponseStatus(code = HttpStatus.OK)
    public List<DisciplineDTO> getDisciplinesOrderedByGrade(){
        return disciplineService.getDisciplinesOrderedByGrade();
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteDisciplineById(@PathVariable("id") Long id){
        disciplineService.deleteDiscipline(id);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public DisciplineDTO createDiscipline(@RequestBody DisciplineCreateDTO disciplineCreateDTO){
        return disciplineService.createNewDiscipline(disciplineCreateDTO);
    }

    @PatchMapping (value = "/{id}/nome")
    @ResponseStatus(code = HttpStatus.OK)
    public DisciplineDTO updateDisciplineName(@PathVariable("id") Long id, @RequestBody UpdateNameDTO name){
        return disciplineService.updateDisciplineName(id, name);
    }

    @PatchMapping (value = "/{id}/note")
    @ResponseStatus(code = HttpStatus.CREATED)
    public DisciplineDTO addNoteInDiscipline(@PathVariable("id") Long id, @RequestBody UpdateNoteDTO note, @RequestHeader("Authorization") String header) throws ServletException {
        return disciplineService.addNewNote(id, note, header);
    }

    @PatchMapping(value = "/likes/{id}")
    @ResponseStatus(code = HttpStatus.CREATED)
    public DisciplineDTO addLikeInDiscipline(@PathVariable("id") Long id, @RequestBody Long numLikes, @RequestHeader("Authorization") String header) throws ServletException {
        return disciplineService.addLikes(id, numLikes, header);
    }

    @GetMapping(value = "/ranking/likes")
    @ResponseStatus(code = HttpStatus.OK)
    public List<DisciplineDTO> getDisciplinesOrderedByLikes(){
        return disciplineService.getDisciplinesOrderedByLikes();
    }

    @PutMapping(value = "/comentarios/{id}")
    @ResponseStatus(code = HttpStatus.CREATED)
    public DisciplineDTO addCommentInDiscipline(@PathVariable("id") Long disciplineId, @RequestBody CommentCreateDTO comment, @RequestHeader("Authorization") String header) throws ServletException {
        return commentService.addComment(disciplineId, comment, header);
    }

}
