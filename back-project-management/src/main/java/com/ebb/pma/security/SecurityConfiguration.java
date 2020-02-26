package com.ebb.pma.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	DataSource dataSource;
	
	@Autowired
	BCryptPasswordEncoder bCryptEncoder;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		/**auth.inMemoryAuthentication().withUser("myUser").password("pass").roles("USER").and().withUser("bamba").password("pass2").roles("USER").and().withUser("managerUser").password("pass3").roles("ADMIN");*/
		/**auth.jdbcAuthentication().dataSource(dataSource).withDefaultSchema().withUser("myUser").password("pass").roles("USER").and().withUser("bamba").password("pass2").roles("USER").and().withUser("managerUser").password("pass3").roles("ADMIN");*/
		/**auth.jdbcAuthentication().dataSource(dataSource)
		.usersByUsernameQuery("SELECT USERNAME, PASSWORD, ENABLED FROM USERS WHERE USERNAME=?")
		.authoritiesByUsernameQuery("SELECT USERNAME, AUTHORITY FROM AUTHORITIES WHERE USERNAME=?");*/
		
		auth.jdbcAuthentication()
		.usersByUsernameQuery("SELECT USERNAME, PASSWORD, ENABLED "
				+ "FROM USER_ACCOUNTS WHERE USERNAME=?")
		.authoritiesByUsernameQuery("SELECT USERNAME, ROLE "
				+ "FROM USER_ACCOUNTS WHERE USERNAME=?")
		.dataSource(dataSource).passwordEncoder(bCryptEncoder);
		
	}
	
	/**@Bean
	public PasswordEncoder getPasswordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}*/
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		/** http.authorizeRequests().anyRequest().authenticated().and().formLogin().and().httpBasic();*/
		/**.antMatchers("/h2-console/**").permitAll()*/
		/**.antMatchers("/employees/new").hasAuthority("ADMIN")*/
		http.authorizeRequests()
		//.antMatchers("/projects/new").hasRole("ADMIN")
		//.antMatchers("/projects/save").hasRole("ADMIN")
		//.antMatchers("/employees/new").hasRole("ADMIN")
		//.antMatchers("/employees/save").hasRole("ADMIN")
		.antMatchers("/", "/**").permitAll()  //.authenticated().and().formLogin();
		.and()
		.formLogin(); //.loginPage("login-page");
		
		http.csrf().disable();
		/**http.headers().frameOptions().disable();*/
	}

}
