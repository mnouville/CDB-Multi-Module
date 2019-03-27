package springconfig;

import org.springframework.beans.factory.annotation.Autowired;
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
  
    @Autowired
    private CdbAuthentificationProvider authProvider;
  
    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
      auth.authenticationProvider(authProvider);
    }
 
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
      http
      .csrf().disable()
      .authorizeRequests()
      .antMatchers("/EditComputer*","/AddComputer*").hasRole("ADMIN")
      .antMatchers("/login*", "/logout").permitAll() 
      .anyRequest().authenticated()
      .and()
      .formLogin()  
      .defaultSuccessUrl("/", true)
      .and()
      .logout()
      .logoutUrl("/logout")
      .deleteCookies("JSESSIONID");
    }
     
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
