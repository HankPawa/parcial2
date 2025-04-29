package co.edu.usco.pw.springSecurityDB.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/inicio")
    public String showInicioPage() {
        return "inicio";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/403")
    public String error403() {
        return "403error";
    }

    @GetMapping("/404")
    public String pageNotFound() {
        return "404";
    }

    @GetMapping("/rector")
    public String showRectorPage() {
        return "rector"; 
    }

    @GetMapping("/docente")
    public String showDocentePage() {
        return "docente"; 
    }

    @GetMapping("/estudiante")
    public String showEstudiantePage() {
        return "estudiante"; 
    }
}

