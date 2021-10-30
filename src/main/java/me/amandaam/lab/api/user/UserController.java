package me.amandaam.lab.api.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;

@RestController
@RequestMapping(value = "/api/v1/usuarios")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public UserDTO createUser(@RequestBody UserCreateDTO userCreateDTO){
        return userService.createNewUser(userCreateDTO);
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<UserDTO> removeUsuario(@PathVariable("email") String email,
                                                 @RequestHeader("Authorization") String header) {
        try {
            return new ResponseEntity<UserDTO>(userService.removeUsuario(email, header), HttpStatus.OK);
        } catch (IllegalArgumentException iae) {
            return new ResponseEntity<UserDTO>(HttpStatus.NOT_FOUND);
        } catch (ServletException e) {
            return new ResponseEntity<UserDTO>(HttpStatus.FORBIDDEN);
        }
    }





}
