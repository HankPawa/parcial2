package co.edu.usco.pw.springSecurityDB.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Asignatura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String descripcion;

    private int salon;

    private String horario;

    @ManyToOne
    @JoinColumn(name = "docente_encargado", nullable = false)
    private UserEntity docenteEncargado;
}