package com.phonecinema.serviciosusuario.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.boot.CommandLineRunner;
import com.phonecinema.serviciosusuario.model.Rol;
import com.phonecinema.serviciosusuario.repository.RolRepository;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private RolRepository rolRepository;

    @Override
    public void run(String... args) {
        if (!rolRepository.existsByNombre("USUARIO"))
            rolRepository.save(new Rol(null, "USUARIO"));

        if (!rolRepository.existsByNombre("ADMIN"))
            rolRepository.save(new Rol(null, "ADMIN"));

        if (!rolRepository.existsByNombre("MODERADOR"))
            rolRepository.save(new Rol(null, "MODERADOR"));
    }
}
