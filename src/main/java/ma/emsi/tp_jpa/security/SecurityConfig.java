package ma.emsi.tp_jpa.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder passwordEncoder = passwordEncoder();
        // this method used for production username and password are stocker in sal table
        auth.jdbcAuthentication()
                .dataSource(dataSource) // dataSource bring the connection config to database that has the user table for login
                //The Query that will bring the login value such as password and username but always make an alias for the username to be
                // 'principal' and password 'credentials'
                .usersByUsernameQuery("SELECT username as principal, password as credentials, active FROM users where username=?")
                // the query that zill bring the role for every user
                .authoritiesByUsernameQuery("SELECT username as principal, role as role FROM users_role where username=?")
                // passing the algorithm that used to enCrypt the password
                .passwordEncoder(passwordEncoder)
                // defined the role prefix exemple if we put in the database role admin aafter the prefix it will be 'ROLE_ADMIN'
                .rolePrefix("ROLE_");

       /* // just for the test those user are stocked in memory for testing
        auth.inMemoryAuthentication().withUser("user1").password(passwordEncoder.encode("1234")).roles("USER");
        auth.inMemoryAuthentication().withUser("user2").password(passwordEncoder.encode("1234")).roles("USER");
        auth.inMemoryAuthentication().withUser("admin").password(passwordEncoder.encode("1234")).roles("USER", "ADMIN");
        */


    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().loginPage("/login"); // customize the login form
        /* http.formLogin(); default login form*/

        // for any path that has /save** and /delete  only users that has the role admin can accees to them
        http.authorizeRequests().antMatchers("/save**/**", "/delete**/**", "/formPatient", "/editPatient").hasRole("ADMIN");

        // let only yhe page index public people can access to it without authentication
        http.authorizeRequests().antMatchers("/index","/login/**","/webjars/**", "/login").permitAll();
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
