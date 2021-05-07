package ma.emsi.tp_jpa.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder passwordEncoder = passwordEncoder();
        // just for the test those user are stocked in memory for testing
        auth.inMemoryAuthentication().withUser("user1").password(passwordEncoder.encode("1234")).roles("USER");
        auth.inMemoryAuthentication().withUser("user2").password(passwordEncoder.encode("1234")).roles("USER");
        auth.inMemoryAuthentication().withUser("admin").password(passwordEncoder.encode("1234")).roles("USER", "ADMIN");

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //http.formLogin().loginPage("/login");
        http.formLogin();

        // for any path that has /save** and /delete  only users that has the role admin can accees to them
        http.authorizeRequests().antMatchers("/save**/**", "/delete**/**", "/formPatient", "/editPatient").hasRole("ADMIN");

        // let only yhe page index public people can access to it without authentication
        http.authorizeRequests().antMatchers("/index").permitAll();

        // all pages before access to them need to be authenticated
        http.authorizeRequests().anyRequest().authenticated();

        // http.csrf() this method used to defeat csrf attack but when u r using ResfulApi you should disable it;

        http.exceptionHandling().accessDeniedPage("/notAthorized");
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
