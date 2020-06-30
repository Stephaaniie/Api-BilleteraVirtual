package ar.com.ada.api.billeteravirtual.services;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.billeteravirtual.entities.Billetera;
import ar.com.ada.api.billeteravirtual.entities.Cuenta;
import ar.com.ada.api.billeteravirtual.entities.Transaccion;
import ar.com.ada.api.billeteravirtual.repositories.BilleteraRepository;

@Service
public class BilleteraService {

    @Autowired
    BilleteraRepository billeteraRepository;

    public void grabar(Billetera billetera){
        billeteraRepository.save(billetera);
    }
    

/**
     * Metodo cargarSaldo 
     * buscar billetera por id 
     * se identifica cuenta por moneda
     * determinar importe a cargar 
     * hacer transaccion
     * 
     * ver delegaciones sobre entidades
     * 
     */

    

    public void cargarSaldo(BigDecimal saldo, String moneda, Integer billeteraId, 
    String conceptoOperacion, String detalle){
    
        Billetera billetera = billeteraRepository.findByBilleteraId(billeteraId);
        
        Cuenta cuenta = billetera.getCuenta(moneda);

        Transaccion transaccion = new Transaccion();
        //transaccion.setCuenta(cuenta);
        transaccion.setMoneda(moneda);
        transaccion.setFecha(new Date());
        transaccion.setConceptoOperacion(conceptoOperacion);
        transaccion.setDetalle(detalle);
        transaccion.setImporte(saldo);
        transaccion.setTipoOperacion(1);// 1 Entrada, 0 Salida
        transaccion.setEstadoId(2);// -1 Rechazada 0 Pendiente 2 Aprobada
        transaccion.setDeCuentaId(cuenta.getCuentaId());
        transaccion.setDeUsuarioId(billetera.getPersona().getUsuario().getUsuarioId());
        transaccion.setaUsuarioId(billetera.getPersona().getUsuario().getUsuarioId());
        transaccion.setaCuentaId(cuenta.getCuentaId());
        
        cuenta.agregarTransaccion(transaccion);

        BigDecimal saldoActual = cuenta.getSaldo();
        BigDecimal saldoNuevo = saldoActual.add(saldo);
        cuenta.setSaldo(saldoNuevo);

        this.grabar(billetera);
    }

    


    /**
     * Metodo enviarSaldo 
     * buscar billetera por id 
     * se identifica cuenta por moneda
     * determinar importe a transferir
     * billetera de origen y billetera destino
     * actualizar los saldos de las cuentas (resta en la origen y suma en la destino)
     * generar 2 transacciones
     * 
     * ver delegaciones sobre entidades
     * 
     */

     /**
      * Metodo consultarSaldo
      * buscar billetera por id
      * se identifica cuenta por moneda
      * traer saldo
      */


     
}