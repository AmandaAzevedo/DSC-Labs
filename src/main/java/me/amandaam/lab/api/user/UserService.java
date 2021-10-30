package me.amandaam.lab.api.user;

import me.amandaam.lab.api.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtService servicoJwt;

    public UserDTO createNewUser(UserCreateDTO user) {
        User newUser = User.builder().name(user.getName()).email(user.getEmail()).password(user.getPassword()).build();
        newUser = this.userRepository.save(newUser);
        return UserDTO.convertToUserDTO(newUser);
    }

    public User getUsuario(String email) {
        Optional<User> optUsuario = userRepository.findByEmail(email);
        if (!optUsuario.isPresent())
            throw new IllegalArgumentException();// usuario nao existe
        return optUsuario.get();
    }

    public UserDTO removeUsuario(String email, String authHeader) throws ServletException {
        User usuario = getUsuario(email);
        if (usuarioTemPermissao(authHeader, email)) {
            userRepository.delete(usuario);
            return UserDTO.convertToUserDTO(usuario);
        }
        throw new ServletException("Usuario nao tem permissao");
    }

    public boolean usuarioTemPermissao(String authorizationHeader, String email) throws ServletException {
        String subject = servicoJwt.getSujeitoDoToken(authorizationHeader);
        Optional<User> optUsuario = userRepository.findByEmail(subject);
        return optUsuario.isPresent() && optUsuario.get().getEmail().equals(email);
    }

    public boolean validaUsuarioSenha(UserDTO usuario) {
        Optional<User> optUsuario = userRepository.findByEmail(usuario.getEmail());
        if (optUsuario.isPresent() && optUsuario.get().getPassword().equals(usuario.getPassword()))
            return true;
        return false;
    }
}
