package co.edu.usco.pw.springSecurityDB.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import co.edu.usco.pw.springSecurityDB.model.AsignaturaEntity;
import co.edu.usco.pw.springSecurityDB.model.UserEntity;
import co.edu.usco.pw.springSecurityDB.repository.AsignaturaRepository;
import co.edu.usco.pw.springSecurityDB.repository.UserRepository;

import java.time.LocalTime;
import java.util.List;

@Controller
@Tag(name = "Docente", description = "Servicios para la gestión de asignaturas por parte del Docente")
public class DocenteController {

    @Autowired
    private AsignaturaRepository asignaturaRepository;

    @Autowired
    private UserRepository userRepository;

    @Operation(summary = "Listar asignaturas del docente", description = "Obtiene una lista de las asignaturas asignadas al docente autenticado.")
    @GetMapping("/docente/asignaturas")
    public String listarAsignaturasDocente(Model model, Authentication authentication) {
        String username = authentication.getName();
        UserEntity docente = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("Docente no encontrado"));

        List<AsignaturaEntity> asignaturas = asignaturaRepository.findByDocenteEncargado(docente);
        model.addAttribute("asignaturas", asignaturas);

        return "docente";
    }

    @Operation(summary = "Actualizar horario", description = "Permite al docente actualizar el horario de una asignatura asignada.")
    @PostMapping("/docente/asignaturas/editar/{id}")
    public String actualizarHorario(@PathVariable Long id, @RequestParam String horaInicio, @RequestParam String horaFin) {
        AsignaturaEntity asignatura = asignaturaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Asignatura no encontrada"));

        asignatura.setHoraInicio(LocalTime.parse(horaInicio));
        asignatura.setHoraFin(LocalTime.parse(horaFin));
        asignaturaRepository.save(asignatura);

        return "redirect:/docente";
    }
}