package Graded_Project.Employee_Management;


	import org.springframework.context.annotation.Bean;
	import org.springframework.context.annotation.Configuration;
	import org.springframework.security.authentication.AuthenticationProvider;
	import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
	import org.springframework.security.config.annotation.web.builders.HttpSecurity;
	import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
	import org.springframework.security.core.userdetails.UserDetailsService;
	import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
	import org.springframework.security.crypto.password.PasswordEncoder;
	import org.springframework.security.web.SecurityFilterChain;

import Graded_Project.Employee_Management.pojo.AppUserService;
	

	@Configuration
	//this is a class containing Security configuration 
	@EnableWebSecurity
	public class SecurityConfiguration {
		@Bean
		public SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception{
			http.csrf().disable().authorizeRequests().requestMatchers("/").authenticated().and().formLogin();
			return http.build();
		}
		@Bean
		public UserDetailsService userDetailsService() {
			return new AppUserService();
		}
		// We are going to encrypt using BCrypt method
		@Bean
		public PasswordEncoder encoder() {
			return new BCryptPasswordEncoder();
		}
		//for db- definition how the authentication must happen
		@Bean
		public AuthenticationProvider authenticationProvider() {
			DaoAuthenticationProvider provider =new DaoAuthenticationProvider();
			
			provider.setPasswordEncoder(encoder());
			provider.setUserDetailsService(userDetailsService());
			
			return provider;
		}
			
		}
