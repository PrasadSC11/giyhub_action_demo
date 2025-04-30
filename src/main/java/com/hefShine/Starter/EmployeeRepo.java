package com.hefShine.Starter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepo extends JpaRepository<Employeess, Long> {

	// Parameterized query to prevent SQL injection
	@Query("SELECT e FROM Employeess e WHERE e.username = :username AND e.password = :password")
	Employeess findByUsernameAndPassword(String username, String password);
}
