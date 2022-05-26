package by.tms.dzen.yandexdzenrestc51.configuration.security;

import by.tms.dzen.yandexdzenrestc51.configuration.security.jwt.JWTConfig;
import by.tms.dzen.yandexdzenrestc51.configuration.security.jwt.JWTTokenProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

//@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
@PropertySource("classpath:endpoint.properties")
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final JWTTokenProvider jwtTokenProvider;

    @Value("${ADMIN_ENDPOINT}")
    private String ADMIN_ENDPOINT;

    @Value("${LOGIN_ENDPOINT}")
    private String LOGIN_ENDPOINT;

    @Value("${PUBLIC_URLS}")
    private String[] PUBLIC_URLS;

    @Value("${DB_H2_ENDPOINT}")
    private String DB_H2_ENDPOINT;

    @Value("${ADMIN_ROLE_NAME}")
    private String ADMIN_ROLE_NAME;


    public SecurityConfiguration(JWTTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(LOGIN_ENDPOINT).permitAll()
                .antMatchers(ADMIN_ENDPOINT).hasAuthority(ADMIN_ROLE_NAME)
                .antMatchers(HttpMethod.GET, PUBLIC_URLS).permitAll()
                .antMatchers(DB_H2_ENDPOINT).permitAll()
                .anyRequest().authenticated()
                .and()
                .apply(new JWTConfig(jwtTokenProvider));
        http
                .headers().frameOptions().sameOrigin();
    }
}
