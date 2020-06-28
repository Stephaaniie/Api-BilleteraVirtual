package ar.com.ada.api.billeteravirtual.controllers;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class BilleteraController {

   /* web metodo 1:
            consultar saldo GET
            URL:/billetera/{id}/saldos


      web metodo 2:
            cargar saldo: POST
            URL:/billeteras/{id}/recargas
            RequestBody: moneda, importe
    
     web metodo 3:
            enviar plata: POST
            URL:/billeteras/{id}/envios
            RequestBody: moneda, importe, email, motivo, detalleMotivo

   */
    
}