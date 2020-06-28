package ar.com.ada.api.billeteravirtual.security.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private UserDetailsService jwtUserDetailsService;

    @Autowired
    private JWTRequestFilter jwtRequestFilter;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        // configure AuthenticationManager so that it knows from where to load

        // user for matching credentials

        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());

    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();

    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {

        return super.authenticationManagerBean();

    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

        // No necesitamos CSRF para este ejemplo ya que somos API Rest y no tenemos ni cookies
        //ni session
        httpSecurity.csrf().disable()
                .cors().and() //activamos cors! para que pueda ser llamado desde otro dominio
                // no autenticar estos request particulares ya que pueden ingresar sin
                // autenticacion

                .authorizeRequests().antMatchers("/auth/*").permitAll().
                // .antMatchers("/auth/register").permitAll().

                // todos los otros requests deben ser autenticados

                anyRequest().authenticated().and().

                // asegurarse de usar stateless session(sesiones sin estado); la sesion no se
                // usara

                // guardar el estado del usuario.

                exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()

                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // Agrego el filter(filtro) para validar los tokens en cada request

        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

    }

}