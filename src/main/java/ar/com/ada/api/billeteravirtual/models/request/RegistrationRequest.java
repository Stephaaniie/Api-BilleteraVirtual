package ar.com.ada.api.billeteravirtual.models.request;

import java.util.Date;

/**
 * RegistrationRequest
 */
public class RegistrationRequest {

    public String fullName; //Nombre persona
    public int country; //pais del usuario
    public int identificationType; //Tipo Documento
    public String identification; //nro documento
    public Date birthDate; //fechaNacimiento
    public String email; //email
    public String password; //contraseña elegida por el usuario.
    
}