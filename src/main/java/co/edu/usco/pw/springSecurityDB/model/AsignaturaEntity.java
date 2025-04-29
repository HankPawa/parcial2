package co.edu.usco.pw.springSecurityDB.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalTime;

@Entity
@Data
public class AsignaturaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30, nullable = false)
    private String nombre;

    @Column(length = 100)
    private String descripcion;

    @Column(nullable = false)
    private int salon;

    @Column(nullable = false)
    private LocalTime horaInicio;

    @Column(nullable = false)
    private LocalTime horaFin;

    @ManyToOne
    @JoinColumn(name = "docente_encargado", nullable = false)
    private UserEntity docenteEncargado;
}