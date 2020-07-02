package ar.com.ada.api.billeteravirtual.entities;

import java.math.BigDecimal;
import java.util.*;

import javax.persistence.*;

@Entity
@Table(name = "cuenta")
public class Cuenta {

	@Id
	@Column(name = "cuenta_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cuentaId;
    private BigDecimal saldo;
    private String moneda;
	@ManyToOne
	@JoinColumn(name = "billetera_id",referencedColumnName = "billetera_id")
    private Billetera billetera;
	@OneToMany(mappedBy = "cuenta",cascade = CascadeType.ALL)
    private List<Transaccion> transacciones = new ArrayList<>();

	public Integer getCuentaId() {
		return cuentaId;
	}

	public void setCuentaId(Integer cuentaId) {
		this.cuentaId = cuentaId;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public String getMoneda() {
		return moneda;
	}

	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}

	public Billetera getBilletera() {
		return billetera;
	}

	public void setBilletera(Billetera billetera) {
		this.billetera = billetera;
	}

	public List<Transaccion> getTransacciones() {
		return transacciones;
	}

	public void setTransacciones(List<Transaccion> transacciones) {
		this.transacciones = transacciones;
	}

    // Se hace relacion bidireccional con este metodo
    public void agregarTransaccion(Transaccion transaccion){
        this.transacciones.add(transaccion);
        transaccion.setCuenta(this);
		
 	BigDecimal saldoActual = this.getSaldo();
		if (transaccion.getTipoOperacion().equals(1)) {

			BigDecimal saldoNuevo = saldoActual.add(saldo);
			this.setSaldo(saldoNuevo);
		} else {

			BigDecimal saldoNuevo = saldoActual.subtract(saldo);
			this.setSaldo(saldoNuevo);
		}

	}

	public Transaccion generarTransaccion(String conceptoOperacion, String detalle, BigDecimal importe,
			Integer tipoOp) {

		Transaccion transaccion = new Transaccion();

		transaccion.setMoneda(moneda);
		transaccion.setFecha(new Date());
		transaccion.setConceptoOperacion(conceptoOperacion);
		transaccion.setDetalle(detalle);
		transaccion.setImporte(importe);
		transaccion.setTipoOperacion(tipoOp);// 1 Entrada, 0 Salida
		transaccion.setEstadoId(2);// -1 Rechazada 0 Pendiente 2 Aprobada

		if (transaccion.getTipoOperacion() == 1) { // Es de entrada

			transaccion.setaUsuarioId(billetera.getPersona().getUsuario().getUsuarioId());
			transaccion.setaCuentaId(this.getCuentaId());
		} else {
			// Es de salida
			transaccion.setDeCuentaId(this.getCuentaId());
			transaccion.setDeUsuarioId(billetera.getPersona().getUsuario().getUsuarioId());
		}

		return transaccion;
	}

}