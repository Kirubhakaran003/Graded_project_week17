package Graded_Project.Employee_Management.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data


public class Employee {
	@Id
	private int id;
	private String firstname;
	private String lastname;
	private String email;
}
