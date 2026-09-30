package EducacionIt.web.security;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService extends UserDetailsService {

    User getByEmail(String email);
}