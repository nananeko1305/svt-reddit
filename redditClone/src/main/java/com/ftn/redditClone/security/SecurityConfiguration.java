package com.ftn.redditClone.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

	@Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {

        authenticationManagerBuilder.userDetailsService(this.userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Bean
    @Lazy
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public AuthenticationTokenFilter authenticationTokenFilterBean()
            throws Exception {
        AuthenticationTokenFilter authenticationTokenFilter = new AuthenticationTokenFilter();
        authenticationTokenFilter
                .setAuthenticationManager(authenticationManagerBean());
        return authenticationTokenFilter;
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception{
        //Naglasavamo browser-u da ne cache-ira podatke koje dobije u header-ima
        //detaljnije: https://www.baeldung.com/spring-security-cache-control-headers
        httpSecurity.headers().cacheControl().disable();
        //Neophodno da ne bi proveravali autentifikaciju kod Preflight zahteva
        httpSecurity.cors();
        //sledeca linija je neophodna iskljucivo zbog nacina na koji h2 konzola komunicira sa aplikacijom
        httpSecurity.headers().frameOptions().disable();
        httpSecurity.csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
//                .antMatchers("/h2-console/**").permitAll()
                .antMatchers(HttpMethod.GET, "/users/").permitAll()
                .antMatchers(HttpMethod.GET, "/users/findAll/").permitAll()
                .antMatchers(HttpMethod.POST, "/users/").permitAll()
                .antMatchers(HttpMethod.PUT, "/users/").permitAll()
                .antMatchers(HttpMethod.DELETE, "/users/").permitAll()
                .antMatchers(HttpMethod.POST, "/users/login/").permitAll()
                .antMatchers(HttpMethod.PUT, "/users/passwordChange/").permitAll()
                .antMatchers(HttpMethod.GET, "/community/").permitAll()
                .antMatchers(HttpMethod.GET, "/community/findAll/").permitAll()
                .antMatchers(HttpMethod.POST, "/community/").permitAll()
                .antMatchers(HttpMethod.PUT, "/community/").permitAll()
                .antMatchers(HttpMethod.DELETE, "/community/delete/").permitAll()
                .antMatchers(HttpMethod.GET, "/posts/").permitAll()
                .antMatchers(HttpMethod.GET, "/posts/findAll/").permitAll()
                .antMatchers(HttpMethod.POST, "/posts/").permitAll()
                .antMatchers(HttpMethod.PUT, "/posts/").permitAll()
                .antMatchers(HttpMethod.DELETE, "/posts/").permitAll()
                .anyRequest().authenticated();

        httpSecurity.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
    }
}