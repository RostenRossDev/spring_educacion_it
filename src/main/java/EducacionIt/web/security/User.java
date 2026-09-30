package EducacionIt.web.security;

import EducacionIt.web.entities.Role;
import lombok.Data;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class User implements UserDetails {
    private Long id;
    private String name;
    private String lastname;
    private String email;
    private String password;
    private Set<Role> roles;
    private Boolean active;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();

        // Agregar roles del usuario (ya tienen el prefijo ROLE_ en la BD)
        if (roles != null) {
            authorities.addAll(
                    roles.stream()
                            .map(role -> new SimpleGrantedAuthority(role.getName()))
                            .collect(Collectors.toSet())
            );
        }

        return authorities;
    }

    @Override
    public @Nullable String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }
}
