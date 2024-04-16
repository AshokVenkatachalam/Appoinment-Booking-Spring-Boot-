package com.appointment.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.appointment.model.AppointmentModel;
import com.appointment.model.DocModel;
import com.appointment.service.DocService;

@RestController
@RequestMapping("/api/")
public class DocController {

	
	@Autowired
	private DocService docservice;

//	public DocController(DocRepo docrepo, AppointmentRepo appointmentrepo) {
//		super();
//		this.docrepo = docrepo;
//		this.appointmentrepo = appointmentrepo;
//	}

	@PostMapping("/docregister")
	public ResponseEntity<String> docRegister(@RequestBody DocModel docmodel) {

		System.out.println("Docregister");
		
	 boolean check=	docservice.docRegister(docmodel);
	 if (check) {
		 
		 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email Exist");
		
	}
		
	 return ResponseEntity.status(HttpStatus.ACCEPTED).body("Registration SUccess");

	}

	@PostMapping("/doclogin")
	public ResponseEntity<String> docLogin(@RequestBody DocModel docmodel,HttpSession session) {
		
		boolean checklogin= docservice.docLogin(docmodel , session);
		
		if (checklogin) {
			
			return ResponseEntity.status(HttpStatus.ACCEPTED).body("Login Success");
			
		}else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Login Failed");
		}

	}

	@GetMapping("/viewappointmentrequet")
	public ResponseEntity<?> viewAppointmentRequest(HttpSession session) {
		
	Object objcheck=docservice.viewAppointmentRequest(session);


		if (objcheck instanceof Boolean && (Boolean) objcheck) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body("No Appointments");
		} else if (objcheck instanceof List<?>) {
			
			  return ResponseEntity.status(HttpStatus.ACCEPTED).body((List<AppointmentModel>) objcheck);
        } else {
			
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected response type");
		}

	}

	@PostMapping("/confirmappointment")
	public ResponseEntity<String> confirmAppointment(@RequestBody AppointmentModel appoinmentmodel,HttpSession session) {


	String confirmcheck=docservice.confirmAppointment(appoinmentmodel,session);
		if (confirmcheck.equals("Flag Updated")) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body("Flag Updated");
		} else {
			if (confirmcheck.equals("ALready Updated in this date or time")) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ALready Updated in this date or time");
			}else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ALready updated or DB ISSUE");
			}
		}
		
	
	}

}
