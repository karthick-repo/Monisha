package com.example.ImmunizationService.model;

import javax.persistence.Entity; 
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint; 
  
@Entity 
@Table(name = "patientdb", uniqueConstraints=
@UniqueConstraint(columnNames={"document"}))
public class PatientsDB { 
     
    @Id 
    private int document; 
    private String name; 
    private String birth; 
    private String vaccine; 
     
    public int getDocument() { 
        return document;
    } 
    public void setDocument(int document) { 
        this.document = document; 
    } 
    public String getName() { 
        return name; 
    } 
    public void setName(String name) { 
        this.name = name; 
    } 
    public String getBirth() { 
        return birth; 
    } 
    public void setBirth(String birth) { 
        this.birth = birth; 
    } 
    public String getVaccine() { 
        return vaccine; 
    } 
    public void setVaccine(String vaccine) { 
        this.vaccine = vaccine; 
    } 
    @Override 
    public String toString() { 
        return "Patients [document=" + document + ", name=" + name + ", birth=" + birth + ", vaccine=" + vaccine + "]"; 
    } 
} 
