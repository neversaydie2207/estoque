package com.fullstack.sic.security;

import java.io.IOException;
import java.util.Properties;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.ui.velocity.VelocityEngineFactoryBean;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter
{

	@Bean
	public AppUserDetailsService userDetailService()
	{
		return new AppUserDetailsService();
	}
	
	@Bean
	public VelocityEngine velocityEngine() throws VelocityException, IOException
	{
		VelocityEngineFactoryBean factory = new VelocityEngineFactoryBean();
		Properties props = new Properties();
		props.put("resource.loader", "class");
		props.put("class.resource.loader.class",
				  "org.apache.velocity.runtime.resource.loader." +
				  "ClasspathResourceLoader");
		
		factory.setVelocityProperties(props);

		return factory.createVelocityEngine();
	}
	

	@Override
	protected void configure(HttpSecurity http) throws Exception
	{
		// Configura Formulario de Autenticacao
		JsfLoginUrlAuthenticationEntryPoint jsfLoginEntry = new JsfLoginUrlAuthenticationEntryPoint();
		jsfLoginEntry.setLoginFormUrl("/login.jsf");
		jsfLoginEntry.setRedirectStrategy(new JsfRedirectStrategy());
		
		
		//Configura Pagina de Acesso Negado
		JsfAccessDeniedHandler jsfDaniedEntry = new JsfAccessDeniedHandler();
		jsfDaniedEntry.setLoginPath("/acesso-negado.jsf");
		jsfDaniedEntry.setContextRelative(true);
		
		http
			.csrf().disable()
			.headers().frameOptions().sameOrigin()
			.and()
			
			.authorizeRequests()
				.antMatchers("/login.jsf", "/solicitar-senha.jsf", "/novasenha.jsf",  "/cadastro-usuario.jsf", "/validaremail.jsf", 
						"/validarcontrato.jsf", "/resources/**", "/javax.faces.resource/**").permitAll()
				
				.antMatchers("/selecao_perfil.jsf", "/acesso-negado.jsf").authenticated()
				.antMatchers("/dashboard_visitante.jsf").hasAnyRole("VISITANTES") 
				.antMatchers("/gestaoadministrativa/**","/cadastro/**").hasAnyRole("ADMINISTRADORES", "TECNICOS", "DIGITADORES", "MASTERS")
				.antMatchers("/administracao/**", "/dashboard.jsf","/cadastro/**").hasAnyRole("ADMINISTRADORES", "MASTERS")
				.antMatchers("/relatorio/**").hasAnyRole("MASTERS") 
				.antMatchers("/**").denyAll()
				.and()
			
			.formLogin()
				.loginPage("/login.jsf")
				.failureUrl("/login.jsf?invalid=true")
				.successHandler(new MyAuthSuccessHandler())
				.and()
			
			.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.and()
				
			.exceptionHandling()
				.accessDeniedPage("/acesso-negado.jsf")
				.authenticationEntryPoint(jsfLoginEntry)
				.accessDeniedHandler(jsfDaniedEntry);
				
	}	
	

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception 
	{ 
		auth.userDetailsService(userDetailService()).passwordEncoder(new BCryptPasswordEncoder());
	}
	
}
