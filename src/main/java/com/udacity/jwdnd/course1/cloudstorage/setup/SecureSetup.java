package com.udacity.jwdnd.course1.cloudstorage.setup;

import com.udacity.jwdnd.course1.cloudstorage.services.Authenticator;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
@AllArgsConstructor
public class SecureSetup extends WebSecurityConfigurerAdapter {

 private Authenticator authenticator;
 @Override
 protected void configure(AuthenticationManagerBuilder auth){
  auth.authenticationProvider(this.authenticator);
 }

 @Override
 protected void configure(HttpSecurity http) throws Exception{
  http.authorizeRequests()
          .antMatchers("/signup", "/login", "/css/**", "/js/**")
          .permitAll()
          .anyRequest()
          .authenticated()
          .and()
          .logout()
          .invalidateHttpSession(true)
          .clearAuthentication(true)
          .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
          .invalidateHttpSession(true)
          .deleteCookies("JSESSIONID")
          .logoutSuccessUrl("/login?logout");;

  http.formLogin()
          .loginPage("/login")
          .permitAll()
          .defaultSuccessUrl("/home", true);
 }

}

