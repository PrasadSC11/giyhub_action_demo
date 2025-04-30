package com.hefShine.Starter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

@RestController
//@CrossOrigin("http://localhost:4200")
public class EmployeeController {

    @Autowired
    private EmployeeRepo employeeRepo;

    @PostMapping("/")
    public Employeess addEmployee(@RequestBody Employeess employee) {
        return employeeRepo.save(employee);
    }

    // Get All Employees
    @GetMapping("/get")
    public List<Employeess> findAll() {
        return employeeRepo.findAll();
    }

    // Find Employee by ID (using parameterized query to avoid SQL injection)
    @GetMapping("Employeess/{id}")
    public Employeess findById(@PathVariable("id") Long id) {
        return employeeRepo.findById(id).orElse(null);
    }

    // Delete Employee by ID (using repository method, safe from SQL injection)
    @DeleteMapping("Employeess/{id}")
    public boolean deleteById(@PathVariable("id") Long id) {
        if (employeeRepo.existsById(id)) {
            employeeRepo.deleteById(id);
            return true;
        }
        return false;
    }

    @GetMapping("login")
    public String login(@RequestParam String username, @RequestParam String password) {
        String query = "select * from employeess where username='" + username + "' and password = '" + password + "'";
        Connection conn = null;
        Statement stmt = null;
        String result = "";
        System.out.println(query);
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/SpringPract", "root", "root");
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            // Only call rs.next() once to move to the first row
            if (rs.next()) { // This checks if there is a matching row
                System.out.println("login");
                result = "login"; // Username and password are correct
            } else {
                System.out.println("failed");
                result = "failed"; // No matching row, so login failed
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error");
            result = "error"; // An error occurred while executing the query
        } finally {
            // Ensure resources are closed
            try {
                if (stmt != null)
                    stmt.close();
                if (conn != null)
                    conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

}
