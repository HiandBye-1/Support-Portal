package Jar.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import Jar.entities.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> { 
    
}
