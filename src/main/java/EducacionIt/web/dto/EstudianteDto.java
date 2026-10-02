package EducacionIt.web.dto;

import EducacionIt.web.entities.Curso;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EstudianteDto {
    private Integer legajo;
    private String nombre;
    private String apellido;
    private int edad;
    private String direccion;
    private String telefono;
    private List<Curso> cursos;

    public static Builder builder() {
        return new Builder();
    }

    private EstudianteDto(Builder builder) {
        this.legajo = builder.legajo;
        this.nombre = builder.nombre;
        this.apellido = builder.apellido;
        this.edad = builder.edad;
        this.direccion = builder.direccion;
        this.telefono = builder.telefono;
        this.cursos = builder.cursos;
    }

    public static final class Builder {
        private Integer legajo;
        private String nombre;
        private String apellido;
        private int edad;
        private String direccion;
        private String telefono;
        private List<Curso> cursos;

        public Builder legajo(Integer legajo){
            this.legajo = legajo;
            return this;
        }
        public Builder nombre(String nombre){
            this.nombre = nombre;
            return this;
        }
        public Builder apellido(String apellido){
            this.apellido = apellido;
            return this;
        }
        public Builder edad(int edad){
            this.edad = edad;
            return this;
        }
        public Builder direccion(String direccion){
            this.direccion = direccion;
            return this;
        }
        public Builder telefono(String telefono){
            this.telefono = telefono;
            return this;
        }
        public Builder cursos( List<Curso> cursos){
            this.cursos = cursos;
            return this;
        }

        public EstudianteDto build(){
            return new EstudianteDto(this);
        }
    }
}
