package Graded_Project.Employee_Management.pojo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;



public interface AppUserRepo extends JpaRepository<AppUser,Integer>{
	//creating custom methods using JPA
	Optional<AppUser> findByName(String name);
	

}