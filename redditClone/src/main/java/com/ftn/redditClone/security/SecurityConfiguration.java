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
                .antMatchers(HttpMethod.GET, "/users/{id}").permitAll()
                .antMatchers(HttpMethod.GET, "/users/{id}/community/{communityId}").permitAll()
                .antMatchers(HttpMethod.GET, "/users").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.GET, "/users/{id}/karma").permitAll()
                .antMatchers(HttpMethod.POST, "/users/").permitAll()
                .antMatchers(HttpMethod.PUT, "/users/{id}").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.DELETE, "/users/").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.POST, "/users/login").permitAll()
                .antMatchers(HttpMethod.PUT, "/users/{id}/passwordChange/").permitAll()

                .antMatchers(HttpMethod.GET, "/community/").permitAll()

                //search
                .antMatchers(HttpMethod.GET, "/community/findByName/{name}").permitAll()
                .antMatchers(HttpMethod.GET, "/community/findByDesc/{desc}").permitAll()
                .antMatchers(HttpMethod.GET, "/community/findByRule/{rule}").permitAll()
                .antMatchers(HttpMethod.GET, "/community/findByName/{name}/{type}").permitAll()
                .antMatchers(HttpMethod.GET, "/community/findByDesc/{desc}/{type}").permitAll()
                .antMatchers(HttpMethod.GET, "/community/findByRangeOfPosts/{from}/{to}").permitAll()
                .antMatchers(HttpMethod.GET, "/community/findByRangeOfAverageKarma/{from}/{to}").permitAll()
                .antMatchers(HttpMethod.GET, "/community/findCommunitiesByMultipleValues").permitAll()








                .antMatchers(HttpMethod.GET, "/community/{id}").permitAll()
                .antMatchers(HttpMethod.GET, "/community/{id}/reports/{reportType}").permitAll()
                .antMatchers(HttpMethod.GET, "/community/{id}/posts/sort/{sortType}").permitAll()
                .antMatchers(HttpMethod.GET, "/community/{id}/suspend").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/community/{id}/posts").permitAll()
                .antMatchers(HttpMethod.GET, "/community/{id}/flairs").permitAll()
                .antMatchers(HttpMethod.POST, "/community/{id}/flairs").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.GET, "/community/{id}/rules").permitAll()
                .antMatchers(HttpMethod.POST, "/community/{id}/rules").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.DELETE, "/community/{id}/rules/{ruleId}").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.POST, "/community/").hasAnyRole("ADMIN",  "USER")
                .antMatchers(HttpMethod.PUT, "/community/{id}").hasAnyRole("ADMIN",  "USER")


//                .antMatchers(HttpMethod.DELETE, "/community/delete/").permitAll()
                .antMatchers(HttpMethod.GET, "/posts/").permitAll()
                .antMatchers(HttpMethod.GET, "/posts/{id}").permitAll()
                .antMatchers(HttpMethod.GET, "/posts/sort/{sortType}").permitAll()
                .antMatchers(HttpMethod.POST, "/posts/").hasAnyRole("ADMIN", "MODERATOR", "USER")
                .antMatchers(HttpMethod.PUT, "/posts/").hasAnyRole("ADMIN", "MODERATOR", "USER")
                .antMatchers(HttpMethod.GET, "/posts/{id}/reactions").permitAll()
                .antMatchers(HttpMethod.GET, "/posts/findAll/").permitAll()
                .antMatchers(HttpMethod.GET, "/posts/{id}/comments/").permitAll()
                .antMatchers(HttpMethod.GET, "/posts/{id}/comments/sort/{sortType}").permitAll()
                .antMatchers(HttpMethod.DELETE, "/posts/{id}").permitAll()
                .antMatchers(HttpMethod.POST, "/reactions/").hasAnyRole("ADMIN", "MODERATOR", "USER")
                .antMatchers(HttpMethod.GET, "/comments/").permitAll()
                .antMatchers(HttpMethod.GET, "/comments/{id}").permitAll()
                .antMatchers(HttpMethod.POST, "/comments/").hasAnyRole("ADMIN", "MODERATOR", "USER")
                .antMatchers(HttpMethod.GET, "/comments/{id}/reactions/").permitAll()
                .antMatchers(HttpMethod.POST, "/comments/{id}/reactions/").permitAll()
                .antMatchers(HttpMethod.DELETE, "/comments/{id}").permitAll()
                .antMatchers(HttpMethod.GET, "/flairs/{id}").permitAll()
                .antMatchers(HttpMethod.POST, "/reports").hasAnyRole("ADMIN", "MODERATOR", "USER")
                .antMatchers(HttpMethod.GET, "/moderators").permitAll()
                .antMatchers(HttpMethod.POST, "/moderators").permitAll()
                .antMatchers(HttpMethod.POST, "/moderators/{id}").permitAll()
                .antMatchers(HttpMethod.POST, "/banneds").permitAll()
                .antMatchers(HttpMethod.DELETE, "/banneds/{id}").permitAll()
                .anyRequest().authenticated();

        //sort/{sortType}

        httpSecurity.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
    }
}
