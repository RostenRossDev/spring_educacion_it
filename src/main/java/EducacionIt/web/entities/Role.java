package EducacionIt.web.entities;

import EducacionIt.web.validator.NotVoid;
import EducacionIt.web.validator.NotVoidValidator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
@Getter
@Setter
public class Role {

    public Role(Long id, String name, String description, Set<UserEntity> users) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.users = users;
        NotVoidValidator.validateFields(this);
    }

    public Role(){
        NotVoidValidator.validateFields(this);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@NotVoid
    @Column(nullable = false, unique = true)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    private Set<UserEntity> users = new HashSet<>();

    @NotVoid
    public void setDescription(String description, Long test) {
        try {
            Method m = this.getClass().getMethod("setDescription", String.class, Long.class);
            NotVoidValidator.validateParameters(m, new Object[]{ description, test });
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
