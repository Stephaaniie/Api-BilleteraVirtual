package ar.com.ada.api.billeteravirtual.security.jwt;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Utilidad para generar y validar JWT(Json Web Tokens)
 */
@Component
public class JWTTokenUtil implements Serializable {

    private static final long serialVersionUID = -2550185165626007488L;

    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    // Clave secreta storeada en el application.properties
    @Value("${jwt.secret}")
    private String secret;

    // Obtiene el username del token(o sea el claim subject)
    public String getUsernameFromToken(String token) {

        return getClaimFromToken(token, Claims::getSubject);

    }

    // obtiene la expiracion del JWT
    public Date getExpirationDateFromToken(String token) {

        return getClaimFromToken(token, Claims::getExpiration);

    }

    // Obtiene la lista de claims(propositos)
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {

        final Claims claims = getAllClaimsFromToken(token);

        return claimsResolver.apply(claims);

    }

    // para traer cualquier informacion del token necesitamos la clave secreta
    private Claims getAllClaimsFromToken(String token) {

        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();

    }

    // checkea si el token expiro
    private Boolean isTokenExpired(String token) {

        final Date expiration = getExpirationDateFromToken(token);

        return expiration.before(new Date());

    }

    // genera un token en base a los datos de la interface UserDetails
    public String generateToken(UserDetails userDetails) {

        Map<String, Object> claims = new HashMap<>();

        return doGenerateToken(claims, userDetails.getUsername());

    }

    // mientras se crea el token -
    // 1. Definir claims(propositos) del token, como Issuer(quien lo emite),
    // Expiration(Expiracion), Subject(usuario/username), and the ID (id usuario)
    // 2. Firmar el JWT usando el algoritmo HS512 y la clave secreta.
    // 3. Deacuerdo a Compact
    // Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
    // se compacta el JWT a una un string URL-safe (URL-prudente)

    private String doGenerateToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))

                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))

                .signWith(SignatureAlgorithm.HS512, secret).compact();

    }

    // valida el token
    public Boolean validateToken(String token, UserDetails userDetails) {

        final String username = getUsernameFromToken(token);

        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));

    }

}