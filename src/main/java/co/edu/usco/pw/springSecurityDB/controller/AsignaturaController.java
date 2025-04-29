package co.edu.usco.pw.springSecurityDB.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import co.edu.usco.pw.springSecurityDB.model.AsignaturaEntity;
import co.edu.usco.pw.springSecurityDB.repository.AsignaturaRepository;
import co.edu.usco.pw.springSecurityDB.model.UserEntity;
import co.edu.usco.pw.springSecurityDB.repository.UserRepository;

import java.util.List;

@Controller
@RequestMapping("/rector/asignaturas")
@Tag(name = "Asignaturas", description = "Servicios para la gestión de asignaturas por parte del Rector")
public class AsignaturaController {

    @Autowired
    private AsignaturaRepository asignaturaRepository;

    @Autowired
    private UserRepository userRepository;

    @Operation(summary = "Listar asignaturas", description = "Obtiene una lista de todas las asignaturas registradas.")
    @GetMapping
    public String listarAsignaturas(Model model) {
        List<AsignaturaEntity> asignaturas = asignaturaRepository.findAll();
        model.addAttribute("asignaturas", asignaturas);
        return "rector";
    }

    @Operation(summary = "Mostrar formulario de creación", description = "Muestra el formulario para crear una nueva asignatura.")
    @GetMapping("/crear")
    public String mostrarFormularioCrear(Model model) {
        model.addAttribute("asignatura", new AsignaturaEntity());
        List<UserEntity> docentes = userRepository.findAll()
                .stream()
                .filter(user -> user.getRoles().stream().anyMatch(role -> role.getName().equals("DOCENTE")))
                .toList();
        model.addAttribute("docentes", docentes);
        return "asignaturas/create";
    }

    @Operation(summary = "Crear asignatura", description = "Crea una nueva asignatura y la guarda en la base de datos.")
    @PostMapping("/crear")
    public String crearAsignatura(@ModelAttribute AsignaturaEntity asignatura, @RequestParam Long docenteId) {
        UserEntity docente = userRepository.findById(docenteId)
            .orElseThrow(() -> new RuntimeException("Docente no encontrado"));
        asignatura.setDocenteEncargado(docente);
        asignaturaRepository.save(asignatura);
        return "redirect:/rector/asignaturas";
    }

    @Operation(summary = "Mostrar formulario de edición", description = "Muestra el formulario para editar una asignatura existente.")
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        model.addAttribute("asignatura", asignaturaRepository.findById(id).orElseThrow());
        model.addAttribute("docentes", userRepository.findAll());
        return "asignaturas/edit";
    }

    @Operation(summary = "Editar asignatura", description = "Actualiza los datos de una asignatura existente.")
    @PostMapping("/editar/{id}")
    public String editarAsignatura(@PathVariable Long id, @ModelAttribute AsignaturaEntity asignatura, @RequestParam Long docenteId) {
        UserEntity docente = userRepository.findById(docenteId).orElseThrow(() -> new RuntimeException("Docente no encontrado"));
        asignatura.setId(id);
        asignatura.setDocenteEncargado(docente);
        asignaturaRepository.save(asignatura);
        return "redirect:/rector/asignaturas";
    }

    @Operation(summary = "Eliminar asignatura", description = "Elimina una asignatura existente.")
    @PostMapping("/eliminar/{id}")
    public String eliminarAsignatura(@PathVariable Long id) {
        asignaturaRepository.deleteById(id);
        return "redirect:/rector/asignaturas";
    }
}