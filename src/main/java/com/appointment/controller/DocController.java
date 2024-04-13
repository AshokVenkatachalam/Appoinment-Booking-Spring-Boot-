package com.appointment.controller;

import java.util.List;

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
import com.appointment.model.AppointmentModel;
import com.appointment.model.DocModel;

@RestController
@RequestMapping("/api/")
public class DocController {

	@Autowired
	private DocRepo docrepo;

	@Autowired
	private AppointmentRepo appointmentrepo;

	public DocController(DocRepo docrepo, AppointmentRepo appointmentrepo) {
		super();
		this.docrepo = docrepo;
		this.appointmentrepo = appointmentrepo;
	}

	@PostMapping("/docregister")
	public ResponseEntity<String> docRegister(@RequestBody DocModel docmodel) {

		System.out.println("Docregister");

		boolean chaeckdoc = docrepo.existsByEmail(docmodel.getEmail());
		if (chaeckdoc) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email Exist");
		} else {
			DocModel save = new DocModel();
			save.setEmail(docmodel.getEmail());
			save.setName(docmodel.getName());
			save.setPassword(docmodel.getPassword());
			save.setSpecialist(docmodel.getSpecialist());
			docrepo.save(save);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body("Registration Success");
		}
	}

	@PostMapping("/doclogin")
	public ResponseEntity<String> docLogin(@RequestBody DocModel docmodel) {

		DocModel checkuser = docrepo.findByEmail(docmodel.getEmail());
		if (checkuser != null) {
			if (checkuser.getPassword().equals(docmodel.getPassword())) {
				return ResponseEntity.status(HttpStatus.ACCEPTED).body("Login Success");
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Wrong Password");
			}
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Login Failed");
		}
	}

	@GetMapping("/viewappointmentrequet")
	public ResponseEntity<?> viewAppointmentRequest() {

		String sessionemail = "ashokvenkat2001@gmail.com";

		List<AppointmentModel> checkdocemail = appointmentrepo.findByDocemail(sessionemail);

		if (checkdocemail.isEmpty()) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body("No Appointments");
		} else {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(checkdocemail);
		}

	}

	@PostMapping("/confirmappointment")
	public ResponseEntity<String> confirmAppointment(@RequestBody AppointmentModel appoinmentmodel) {

		String sessionemail = "ashokvenkat2001@gmail.com";

		AppointmentModel appointmentfind = appointmentrepo.findByDocemailAndPatemail(sessionemail,
				appoinmentmodel.getPatemail());

		if (appointmentfind != null && appointmentfind.getFlag() == 1) {

			if (appointmentfind.getDate().equals(appoinmentmodel.getDate())
					&& appointmentfind.getTime().equals(appoinmentmodel.getTime())) {
				
				appointmentfind.setFlag(2);
				
				appointmentrepo.save(appointmentfind);
				
				return ResponseEntity.status(HttpStatus.ACCEPTED).body("Flag Updated");

			} else {

				
				return ResponseEntity.status(HttpStatus.ACCEPTED).body("ALready Updated in this date or time");
				
			}

		}else {
			
			return ResponseEntity.status(HttpStatus.ACCEPTED).body("ALready updated or DB ISSUE");
		}

		
	}

}
