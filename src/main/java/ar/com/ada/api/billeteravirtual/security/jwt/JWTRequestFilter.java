package ar.com.ada.api.billeteravirtual.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import ar.com.ada.api.billeteravirtual.services.JWTUserDetailsService;
import io.jsonwebtoken.ExpiredJwtException;

/**
 * Este es un filtro que se ejectue en cada request UNA sola ves(OncePerRequest)
 */
@Component
public class JWTRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JWTUserDetailsService jwtUserDetailsService;

    @Autowired
    private JWTTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)

            throws ServletException, IOException {

        final String requestTokenHeader = request.getHeader("Authorization");

        String username = null;

        String jwtToken = null;

        // JWT Token viene de la forma "Bearer token". Remover el texto Bearer y obtener
        // solo el Token
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {

            jwtToken = requestTokenHeader.substring(7);

            try {
                username = jwtTokenUtil.getUsernameFromToken(jwtToken);
            } catch (IllegalArgumentException e) {

                System.out.println("Unable to get JWT Token");

            } catch (ExpiredJwtException e) {

                System.out.println("JWT Token has expired");

            }

        } else {

            logger.warn("JWT Token does not begin with Bearer String");

        }

        // una vez obtenido, validarlo.
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);

            // si el token es valido, configurar Spring Security para poner la autenticacion
            // manualmente

            if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(

                        userDetails, null, userDetails.getAuthorities());

                usernamePasswordAuthenticationToken

                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Despues de setear el Authentication en el contexto, especificaremos
                // que el usuario esta autenticado. y se pasa a
                // Spring Security Configurations en forma exitosa.

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

            }

        }

        chain.doFilter(request, response);

    }

}