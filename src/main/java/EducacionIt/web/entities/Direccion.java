package EducacionIt.web.entities;

import lombok.*;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Direccion {
    private String calle;
    private String ciudad;
    private int codigoPostal;
}