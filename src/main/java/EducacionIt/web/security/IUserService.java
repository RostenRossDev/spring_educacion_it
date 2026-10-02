package EducacionIt.web.security;

import EducacionIt.web.entities.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService extends UserDetailsService {

    User getByEmail(String email);

    UserEntity save(UserEntity u);
}