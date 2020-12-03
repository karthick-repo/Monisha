package com.example.ImmunizationService.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.ImmunizationService.dao.PatientRepo;
import com.example.ImmunizationService.dao.VaccineRepo;
import com.example.ImmunizationService.exception.DuplicateException;
import com.example.ImmunizationService.model.PatientsDB;
import com.example.ImmunizationService.model.VaccineDB;
import com.sun.el.parser.ParseException;

@Controller
public class ImmunizationServiceController {
	@Autowired
	PatientRepo patientRepo;

	@Autowired
	VaccineRepo vaccineRepo;

	public int searchId;
	private static final String TITLE = "Immunization Service";
	private static final String DASHBOARD = "Dashboard";
	private static final String PATIENTS = "Patients";

	@RequestMapping("/home")
	public ModelAndView dashboard() {
		ModelAndView mv = new ModelAndView();
		List<PatientsDB> patientlist = patientRepo.findAll();
		mv.addObject("title", TITLE);
		mv.addObject("dashboard", DASHBOARD);
		mv.addObject("patients", PATIENTS);
		mv.addObject("data", patientlist);
		mv.addObject("k", 3);

		mv.setViewName("home");
		return mv;
	}

	@RequestMapping("/patients")
	public ModelAndView showPatients() {
		ModelAndView mv = new ModelAndView();
		List<PatientsDB> list = patientRepo.findAll();
		List<VaccineDB> vaccine = vaccineRepo.findAll();
		mv.addObject("title", TITLE);
		mv.addObject("dashboard", DASHBOARD);
		mv.addObject("patients", PATIENTS);
		mv.addObject("details", list);
		mv.addObject("toppatients", patientRepo.findAll());
		mv.addObject("topvaccines", vaccineRepo.findAll());
		mv.setViewName("patients");
		return mv;
	}

	@RequestMapping("/getpatient")
	public ModelAndView patientPage(@RequestParam("documentSearch") int document) {
		ModelAndView mv = new ModelAndView();
		searchId = document;
		mv.addObject("title", TITLE);
		mv.addObject("dashboard", DASHBOARD);
		mv.addObject("patients", PATIENTS);
		PatientsDB patient = patientRepo.findById(document).orElse(new PatientsDB());
		mv.addObject("document", patient.getDocument());
		mv.addObject("patient", patient);
		mv.addObject("topvaccines", vaccineRepo.findAllUsersbyid(document));
		mv.setViewName("getpatient");

		// DISPLAY AGE FROM DATE

		Date d = patientRepo.datefindDateForUser(document);
		LocalDate now = LocalDate.now();
		int year = d.getYear();
		int month = d.getMonth();
		int day = d.getDay();

		LocalDate oldDate = LocalDate.of(year + 1900, month, day);
		Period diff = Period.between(oldDate, now);
		mv.addObject("patientAge", diff.getYears());

		return mv;

	}

	@RequestMapping("/addpatient")
	public ModelAndView addPatient() {

		ModelAndView mv2 = new ModelAndView();
		mv2.addObject("title", TITLE);
		mv2.addObject("dashboard", DASHBOARD);
		mv2.addObject("patients", PATIENTS);
		mv2.setViewName("addpatient");

		return mv2;
	}

	@RequestMapping("/addpatients")
	public ModelAndView savePatient(PatientsDB patient) {
		ModelAndView mv1 = new ModelAndView("patients");
		List<PatientsDB> patientList = patientRepo.findAll();
		for (PatientsDB p : patientList) {
			if (p.getDocument() == patient.getDocument()) {
				throw new DuplicateException("Patient", "document", patient.getDocument());
			}
		}
		patientRepo.save(patient);
		mv1.setViewName("addpatient");
		mv1.addObject("dashboard", DASHBOARD);
		mv1.addObject("patients", PATIENTS);
  
		return mv1;
	}

	@RequestMapping("/addvaccine")
	public ModelAndView vaccine(VaccineDB vaccineDB) {
		ModelAndView mv = new ModelAndView();

		vaccineRepo.save(vaccineDB);
		System.out.println("check : " + vaccineDB.getVaccine() + vaccineDB.getDocument());
		updatePatient(vaccineDB.getVaccine(), vaccineDB.getDocument());
		mv.setViewName("vaccine");
		return mv;
	}

	@RequestMapping("/vaccine")
	public ModelAndView updateVaccine() throws ParseException {
		ModelAndView mv = new ModelAndView();

		mv.addObject("title", TITLE);
		mv.addObject("dashboard", DASHBOARD);
		mv.addObject("patients", PATIENTS);
		mv.addObject("searchId", searchId);
		mv.setViewName("vaccine");

		return mv;
	}

	public void updatePatient(String vaccine, int doc) {
		PatientsDB patient = patientRepo.findById(doc).orElse(new PatientsDB());
		patient.setVaccine(vaccine);
		patientRepo.save(patient);
	}

}
