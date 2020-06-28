package ar.com.ada.api.billeteravirtual.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.billeteravirtual.entities.Persona;
import ar.com.ada.api.billeteravirtual.entities.Usuario;
import ar.com.ada.api.billeteravirtual.repos.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository repo;

    @Autowired
    PersonaService personaService;

    public Usuario buscarPorUsername(String username) {
        return null;
    }

    public void login(String username, String password) {
    }


    public Usuario crearUsuario(String nombre, int pais, int tipoDocumento, String documento, Date fechaNacimiento,
            String email, String password) {

        /*
         * 1.1-->Crear una Persona(setearle un usuario) 1.2-->crear un usuario
         * 1.3-->Crear una billetera(setearle una persona) 1.4-->Crear una cuenta en
         * pesos y otra en dolares
         */
        Persona persona = new Persona();
        persona.setNombre(nombre);
        persona.setPaisId(pais);
        persona.setTipoDocumentoId(tipoDocumento);
        persona.setDocumento(documento);
        persona.setFechaNacimiento(fechaNacimiento);

        Usuario usuario = new Usuario();
        usuario.setUsername(email);
        usuario.setEmail(email);
        usuario.setPassword(password);

        persona.setUsuario(usuario);

        personaService.grabar(persona);

        return usuario;
    }
}