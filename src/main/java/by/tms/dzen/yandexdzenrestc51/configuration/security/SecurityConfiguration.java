package by.tms.dzen.yandexdzenrestc51.configuration.security;

import by.tms.dzen.yandexdzenrestc51.configuration.security.jwt.JWTConfig;
import by.tms.dzen.yandexdzenrestc51.configuration.security.jwt.JWTTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final JWTTokenProvider jwtTokenProvider;
    private static final String ADMIN_ENDPOINT = "/api/admin/**";
    private static final String LOGIN_ENDPOINT = "/api/v1/auth/**";
    private static final String[] PUBLIC_URLS = {
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/swagger-ui/",
            "/swagger-ui",
            "/webjars/**",
            "/v3/api-docs/**",
            "configuration/**",
            "webjars/**",
            "/*.html",
            "/**/*.html",
            "/**/*.css",
            "/**/*.js"
    };

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
                .antMatchers(ADMIN_ENDPOINT).hasAuthority("ADMIN")
                .antMatchers(HttpMethod.GET, PUBLIC_URLS).permitAll()
                .antMatchers("/db/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .apply(new JWTConfig(jwtTokenProvider));
        http
                .headers().frameOptions().sameOrigin();
    }
}
