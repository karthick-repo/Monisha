package com.example.ImmunizationService.dao;


import java.sql.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.ImmunizationService.model.PatientsDB; 

public interface PatientRepo extends JpaRepository<PatientsDB, Integer> { 
	
	@Query(value = "SELECT BIRTH FROM patientDB u WHERE u.document= ?#{[0]}", nativeQuery = true)
	Date datefindDateForUser(int document);
	
}



