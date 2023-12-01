package org.sid.walletservice.security;

import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

public class KeycloakSecurityConfig extends KeycloakWebSecurityConfigurerAdapter {
    @Override
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        http.csrf().disable();
        http.cors();

        http.authorizeRequests().antMatchers("graphql/**").permitAll();
        http.authorizeRequests().antMatchers("graphiql/**").permitAll();

        http.authorizeRequests()
                .antMatchers("/h2-console/**","/swagger-ui/**","/v3/**",
                        "/public/**","/actuator.**").permitAll();

        http.headers().frameOptions().disable(); // desctive for allowing Frames

        http.authorizeRequests().anyRequest().authenticated(); // all request necesite authatication
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(keycloakAuthenticationProvider());
    }
}
