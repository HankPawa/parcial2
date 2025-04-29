package co.edu.usco.pw.springSecurityDB.config;

import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import co.edu.usco.pw.springSecurityDB.model.RoleEntity;
import co.edu.usco.pw.springSecurityDB.model.UserEntity;
import co.edu.usco.pw.springSecurityDB.repository.RoleRepository;
import co.edu.usco.pw.springSecurityDB.repository.UserRepository;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(
            RoleRepository roleRepository,
            UserRepository userRepository,
            BCryptPasswordEncoder passwordEncoder) {

        return args -> {
            // Crear roles necesarios
            RoleEntity rectorRole = roleRepository.findByName("RECTOR").orElseGet(() -> roleRepository.save(new RoleEntity("RECTOR")));
            RoleEntity docenteRole = roleRepository.findByName("DOCENTE").orElseGet(() -> roleRepository.save(new RoleEntity("DOCENTE")));
            RoleEntity estudianteRole = roleRepository.findByName("ESTUDIANTE").orElseGet(() -> roleRepository.save(new RoleEntity("ESTUDIANTE")));

            // Crear usuarios de ejemplo
            if (userRepository.findByUsername("rector").isEmpty()) {
                UserEntity rector = new UserEntity();
                rector.setUsername("rector");
                rector.setPassword(passwordEncoder.encode("1234"));
                rector.setRoles(Set.of(rectorRole));
                userRepository.save(rector);
            }

            if (userRepository.findByUsername("docente1").isEmpty()) {
                UserEntity docente = new UserEntity();
                docente.setUsername("docente1");
                docente.setPassword(passwordEncoder.encode("1234"));
                docente.setRoles(Set.of(docenteRole));
                userRepository.save(docente);
            }
            if (userRepository.findByUsername("docente2").isEmpty()) {
                UserEntity docente = new UserEntity();
                docente.setUsername("docente2");
                docente.setPassword(passwordEncoder.encode("1234"));
                docente.setRoles(Set.of(docenteRole));
                userRepository.save(docente);
            }
            if (userRepository.findByUsername("docente3").isEmpty()) {
                UserEntity docente = new UserEntity();
                docente.setUsername("docente3");
                docente.setPassword(passwordEncoder.encode("1234"));
                docente.setRoles(Set.of(docenteRole));
                userRepository.save(docente);
            }

            if (userRepository.findByUsername("estudiante").isEmpty()) {
                UserEntity estudiante = new UserEntity();
                estudiante.setUsername("estudiante");
                estudiante.setPassword(passwordEncoder.encode("1234"));
                estudiante.setRoles(Set.of(estudianteRole));
                userRepository.save(estudiante);
            }
        };
    }
}