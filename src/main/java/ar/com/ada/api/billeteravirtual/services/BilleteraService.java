package ar.com.ada.api.billeteravirtual.services;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.billeteravirtual.entities.*;
import ar.com.ada.api.billeteravirtual.repositories.BilleteraRepository;

@Service
public class BilleteraService {

    @Autowired
    BilleteraRepository billeteraRepository;

    /*
     * 2. Metodo: enviar plata 2.1-- recibir un importe, la moneda en la que va a
     * estar ese importe recibir una billetera de origen y otra de destino 2.2--
     * actualizar los saldos de las cuentas (a una se le suma y a la otra se le
     * resta) 2.3-- generar dos transacciones
     */

    /*
     * 3. Metodo: consultar saldo 3.1-- recibir el id de la billetera y la moneda en
     * la que esta la cuenta
     */
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

    public BigDecimal consultarSaldo(Integer billeteraId, String moneda) {

        Billetera billetera = billeteraRepository.findByBilleteraId(billeteraId);

        Cuenta cuenta = billetera.getCuenta(moneda);

        return cuenta.getSaldo();

    }

    public Billetera buscarPorId(Integer id) {

        return billeteraRepository.findByBilleteraId(id);
    }

    public void enviarSaldo(BigDecimal importe, String moneda, Integer billeteraOrigenId, Integer billeteraDestinoId, String concepto, String detalle) {

        /*
         * Metodo: enviar plata 2.1-- recibir un importe, la moneda en la que va a estar
         * ese importe recibir una billetera de origen y otra de destino 2.2--
         * actualizar los saldos de las cuentas (a una se le suma y a la otra se le
         * resta) 2.3-- generar dos transacciones
         */

        Billetera billeteraSaliente = this.buscarPorId(billeteraOrigenId);
        Billetera billeteraEntrante = this.buscarPorId(billeteraDestinoId);

        Cuenta cuentaSaliente = billeteraSaliente.getCuenta(moneda);
        Cuenta cuentaEntrante = billeteraEntrante.getCuenta(moneda);

        Transaccion tSaliente = new Transaccion();
        Transaccion tEntrante = new Transaccion();

        tSaliente = cuentaSaliente.generarTransaccion(concepto, detalle, importe, 1);
        tSaliente.setaCuentaId(cuentaEntrante.getCuentaId());
        tSaliente.setaUsuarioId(billeteraEntrante.getPersona().getUsuario().getUsuarioId());

        tEntrante = cuentaEntrante.generarTransaccion(concepto, detalle, importe, 0);
        tEntrante.setDeCuentaId(cuentaSaliente.getCuentaId());
        tEntrante.setDeUsuarioId(billeteraSaliente.getPersona().getUsuario().getUsuarioId());

        cuentaSaliente.agregarTransaccion(tSaliente);
        cuentaEntrante.agregarTransaccion(tEntrante);


    }
}