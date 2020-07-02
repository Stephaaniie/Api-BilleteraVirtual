package ar.com.ada.api.billeteravirtual.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import ar.com.ada.api.billeteravirtual.entities.Billetera;
import ar.com.ada.api.billeteravirtual.entities.Cuenta;
import ar.com.ada.api.billeteravirtual.models.response.SaldoResponse;
import ar.com.ada.api.billeteravirtual.services.BilleteraService;

@RestController
public class BilleteraController {

    @Autowired
    BilleteraService billeteraService;

    /*
     * webMetodo 1: consultar saldo: GET URL:/billeteras/{id}/saldos/{moneda}
     * webMetodo 2: cargar saldo: POST URL:/billeteras/{id}/recargas requestBody: {
     * "moneda": "importe": } webMetodo 3:
     * 
     * enviar saldo: POST URL:/billetera/{id}/envios requestBody: { "moneda":
     * "importe": "email": "motivo": "detalleDelMotivo": }
     */

    @GetMapping("/billeteras/{id}/saldos/{moneda}")
    public ResponseEntity<?> consultarSaldo(@PathVariable Integer id, @PathVariable String moneda) {

        SaldoResponse saldo = new SaldoResponse();

        saldo.saldo = billeteraService.consultarSaldo(id, moneda);
        saldo.moneda = moneda;

        return ResponseEntity.ok(saldo);
    }

    @GetMapping("/billeteras/{id}/saldos")
    public ResponseEntity<List<SaldoResponse>> consultarSaldo(@PathVariable Integer id) {

        Billetera billetera = new Billetera();

        billetera = billeteraService.buscarPorId(id);

        List<SaldoResponse> saldos = new ArrayList<>();

        for (Cuenta cuenta : billetera.getCuentas()) {

            SaldoResponse saldo = new SaldoResponse();

            saldo.saldo = cuenta.getSaldo();
            saldo.moneda = cuenta.getMoneda();
            saldos.add(saldo);
        }
        return ResponseEntity.ok(saldos);
    }
}