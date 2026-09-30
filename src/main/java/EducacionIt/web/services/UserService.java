package EducacionIt.web.services;


import EducacionIt.web.entities.UserEntity;
import EducacionIt.web.repositories.UserRepositoty;
import EducacionIt.web.security.IUserService;
import EducacionIt.web.security.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepositoty userRepository;

    @Override
    public User getByEmail(String email) {
        UserEntity userE = userRepository.findByEmail(email);
        User userDetail = new User();
        userDetail.setId(userE.getId());
        userDetail.setName(userE.getName());
        userDetail.setEmail(userE.getEmail());
        userDetail.setActive(userE.getActive());
        userDetail.setPassword(userE.getPassword());
        userDetail.setLastname(userE.getLastname());
        userDetail.setRoles(userE.getRoles());
        return userDetail;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.getByEmail(username);
    }
}