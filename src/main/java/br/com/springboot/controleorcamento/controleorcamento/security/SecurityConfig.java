package br.com.springboot.controleorcamento.controleorcamento.security;

import br.com.springboot.controleorcamento.controleorcamento.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import static br.com.springboot.controleorcamento.controleorcamento.security.SecurityConstants.SIGN_UP_URL;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private UsuarioService usuarioService;



	@Override
	public void configure(HttpSecurity http) throws Exception{
		http.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues())
				.and().csrf().disable()
				.authorizeRequests()
				//.antMatchers(HttpMethod.GET,SIGN_UP_URL).permitAll()
               // .antMatchers("v1/gastos/protected").access("hasRole('ROLE_USER')")
               // .antMatchers("/*/admin/**").hasRole("ADMIN")
                //.antMatchers(/*/user/**"").permitAll()
                //.antMatchers(HttpMethod.OPTIONS).permitAll()
				.antMatchers(HttpMethod.POST,"/*/user/**").permitAll()
				.anyRequest().authenticated()
                .and()
				.addFilter(new JWTAuthenticationFilter(authenticationManager()))
				.addFilter(new JWTAuthorizationFilter(authenticationManager(),usuarioService));
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(usuarioService).passwordEncoder(new BCryptPasswordEncoder());
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**", "/configuration/**", "/swagger-ui.html", "/webjars/**", "/h2-console/**");
	}
}
