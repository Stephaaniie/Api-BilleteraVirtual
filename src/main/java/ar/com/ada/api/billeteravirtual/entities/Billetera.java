package ar.com.ada.api.billeteravirtual.entities;

import java.util.*;

import javax.persistence.*;

@Entity
@Table(name = "billetera")
public class Billetera {
    
    @Id
    @Column(name = "billetera_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer billeteraId;
    @OneToOne
    @JoinColumn(name = "persona_id",referencedColumnName = "persona_id")
    private Persona persona;
    @OneToMany(mappedBy = "billetera",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Cuenta> cuentas = new ArrayList<>();

	public Integer getBilleteraId() {
		return billeteraId;
	}

	public void setBilleteraId(Integer billeteraId) {
		this.billeteraId = billeteraId;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public List<Cuenta> getCuentas() {
		return cuentas;
	}

	public void setCuentas(List<Cuenta> cuentas) {
		this.cuentas = cuentas;
	}   

    // se crea este metodo para hacer la relacion bidireccional y agregar la cuenta a la billetera
    public void agregarCuenta(Cuenta cuenta){
        this.cuentas.add(cuenta);
        cuenta.setBilletera(this);
    }

    public Cuenta getCuenta(String moneda){

        for(Cuenta cuenta: this.cuentas){

            if(cuenta.getMoneda().equals(moneda)){
                
                return cuenta;
            }
        }
        return null;
    }

}