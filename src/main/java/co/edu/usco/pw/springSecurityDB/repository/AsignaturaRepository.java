package co.edu.usco.pw.springSecurityDB.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import co.edu.usco.pw.springSecurityDB.model.AsignaturaEntity;
import co.edu.usco.pw.springSecurityDB.model.UserEntity;

import java.util.List;

public interface AsignaturaRepository extends JpaRepository<AsignaturaEntity, Long> {
    List<AsignaturaEntity> findByDocenteEncargado(UserEntity docenteEncargado);
}