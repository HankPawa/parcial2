package co.edu.usco.pw.springSecurityDB.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import co.edu.usco.pw.springSecurityDB.model.AsignaturaEntity;
import co.edu.usco.pw.springSecurityDB.repository.AsignaturaRepository;

import java.util.List;

@Controller
public class EstudianteController {

    @Autowired
    private AsignaturaRepository asignaturaRepository;

    @GetMapping("/estudiante/asignaturas")
    public String listarAsignaturasEstudiante(Model model, Authentication authentication) {
        // Obtener todas las asignaturas
        List<AsignaturaEntity> asignaturas = asignaturaRepository.findAll();

        // Pasar las asignaturas al modelo
        model.addAttribute("asignaturas", asignaturas);

        return "estudiante"; // Renderizar la vista "estudiante.html"
    }
}