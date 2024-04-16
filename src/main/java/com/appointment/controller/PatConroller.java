package com.appointment.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.appointment.Repo.AppointmentRepo;
import com.appointment.Repo.DocRepo;
import com.appointment.Repo.PatRepo;
import com.appointment.model.AppointmentModel;
import com.appointment.model.DocModel;
import com.appointment.model.PatModel;
import com.appointment.service.AppointmentService;
import com.appointment.service.PatService;

@RestController
@RequestMapping("/api/")
public class PatConroller {
	
	@Autowired
	private PatService patservice;

	@Autowired
	private PatRepo patrepo;

	@Autowired
	private AppointmentService appointservice;

	@Autowired
	private AppointmentRepo appointrepo;



	@PostMapping("/patregister")
	public ResponseEntity<String> patRegister(@RequestBody PatModel patmodel) {
		
	boolean registercheck=	patservice.patRegister(patmodel);
		if (registercheck) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body("Registration Success");
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email Exist");	
		}
	}

	@PostMapping("/patlogin")
	public ResponseEntity<String> patLogin(@RequestBody PatModel patmodel,HttpSession session) {

	boolean checklogin=	patservice.patLogin(patmodel,session);
	
	if (checklogin) {
		
		return ResponseEntity.status(HttpStatus.ACCEPTED).body("Login Success");
		
	} else {
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Login Failed");
	}
	}

	@GetMapping("/viewdoctors")
	public ResponseEntity<List<DocModel>> viewDoctors() {
		
		List<DocModel> listdoc= patservice.viewDoctors();

//		List<DocModel> listpat= patservice.viewDoctors();
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(listdoc);
	}

	@PostMapping("/bookappoint")
	public ResponseEntity<String> bookDoctor(@RequestBody AppointmentModel appointmodel,HttpSession session) {
		
	boolean bookdoc=	patservice.bookDoctor(appointmodel,session);
	if (bookdoc) {
		return ResponseEntity.status(HttpStatus.ACCEPTED).body("Appointment Booked");
	} else{
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Appointment Already Booked in this date");
	}
	}
	
	@GetMapping("/viewbookedapoointment")
	public ResponseEntity<?> viewBookedAppointmetns(HttpSession session){
		
		Object checkappointment= appointservice.viewBookedAppointmetns(session);
		
		
		if (checkappointment instanceof Boolean && (boolean) checkappointment) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body("No Appointments");
			
		} else if(checkappointment instanceof List<?>) {
			
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(checkappointment);

		}else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected response type");

		}

	}
	
	
	@GetMapping("/deleteappointment/{id}")
	public ResponseEntity<String> deleteAppointment(@PathVariable Long id){
		
		boolean deletecheck= appointservice.deleteAppointment(id);
		
//		boolean checkid= appointrepo.existsById(id);
		if (deletecheck) {
			
			return	ResponseEntity.status(HttpStatus.ACCEPTED).body("Appointment Deleted");
		} else {
			return	ResponseEntity.status(HttpStatus.ACCEPTED).body("Id not exist");
		}	 
	}

}
