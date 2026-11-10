package Jar.service;
import Jar.entities.Employee;
import Jar.repository.EmployeeRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.*;
import org.springframework.web.server.ResponseStatusException;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService( EmployeeRepository empRepo){
        this.employeeRepository = empRepo;
    }

    public Employee findId(int id){
        return employeeRepository.findById(id).orElseThrow(() -> 
        new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found"));
        
    }

    public Employee save(Employee employee){
        return employeeRepository.save(employee);
    }

    public Employee update(Employee employee, int id){
        Employee existing = findId(id);
        existing.setName(employee.getName());
        return employeeRepository.save(existing);
    }


    public void delete(int id){
        employeeRepository.deleteById(id);
    }
    
}
