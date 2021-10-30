package me.amandaam.lab.api.user;

import me.amandaam.lab.api.jwt.JwtService;
import me.amandaam.lab.api.jwt.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private JwtService servicoJwt;


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> autentica(@RequestBody UserDTO user) throws ServletException {
        return new ResponseEntity<LoginResponse>(servicoJwt.autentica(user), HttpStatus.OK);
    }

}
