package com.example.demo.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demo.security.jwt.filter.JwtFilter;
import com.example.demo.security.service.UserService;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserService userDetailsService;

	@Autowired
	private JwtFilter jwtFilter;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean(name = BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests()
				.antMatchers("/api/v1/signup", "/api/v1/mail/**", "/api/v1/userinfo", "/api/v1/login",
						"/api/v1/resources/**", "/api/v1/displayimg/resources/**",
						"/api/v1/signup/**", "/api/v1/search/**", "/api/v1/reset/password", "/api/v1/boardlist",
						"/api/v1/boardlist/**", "/api/v1/boardtext/**", "/api/v1/boardimg",
						"/api/v1/displayimg/board/**", "/api/v1/boardimgconnect/**", "/api/v1/comment/**", "/api/v1/comment",
						"/api/v1/commentlist/**", "/api/v1/RecommendRanking", "/api/v1/RecommendUp/**",
						"/api/v1/RecommendDown/**", "/api/v1/recommend/**", "/api/v1/scrap", "/api/v1/scrapdel/**", "/api/v1/scraplist/**",
						"/api/v1/closetimgupload", "/api/v1/displayimg/closet/**", "/api/v1/closetlist",
						"/api/v1/closet", "/api/v1/clothDelete/**", "/api/v1/clothimglist", "/api/v1/clothimgconnect",
						"/api/v1/clothimgupdate/**", "/api/v1/clothUpdate/**","/api/imgconnect/*")
				.permitAll().anyRequest().authenticated().and().exceptionHandling().and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
		;
	}
}