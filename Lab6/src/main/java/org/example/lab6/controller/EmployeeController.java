package org.example.lab6.controller;

import org.example.lab6.api.ApiResponse;
import org.example.lab6.model.Employee;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {

    ArrayList<Employee> employees = new ArrayList<>();

    @GetMapping("/get")
    public ArrayList<Employee> getEmployees() {
        return employees;
    }
    @PostMapping("/add")
    public ResponseEntity addEmployee(@RequestBody Employee employee, Errors errors) {
        if(errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        employees.add(employee);
        return ResponseEntity.status(200).body("Employee added successfully");
    }

    @PutMapping("/update/{index}")
    public ResponseEntity updateEmployee(@PathVariable int index, Employee employee, Errors errors) {
        if(errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        employees.set(index, employee);
        return ResponseEntity.status(200).body("Employee updated successfully");
    }

    @DeleteMapping("/delete/{index}")
    public ResponseEntity deleteEmployee(@PathVariable int index){
        if(index > employees.size() - 1) {
            return ResponseEntity.status(400).body(new ApiResponse("Index out of bound"));
        }
        employees.remove(index);
        return ResponseEntity.status(200).body(new ApiResponse("Employee deleted successfully"));
    }
   @GetMapping("/search/{position}")
    public ArrayList<Employee> searchEmployees(@PathVariable String position) {
        ArrayList<Employee> result = new ArrayList<>();
        for (Employee e : employees) {
            if (e.getPosition().equalsIgnoreCase(position)) {
                result.add(e);
            }
        }
        return result;
    }
    @GetMapping("/range/{minAge}/{maxAge}")
    public ArrayList<Employee> getEmployeeAgeRange(@PathVariable int minAge,@PathVariable int maxAge) {
        ArrayList<Employee> ageRange = new ArrayList<>();
        for(Employee e : employees) {
            if(e.getAge() >= minAge && e.getAge() <= maxAge){
                ageRange.add(e);
            }
        }
        return ageRange;
    }

    @GetMapping("/check/{id}")
     public ResponseEntity checkAnnualLeave(@PathVariable String id) {
        for(Employee e : employees) {
            if(e.getId().equals(id) && e.isOnLeave() == false && e.getAnnualLeave() >= 1){
                e.setOnLeave(true);
                e.setAnnualLeave(e.getAnnualLeave() - 1);
                return ResponseEntity.status(200).body(new ApiResponse("Successful annual leave apply"));
            }
        }
        return ResponseEntity.status(400).body("Id not found");
     }

     @GetMapping("/leave")
     public ArrayList<Employee> getNoLeaveEmployee() {
        ArrayList<Employee> noLeaveEmployees = new ArrayList<>();
        for(Employee e : employees) {
            if(e.getAnnualLeave() == 0 ) {
                noLeaveEmployees.add(e);
            }
        }
        return noLeaveEmployees;
     }


    @PutMapping("/promote/{id1}/{id2}")
    public ResponseEntity promote(@PathVariable String id1 , @PathVariable String id2){
        for (Employee e : employees){
            if (e.getId().equals(id1) && e.getPosition().equals("supervisor")){
                for (Employee employee1 : employees){
                    if (employee1.getId().equals(id2) && employee1.getAge() >=30 && ! employee1.isOnLeave()){
                        employee1.setPosition("supervisor");
                        return ResponseEntity.status(200).body(new ApiResponse("Coordinator employee promoted to supervisor"));

                    }
                }
            }
        }
        return ResponseEntity.status(400).body(new ApiResponse("Employee can not be promoted"));
    }

}
