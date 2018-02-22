package br.com.springboot.controleorcamento.security;

import br.com.springboot.controleorcamento.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig{

	private UserService userService;

	private AccessDeniedHandler accessDeniedHandler;

	@Autowired
	public SecurityConfig(UserService userService, AccessDeniedHandler accessDeniedHandler) {
		this.userService = userService;
		this.accessDeniedHandler = accessDeniedHandler;
	}

	@Configuration
	public class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
		protected void configure(HttpSecurity http) throws Exception {
			http.csrf().disable()
					.authorizeRequests()
					.antMatchers(HttpMethod.POST,"/*/user/**").permitAll()
					.anyRequest().authenticated()
					.and()
					.addFilter(new JWTAuthenticationFilter(authenticationManager()))
					.addFilter(new JWTAuthorizationFilter(authenticationManager(), userService));
		}

		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());
		}

		@Override
		public void configure(WebSecurity web) throws Exception {
			web.ignoring().antMatchers("/v2/api-docs","/**.html", "/configuration/ui", "/swagger-resources/**", "/configuration/**", "/swagger-ui.html", "/h2-console/**");
		}
	}

	@Configuration
	@Order(1)
	public class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.antMatcher("/site/**").csrf().disable()
					.authorizeRequests()
					.anyRequest().authenticated()
					.and()
					.formLogin()
					.loginPage("/site/login")
					.permitAll()
					.and()
					.logout()
					.permitAll()
					.and()
					.exceptionHandling().accessDeniedHandler(accessDeniedHandler);
		}

		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());
		}

		@Override
		public void configure(WebSecurity web) throws Exception {
			web.ignoring().antMatchers("/v2/api-docs","/**.html", "/configuration/ui", "/swagger-resources/**", "/configuration/**", "/swagger-ui.html", "/webjars/**", "/h2-console/**");
		}
	}



}






