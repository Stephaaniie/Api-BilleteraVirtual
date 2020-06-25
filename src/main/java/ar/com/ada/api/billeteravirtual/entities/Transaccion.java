package ar.com.ada.api.billeteravirtual.entities;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "transaccion")
public class Transaccion {

	@Id
	@Column(name = "transaccion_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer transaccionId;
	@ManyToOne
	@JoinColumn(name = "cuenta_id", referencedColumnName = "cuenta_id")
    private Cuenta cuenta;
    private Date fecha;
	@Column(name = "estado_id")
    private Integer estadoId;
    private BigDecimal importe;
    private String moneda;
	@Column(name = "tipo_operacion")
    private Integer tipoOperacion;
	@Column(name = "concepto_operacion")
    private String conceptoOperacion;
    private String detalle;
	@Column(name = "de_usuario")
    private Integer deUsuarioId;
	@Column(name = "a_usuario")
    private Integer aUsuarioId;
	@Column(name = "de_cuenta")
    private Integer deCuentaId;
	@Column(name = "a_cuenta")
    private Integer aCuentaId;

	public Integer getTransaccionId() {
		return transaccionId;
	}

	public void setTransaccionId(Integer transaccionId) {
		this.transaccionId = transaccionId;
	}

	public Cuenta getCuenta() {
		return cuenta;
	}

	public void setCuenta(Cuenta cuenta) {
		this.cuenta = cuenta;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Integer getEstadoId() {
		return estadoId;
	}

	public void setEstadoId(Integer estadoId) {
		this.estadoId = estadoId;
	}

	public BigDecimal getImporte() {
		return importe;
	}

	public void setImporte(BigDecimal importe) {
		this.importe = importe;
	}

	public String getMoneda() {
		return moneda;
	}

	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}

	public Integer getTipoOperacion() {
		return tipoOperacion;
	}

	public void setTipoOperacion(Integer tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}

	public String getConceptoOperacion() {
		return conceptoOperacion;
	}

	public void setConceptoOperacion(String conceptoOperacion) {
		this.conceptoOperacion = conceptoOperacion;
	}

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public Integer getDeUsuarioId() {
		return deUsuarioId;
	}

	public void setDeUsuarioId(Integer deUsuarioId) {
		this.deUsuarioId = deUsuarioId;
	}

	public Integer getaUsuarioId() {
		return aUsuarioId;
	}

	public void setaUsuarioId(Integer aUsuarioId) {
		this.aUsuarioId = aUsuarioId;
	}

	public Integer getDeCuentaId() {
		return deCuentaId;
	}

	public void setDeCuentaId(Integer deCuentaId) {
		this.deCuentaId = deCuentaId;
	}

	public Integer getaCuentaId() {
		return aCuentaId;
	}

	public void setaCuentaId(Integer aCuentaId) {
		this.aCuentaId = aCuentaId;
	}

}