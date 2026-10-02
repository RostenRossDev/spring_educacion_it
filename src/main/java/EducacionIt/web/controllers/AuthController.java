package EducacionIt.web.controllers;

import EducacionIt.web.dto.CredentialDto;
import EducacionIt.web.dto.RegisterDto;
import EducacionIt.web.entities.Persona;
import EducacionIt.web.entities.Role;
import EducacionIt.web.entities.UserEntity;
import EducacionIt.web.enums.RoleEnum;
import EducacionIt.web.security.IUserService;
import EducacionIt.web.security.JwtUtil;
import EducacionIt.web.security.User;
import EducacionIt.web.services.PersonaService;
import EducacionIt.web.services.RoleService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Log4j2
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private IUserService userService;

    @Autowired
    private PersonaService personaService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> login(@RequestBody RegisterDto registerDto){
        Persona p = new Persona();
        p.setApellido(registerDto.lastname());
        p.setNombre(registerDto.name());
        p.setTelefono(registerDto.phone());
        p = personaService.save(p);

        UserEntity u = new UserEntity();
        u.setEmail(registerDto.email());
        u.setPersona(p);
        u.setActive(true);
        u.setPassword(passwordEncoder.encode(registerDto.password()));
        Role role = roleService.finByName(RoleEnum.ROLE_USER.name());
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        u.setRoles(roles);

        u = userService.save(u);
        if (u.getId() == null) {
            return ResponseEntity.internalServerError().body("Ocurrio un error interno, intente mas tarde.");
        }

        log.info(u.getPassword());
        return ResponseEntity.ok("Exito en la cracion de usuario.");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody CredentialDto credentialDto){
        User userDetails = userService.getByEmail(credentialDto.email());

        if(userDetails == null || !passwordEncoder.matches(credentialDto.password(), userDetails.getPassword())) {
            // Auditar intento fallido de login
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Map<String, Object> additionalInfo = additionalInfo(userDetails);
        String accessToken = jwtUtil.generateToken( userDetails, additionalInfo);
        String refreshToken = jwtUtil.generateRefreshToken(userDetails);
        log.info("token: " + accessToken);
        log.info("refreshToken: " + refreshToken);
        Map<String, Object> response = new HashMap<>();
        response.put("accessToken", accessToken);
        response.put("refreshToken", refreshToken);
        return ResponseEntity.ok(response);
    }

    private Map<String, Object> additionalInfo(User userDetails){
        Map<String, Object> additionalInfo = new HashMap<>();
        additionalInfo.put("email", userDetails.getEmail());
        additionalInfo.put("name", userDetails.getName());
        additionalInfo.put("lastname", userDetails.getLastname());

        // Roles
        additionalInfo.put("roles", userDetails.getRoles());
        return additionalInfo;
    }
}
