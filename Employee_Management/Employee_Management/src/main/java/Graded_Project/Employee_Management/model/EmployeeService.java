package Graded_Project.Employee_Management.model;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
	@Autowired
	EmployeeRepository employeeRepository;
	
	public void add(Employee employee) {
		employeeRepository.save(employee);
	}
	
	public void update(Employee employee) {
		employeeRepository.save(employee);
	}
	
	public void delete(Employee employee) {
		employeeRepository.delete(employee);
	}
	
	public List<Employee> getAll() {
		return employeeRepository.findAll();
	}
	
	public void bulkAddEmployee(List<Employee> employee) {
		employeeRepository.saveAll(employee);
	}
	
	public  Employee getById(int id) {
		Optional<Employee> tkOptional= employeeRepository.findById(id);
		Employee temp = null;
		if (tkOptional.get() != null) {
			temp = tkOptional.get();
		}
		return temp;
	}
	
	public Employee findByEmployeeId(int id) {

		if (employeeRepository.findById(id).isEmpty())
		{
			return null;
		} else {
			return employeeRepository.findById(id).get();
		}
	}
	
	//filter using firstname
		public List<Employee> filterByFirstnameEmployee(String columnName, String searchKey) {

			// 1.Dummy
			Employee dummy = new Employee();
			dummy.setFirstname(searchKey);

			// 2. where with ExampleMatcher
			ExampleMatcher em = ExampleMatcher.matching()
					.withMatcher("firstname", ExampleMatcher.GenericPropertyMatchers.contains())
					.withIgnorePaths("id","lastname", "email");

			// 3.Combining Dummy with Where
			Example<Employee> example = Example.of(dummy, em);

			return employeeRepository.findAll(example);
		}
		
		
		// only sorting
		public List<Employee> getBySortOnlyEmployee(String columns, Direction direction) {

			// does not care of ordering or sorting or direction
			return employeeRepository.findAll(Sort.by(direction, columns));

		}
	

}
