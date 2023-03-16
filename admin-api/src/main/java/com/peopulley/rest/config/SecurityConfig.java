package com.peopulley.rest.config;

import com.peopulley.rest.base.service.UserService;
import com.peopulley.rest.common.constants.Constants;
import com.peopulley.rest.common.filter.JwtRequestFilter;
import com.peopulley.rest.common.handler.CustomAccessDeniedHandler;
import com.peopulley.rest.common.handler.CustomAuthenticationEntryPoint;
import com.peopulley.rest.util.CookieUtil;
import com.peopulley.rest.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    private final JwtUtil jwtUtil;

    private final UserService userService;

    private final CookieUtil cookieUtil;


    @Override // ignore check swagger resource
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(Constants.SECURITY_WEB_IGNORE_URLS);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable()
                .authorizeRequests()
                    .antMatchers(Constants.SECURITY_PERMIT_ALL).permitAll()
//                .antMatchers(Constants.SECURITY_WEB_IGNORE_URLS).permitAll()

                .anyRequest().authenticated()
                .and()
                .csrf().disable()
                .cors().configurationSource(corsConfigurationSource())
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(customAuthenticationEntryPoint)
                .accessDeniedHandler(customAccessDeniedHandler)
                .and()
                .addFilterBefore(new JwtRequestFilter(jwtUtil, userService, cookieUtil), UsernamePasswordAuthenticationFilter.class)
        ;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        /*CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowCredentials(true);
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT"));

        if (BaseCommonUtil.isNotStageProdProfile(PropertiesValue.profilesActive)) {
            configuration.addAllowedOrigin("*");
            configuration.addAllowedHeader("*");
        }else{
            configuration.addAllowedOrigin("*");
            configuration.addAllowedHeader("*");
        }

        source.registerCorsConfiguration("/**", configuration);*/

        return source;
    }
}