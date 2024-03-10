package Graded_Project.Employee_Management;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import Graded_Project.Employee_Management.model.Employee;
import Graded_Project.Employee_Management.model.EmployeeService;
import Graded_Project.Employee_Management.pojo.AppUser;
import Graded_Project.Employee_Management.pojo.AppUserService;

@SpringBootApplication
public class EmployeeManagementApplication implements CommandLineRunner{
	
	@Autowired
	EmployeeService employeeService;
	@Autowired
	AppUserService appUserService;
	
	public static void main(String[] args) {
		SpringApplication.run(EmployeeManagementApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		
		Set<String> authAdmin= new HashSet<>();
		authAdmin.add("Admin");


		//create encode object
		PasswordEncoder en= new BCryptPasswordEncoder();
		AppUser appAdmin= new AppUser(1, "admin", "admin", en.encode("adminPassword"), authAdmin);
		appUserService.add(appAdmin);
		
		
		/*//inserting Employee in bulk
				Employee emp1= new Employee(1,"kirubhakaran","rajendran","kirubhakaran@gmail.com");
				Employee emp2= new Employee(2,"akilan","mahesh","akilan@gmail.com");
				Employee emp3= new Employee(3,"sanjay","kumar","sanjay@gmail.com");
				List<Employee> employee1=new ArrayList<>();
				employee1.add(emp1);
				employee1.add(emp2);
				employee1.add(emp3);
				employeeService.bulkAddEmployee(employee1);*/
	}

}
