package Graded_Project.Employee_Management.pojo;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;


@Service

public class AppUserService implements UserDetailsService {
	@Autowired
	AppUserRepo repo;
	
	public void add(AppUser user) {
		repo.save(user);
	}
	
	public List<AppUser> getAll(){
		return repo.findAll();
	}
	
	
	//to authenticate the user
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
		
		Optional<AppUser> appUser = repo.findByName(username);
		
		//we are converting the roles form db to grantedAuth of userDetailsService
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		
		for(String temp: appUser.get().getAuthorities()) {
			GrantedAuthority ga = new SimpleGrantedAuthority(temp);
			grantedAuthorities.add(ga);
		}
		
		//converting appuser to user from user security
		User user = new User(username,appUser.get().getPassword(),grantedAuthorities);
		return user;
	}
}
