package Graded_Project.Employee_Management;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Graded_Project.Employee_Management.model.Employee;
import Graded_Project.Employee_Management.model.EmployeeService;
import Graded_Project.Employee_Management.pojo.AppUser;
import Graded_Project.Employee_Management.pojo.AppUserService;
import io.swagger.v3.oas.annotations.Operation;

@RestController
public class EmployeeController {

	@Autowired
	EmployeeService employeeService;
	@Autowired
	AppUserService appUserService;

	@GetMapping("/getAllEmployee")
	public List<Employee> getAllEmployee() {
		return employeeService.getAll();
	}

	@Operation(summary = "Add user /* Only admin can access */")
	@PostMapping("/api/addUser")
	public String addUser(@RequestParam int id, @RequestParam String username, @RequestParam String password,
			Authentication authentication, SecurityContextHolder auth) {
		String acceptedRole = "admin"; // for db
		boolean roleFound = false;

		// who is currently logging in
		System.out.println("Current login by: " + authentication.getName());

		// find the role of the person who have logged
		@SuppressWarnings("static-access")
		Collection<? extends GrantedAuthority> grantedRoles = auth.getContext().getAuthentication().getAuthorities();
		// above - getting the role(s) mapped with the user

		for (int i = 0; i < grantedRoles.size(); i++) {
			String role = grantedRoles.toArray()[i].toString();
			System.out.println("My Role: " + role);

			if (role.equalsIgnoreCase(acceptedRole)) {
				roleFound = true;
			}
		}
		if (roleFound) {
			Set<String> authUser = new HashSet<>();
			authUser.add("User");

			// create encode object
			PasswordEncoder en = new BCryptPasswordEncoder();

			AppUser appUser = new AppUser(id, username, password, en.encode(password), authUser);
			appUserService.add(appUser);
			return "User added";
		}
		return "Only admin can access";
	}

	@Operation(summary = "Add Employee /* Only admin can access */ ")
	@PostMapping("/api/AddEmployee")
	public String addEmployee(@RequestParam int id, @RequestParam String firstname, @RequestParam String lastname,
			@RequestParam String email, Authentication authentication, SecurityContextHolder auth) {
		String acceptedRole = "admin";

		boolean roleFound = false;
		System.out.println("Curent login by:" + authentication.getName());

		Collection<? extends GrantedAuthority> grantedRoles = auth.getContext().getAuthentication().getAuthorities();
		for (int i = 0; i < grantedRoles.size(); i++) {

			String role1 = grantedRoles.toArray()[i].toString();
			System.out.println("my role :" + role1);

			if (role1.equalsIgnoreCase(acceptedRole)) {
				roleFound = true;
			}
		}
		if (roleFound == true) {
			Employee emp1 = new Employee(id, firstname, lastname, email);
			employeeService.add(emp1);
			return "Employee added";

		} else {
			return "Only admin can access";
		}
	}
	@Operation(summary="Update Employee /* Only admin can access */ ")
	@PutMapping("/api/UpdateEmployee")
	public String updateEmployee(@RequestParam int id, @RequestParam String firstname, @RequestParam String lastname,
			@RequestParam String email, Authentication authentication, SecurityContextHolder auth) {
		String acceptedRole = "admin";

		boolean roleFound = false;
		System.out.println("Curent login by:" + authentication.getName());

		Collection<? extends GrantedAuthority> grantedRoles = auth.getContext().getAuthentication().getAuthorities();
		for (int i = 0; i < grantedRoles.size(); i++) {

			String role1 = grantedRoles.toArray()[i].toString();
			System.out.println("my role :" + role1);

			if (role1.equalsIgnoreCase(acceptedRole)) {
				roleFound = true;
			}
		}
		if (roleFound == true) {
			Employee emp1 = new Employee(id, firstname, lastname, email);
			employeeService.update(emp1);
			return "Employee updated";
		} else {
			return "Only admin can access";
		}
	}
	
	@Operation(summary = "Delete Employee /* Only admin can access */ ")
	@DeleteMapping("/api/DeleteEmployee")
	public String deleteEmployee(@RequestParam int id, Authentication authentication, SecurityContextHolder auth) {
		String acceptedRole = "admin";

		boolean roleFound = false;
		System.out.println("Curent login by:" + authentication.getName());

		Collection<? extends GrantedAuthority> grantedRoles = auth.getContext().getAuthentication().getAuthorities();
		for (int i = 0; i < grantedRoles.size(); i++) {

			String role1 = grantedRoles.toArray()[i].toString();
			System.out.println("my role :" + role1);

			if (role1.equalsIgnoreCase(acceptedRole)) {
				roleFound = true;
			}
		}
		if (roleFound == true) {
			Employee emp1 = new Employee(id, null, null, null);
			employeeService.update(emp1);
			return "Employee Deleted";
		} else {
			return " Only admin can access";
		}

	}

	@GetMapping("/api/getByEmployeeId")
	public Employee getById(@RequestParam int id) {
		return employeeService.findByEmployeeId(id);
	}

	@Operation(summary = "search for firstname")
	@GetMapping("/searchByFirstname")
	public List<Employee> searchByDepartmentV2(@RequestParam String firstname) {
		return employeeService.filterByFirstnameEmployee(firstname, firstname);
	}

	@Operation(summary = "For paging purposes. Send direction 'asc' for asc and 'desc' for desc.")
	@GetMapping("/api/sorting")
	public List<Employee> getBySortOnlyEmployee(@RequestParam String order) {

		// asc
		if ("asc".equals(order.toString())) {
			List<Employee> employee = employeeService.getBySortOnlyEmployee("firstname", Direction.ASC);
			return employee;
		}
		// desc
		else if ("desc".equals(order.toString())) {
			List<Employee> employee = employeeService.getBySortOnlyEmployee("firstname", Direction.DESC);
			return employee;
		}
		return null;

	}

}