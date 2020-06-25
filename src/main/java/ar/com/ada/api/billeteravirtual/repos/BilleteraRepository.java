package ar.com.ada.api.billeteravirtual.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.com.ada.api.billeteravirtual.entities.Billetera;

public interface BilleteraRepository extends JpaRepository<Billetera, Integer>{
    
}