package ar.com.ada.api.billeteravirtual.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.com.ada.api.billeteravirtual.entities.Usuario;
import ar.com.ada.api.billeteravirtual.models.request.LoginRequest;
import ar.com.ada.api.billeteravirtual.models.request.RegistrationRequest;
import ar.com.ada.api.billeteravirtual.models.response.JwtResponse;
import ar.com.ada.api.billeteravirtual.models.response.RegistrationResponse;
import ar.com.ada.api.billeteravirtual.security.jwt.JWTTokenUtil;
import ar.com.ada.api.billeteravirtual.services.JWTUserDetailsService;
import ar.com.ada.api.billeteravirtual.services.UsuarioService;;

/**
 * AuthController
 */
@RestController
public class AuthController {

    @Autowired
    UsuarioService usuarioService;

    /*
     * @Autowired private AuthenticationManager authenticationManager;
     */
    @Autowired
    private JWTTokenUtil jwtTokenUtil;

    @Autowired
    private JWTUserDetailsService userDetailsService;
    //Auth : authentication ->
    @PostMapping("auth/register")
    public ResponseEntity<RegistrationResponse> postRegisterUser(@RequestBody RegistrationRequest req) {
        RegistrationResponse r = new RegistrationResponse();

        Usuario usuario = usuarioService.crearUsuario(req.fullName, req.country, req.identificationType, req.identification, req.birthDate, req.email, req.password);
        r.isOk = true;
        r.message = "Te registraste con exitoooo!!!!!!!";
        r.userId = usuario.getUsuarioId(); // <-- AQUI ponemos el numerito de id para darle a front!
        return ResponseEntity.ok(r);

    }

    @PostMapping("auth/login") // probando nuestro login
    public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginRequest authenticationRequest)
            throws Exception {

        usuarioService.login(authenticationRequest.username, authenticationRequest.password);

        UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.username);

        String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));

    }

}