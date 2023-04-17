package ma.emsi.jpatp.SECURITY;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)


public class SecurityConfig {
    @Autowired

    private PasswordEncoder passwordEncoder;



    //pour definir des users qui permettent d'cceder a l'app
    // InMemory : je precise en memoire les usrrs qui peuvent acceder a l'app

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager(){
        return new InMemoryUserDetailsManager(

                //determiner les utilisateurs qui nont le droit de se connecter avec des mdp mais spring security va hashé les mdp et donc sans le {noop} le mdp va pas marché

                User.withUsername("user1").password(passwordEncoder.encode("1234")).roles("USER").build(), // on a jouté passwordEncoder.encode(mdp) pour que le mdp soit stocké en memoire crypté
                User.withUsername("user2").password(passwordEncoder.encode("1234")).roles("USER").build(),
                User.withUsername("admin").password(passwordEncoder.encode("1234")).roles("USER","ADMIN").build()

        );
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)
    throws Exception{
        //ajouter un formulaire d'authentification
        httpSecurity.formLogin().loginPage("/login").permitAll();
        httpSecurity.rememberMe();
        httpSecurity.authorizeHttpRequests().requestMatchers("/webjars/**","*/h2-console/**").permitAll();
        //httpSecurity.authorizeHttpRequests().requestMatchers("/user/**").hasRole("USER");
        //httpSecurity.authorizeHttpRequests().requestMatchers("/admin/**").hasRole("ADMIN");


        httpSecurity.authorizeHttpRequests().anyRequest().authenticated(); // toutes les requetes necessitent une authentification.
        httpSecurity.exceptionHandling().accessDeniedPage("/nottAuthorized");



        return httpSecurity.build();

    }
}
