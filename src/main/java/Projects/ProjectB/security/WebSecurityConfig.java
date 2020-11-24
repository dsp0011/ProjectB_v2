package Projects.ProjectB.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) throws Exception {
        // Permit the following CRUD operations on the specified endpoints.
        // TODO: Should be restricted before production.
        web.ignoring().antMatchers(HttpMethod.POST, "/**");
        web.ignoring().antMatchers(HttpMethod.PUT, "/**");
        web.ignoring().antMatchers(HttpMethod.DELETE, "/**");
        web.ignoring().antMatchers(HttpMethod.GET, "/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().cors().and();
        http
                .authorizeRequests()
                    // Specify web pages that are open to all users.
                    // TODO: Update to represent actual web pages before production.
                    .antMatchers("/",
                            "/index",
                            "/chat",
                            "/home",
                            "/users",
                            "/polls",
                            "/userPolls",
                            "/devices",
                            "/votes").permitAll()
                    .anyRequest().authenticated()
                    .and()
                .formLogin();
        /*
                    // Specify a login page, which unauthorized users are
                    // redirected to and is viewable by anyone.
                    .loginPage("/login").permitAll()
                    .and()
                .logout().permitAll(); // Logout page is open for all.
         */
    /*    http
                // Requires that HTTPS be used when sending requests.
                .requiresChannel()
                .anyRequest()
                .requiresSecure();
     */
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        // An in memory user, which can be used for testing purposes.
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        UserDetails user = User.builder()
                .passwordEncoder(encoder::encode)
                .username("user")
                .password("password")
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }
}
