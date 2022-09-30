package com.examples.empapp.service;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.examples.empapp.exception.EmployeeException;
import com.examples.empapp.model.Employee;

public class EmployeeServiceColImpl implements EmployeeService {

	Map<Integer, Employee> employees = new HashMap<>();

	Comparator<Employee> EMPLOYEE_NAME_ASC_SORT = new Comparator<Employee>() {
		@Override
		public int compare(Employee o1, Employee o2) {
			return o1.getName().compareTo(o2.getName());

		}
	};

	public EmployeeServiceColImpl() {
		// Initializing with employee data
		employees.put(1, new Employee(1, "Anil", 45, "Delivery Manager", "Operations", "India"));
		employees.put(2, new Employee(2, "Swapnil", 35, "Quality Analyst", "Quality", "India"));
		employees.put(3, new Employee(3, "Georgil", 42, "Manager", "Operations", "USA"));
		employees.put(4, new Employee(4, "Sunil", 26, "Associate", "Operations", "India"));
		employees.put(5, new Employee(5, "Saril", 30, "Lead Associate", "Operations", "UK"));
		employees.put(6, new Employee(6, "Vinil", 36, "Manager", "Admin", "Australia"));
	}

	public boolean create(Employee employee) {
		employee.setEmpId(employees.size() + 1);
		return employees.put(employee.getEmpId(), employee) != null ? true : false;
	}

	public Employee get(int id) throws EmployeeException {
		Employee emp = employees.get(id);
		
		if(emp == null) {
			throw new EmployeeException("No employee found for given id.");
		}
		return emp;
	}

	public List<Employee> getAll() {
		ArrayList<Employee> empList = new ArrayList<Employee>(employees.values());
//		Collections.sort(empList, EMPLOYEE_NAME_ASC_SORT);
		return empList;

	}

	public boolean update(Employee employee) {
		return employees.put(employee.getEmpId(), employee) != null ? true : false;
	}

	public boolean delete(int id) {
		return employees.remove(id) != null ? true : false;
	}

	public boolean validate(Employee emp, String msg, Predicate<Employee> condition,
			Function<String, Boolean> operation) {
		if (!condition.test(emp)) {
			return operation.apply(msg);
		}
		return true;
	}

	// Get Employee count greater than given age
	public long getEmployeeCountAgeGreaterThan(int age) {
		long count = 0;
		for (Employee emp: employees.values()) {
			if(emp.getAge() > age) {
				count++;
			}
		}
		return count;
	}

	// Get list of Employee IDs whose age is greater than given age
	public List<Integer> getEmployeeIdsAgeGreaterThan(int age) {
		List<Integer> empIds = new ArrayList<>();
		for(Employee emp: employees.values()) {
			if(emp.getAge() > age) {
				empIds.add(emp.getEmpId());
			}
		}
		return empIds;
	}

	// Get Department wise Employee count
	public Map<String, Long> getEmployeeCountByDepartment() {
		Map<String, Long> empCountByDept = new HashMap<>();
		for (Employee emp: employees.values()) {
			if(empCountByDept.containsKey(emp.getDepartment())) {
				long count = empCountByDept.get(emp.getDepartment());
				empCountByDept.put(emp.getDepartment(), ++count);
			} else {
				empCountByDept.put(emp.getDepartment(), 1L);
			}
		}
		return empCountByDept;
	}

	// Get Department wise Employee count ordered by Department name
	public Map<String, Long> getEmployeeCountByDepartmentOrdered() {
		Map<String, Long> empCountByDept = new TreeMap<>();
		for (Employee emp: employees.values()) {
			if(empCountByDept.containsKey(emp.getDepartment())) {
				long count = empCountByDept.get(emp.getDepartment());
				empCountByDept.put(emp.getDepartment(), ++count);
			} else {
				empCountByDept.put(emp.getDepartment(), 1L);
			}
		}
		return empCountByDept;
	}
}