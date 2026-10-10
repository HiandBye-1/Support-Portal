package Jar;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import Jar.service.EmployeeService;

@RestController
public class EmployeeController {
  private EmployeeService employeeService;

  public EmployeeController(EmployeeService employeeService) {
    this.employeeService = employeeService;
  }


  @GetMapping("/employee")//get employee
  public String print(){
    return "hello";
  }


  @GetMapping("/employee/{id}")//get employee/id
  public Employee findId(@PathVariable int id){
    return employeeService.findId(id);
  }

  @PostMapping("/employee")//post method
  @ResponseStatus(HttpStatus.CREATED)
  public Employee saveEmployee(@RequestBody Employee employee){
    return employeeService.save(employee);
  }

  @PutMapping("/employee/{id}")//put method
  public Employee updateEmployee(@RequestBody Employee employee, @PathVariable int id){
    return employeeService.update(employee, id);
  }

  @DeleteMapping("/employee/{id}")//delete method
  public void deleteEmployee(@PathVariable int id){
    employeeService.delete(id);
  }


}
