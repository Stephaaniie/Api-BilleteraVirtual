package ar.com.ada.api.billeteravirtual.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.ada.api.billeteravirtual.entities.Persona;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Integer> {
    
}