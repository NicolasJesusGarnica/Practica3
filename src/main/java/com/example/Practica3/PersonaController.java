package com.example.Practica3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class PersonaController {

    @Autowired
    private PersonaRepository repository;

    // 1. Ver todos los registros (GET)
    @GetMapping
    public List<Persona> listar() {
        return repository.findAll();
    }

    // 2. Ver un solo registro por su ID (GET /id)
    @GetMapping("/{id}")
    public Persona obtenerPorId(@PathVariable Long id) {
        return repository.findById(id).orElse(null);
    }

    // Crear un registro (POST - el de la foto de clase)
    @PostMapping
    public Persona crear(@RequestBody Persona persona) {
        return repository.save(persona);
    }

    // TAREA: Modificar un registro por ID (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<Persona> actualizar(@PathVariable Long id, @RequestBody Persona datosNuevos) {
        return repository.findById(id).map(persona -> {
            persona.setName(datosNuevos.getName());
            persona.setLastname(datosNuevos.getLastname());
            return ResponseEntity.ok(repository.save(persona));
        }).orElse(ResponseEntity.notFound().build());
    }

    // TAREA: Eliminar un registro por ID (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}