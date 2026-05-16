package Security.Spring.Revision.Config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password4j.BcryptPassword4jPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collection;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class WebConfig {

    @Bean
    SecurityFilterChain getSecurityFilterChain(HttpSecurity httpsecurity){

        httpsecurity.authorizeHttpRequests((req)->{
            req.requestMatchers("/User/Login").permitAll();
           //req.anyRequest().authenticated();
        })
                .httpBasic(Customizer.withDefaults())
                //csrf(x=>x.disable)
                .csrf(CsrfConfigurer::disable)
                .formLogin(Customizer.withDefaults())
                // to stop using/creating Sessions.
                .sessionManagement(x->x.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        ;

        return httpsecurity.build();
    }

    @Bean
    UserDetailsService getuserdetailsservice(){
      User user1 = new User("Beni",getpasswordencoder().encode("Beni"),Collections.singleton(()->"User"));
      User user2 = new User("Arpita",getpasswordencoder().encode("Arpita"),Collections.singleton(()->"User"));
      return new InMemoryUserDetailsManager(user1,user2);
    }

    @Bean
    PasswordEncoder getpasswordencoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
     public AuthenticationManager getauthmanager(AuthenticationConfiguration authconfig){
            return authconfig.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider getauthenticationprovider(){
        DaoAuthenticationProvider authprovder = new DaoAuthenticationProvider(getuserdetailsservice());
        authprovder.setPasswordEncoder(getpasswordencoder());
        return authprovder;
    }
}
