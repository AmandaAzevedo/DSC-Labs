package me.amandaam.lab1.api.discipline;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/disciplinas")
public class DisciplineController {
    @Autowired
    private DisciplineService disciplineService;

    /*
    OK
     */
    @GetMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public DisciplineDTO getDisciplineById(@PathVariable("id") Long id){
        return disciplineService.getDisciplineByID(id);
    }

    /*
    OK
     */
    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<DisciplineDTO> getAllDisciplines(){
        return disciplineService.getAllDisciplines();
    }

    /*
    OK
     */
    @GetMapping(value = "/ranking")
    @ResponseStatus(code = HttpStatus.OK)
    public List<DisciplineDTO> getDisciplinesOrderedByGrade(){
        return disciplineService.getDisciplinesOrderedByGrade();
    }

    /*
    OK
     */
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteDisciplineById(@PathVariable("id") Long id){
        disciplineService.deleteDiscipline(id);
    }

    /*
    OK
     */
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

    /*
    OK
     */
    @PatchMapping (value = "/{id}/note")
    @ResponseStatus(code = HttpStatus.CREATED)
    public DisciplineDTO addNoteInDiscipline(@PathVariable("id") Long id, @RequestBody UpdateNoteDTO note){
        return disciplineService.addNewNote(id, note);
    }
}
