package EducacionIt.web.repositories;


import EducacionIt.web.entities.Role;
import EducacionIt.web.entities.UserEntity;
import EducacionIt.web.security.IUserService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepositoty extends JpaRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);
}
