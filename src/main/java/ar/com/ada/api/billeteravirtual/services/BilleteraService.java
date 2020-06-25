package ar.com.ada.api.billeteravirtual.services;

import org.springframework.stereotype.Service;

@Service
public class BilleteraService {
    
    /* 1.Metodo: Cargar saldo
    1.1-- Recibir un importe, se busca una billetera por id,
    se busca una cuenta por la moneda
    1.2-- hacer transaccion 
    1.3-- actualizar el saldo de la billetera */

    /* 2. Metodo: enviar plata
    2.1-- recibir un importe, la moneda en la que va a estar ese importe
    recibir una billetera de origen y otra de destino
    2.2-- actualizar los saldos de las cuentas (a una se le suma y a la otra se le resta)
    2.3-- generar dos transacciones
    */

    /* 3. Metodo: consultar saldo 
    3.1-- recibir el id de la billetera y la moneda en la que esta la cuenta
    */
}