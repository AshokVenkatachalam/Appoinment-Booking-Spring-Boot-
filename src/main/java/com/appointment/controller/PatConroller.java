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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.appointment.Repo.AppointmentRepo;
import com.appointment.Repo.DocRepo;
import com.appointment.Repo.PatRepo;
import com.appointment.model.AppointmentModel;
import com.appointment.model.DocModel;
import com.appointment.model.PatModel;

@RestController
@RequestMapping("/api/")
public class PatConroller {

	@Autowired
	private PatRepo patrepo;

	@Autowired
	private DocRepo docrepo;

	@Autowired
	private AppointmentRepo appointrepo;

	public PatConroller(PatRepo patrepo, DocRepo docrepo, AppointmentRepo appointrepo) {
		super();
		this.patrepo = patrepo;
		this.docrepo = docrepo;
		this.appointrepo = appointrepo;
	}

	@PostMapping("/patregister")
	public ResponseEntity<String> docRegister(@RequestBody PatModel patmodel) {

		boolean chaeckdoc = patrepo.existsByEmail(patmodel.getEmail());
		if (chaeckdoc) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email Exist");
		} else {
			PatModel save = new PatModel();
			save.setEmail(patmodel.getEmail());
			save.setName(patmodel.getName());
			save.setPassword(patmodel.getPassword());
			save.setDob(patmodel.getDob());
//			save.setSpecialist(patmodel.getSpecialist());
			patrepo.save(save);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body("Registration Success");
		}
	}

	@PostMapping("/patlogin")
	public ResponseEntity<String> docLogin(@RequestBody PatModel patmodel) {

		PatModel checkuser = patrepo.findByEmail(patmodel.getEmail());
		if (checkuser != null) {
			if (checkuser.getPassword().equals(patmodel.getPassword())) {
				return ResponseEntity.status(HttpStatus.ACCEPTED).body("Login Success");
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Wrong Password");
			}
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Login Failed");
		}
	}

	@GetMapping("/viewdoctors")
	public ResponseEntity<List<DocModel>> viewDoctors() {

		List<DocModel> listdoc = docrepo.findAll();

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(listdoc);
	}

	@PostMapping("/bookappoint")
	public ResponseEntity<String> bookDoctor(@RequestBody AppointmentModel appointmodel) {
		String sessionemail = "ashokpat@gmail.com";
		AppointmentModel appointmentcheck = appointrepo.findByPatemailAndDocemail(sessionemail,
				appointmodel.getDocemail());
		if (appointmentcheck == null) {
			AppointmentModel bookappointment = new AppointmentModel();
			bookappointment.setDate(appointmodel.getDate());
			bookappointment.setDocemail(appointmodel.getDocemail());
			bookappointment.setPatemail(sessionemail);
			bookappointment.setTime(appointmodel.getTime());
			bookappointment.setFlag(1);
			appointrepo.save(bookappointment);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body("Appointment Booked");
		} else {
			if (appointmentcheck.getDate().equals(appointmodel.getDate())) {
				return ResponseEntity.status(HttpStatus.ACCEPTED).body("Appointment Already Booked in this date");
			} else {
				AppointmentModel bookappointment = new AppointmentModel();
				bookappointment.setDate(appointmodel.getDate());
				bookappointment.setDocemail(appointmodel.getDocemail());
				bookappointment.setPatemail(sessionemail);
				bookappointment.setTime(appointmodel.getTime());
				bookappointment.setFlag(1);
				appointrepo.save(bookappointment);
				return ResponseEntity.status(HttpStatus.ACCEPTED).body("Appointment Booked");
			}
		}
	}
	
	@GetMapping("/viewbookedapoointment")
	public ResponseEntity<?> viewBookedAppointmetns(){
		
		String sessionemail = "ashokpat@gmail.com";
		
		List<AppointmentModel> getpateemail= appointrepo.findByPatemail(sessionemail);
		
		if (getpateemail.isEmpty()) {
			
			return ResponseEntity.status(HttpStatus.ACCEPTED).body("No Appointments");
		} else {
			
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(getpateemail);
		}
	}
	
	
	@GetMapping("/deleteappointment/{id}")
	public ResponseEntity<String> deleteAppointment(@PathVariable Long id){
		
		boolean checkid= appointrepo.existsById(id);
		
		if (checkid) {
			
			appointrepo.deleteById(id);
			
			return	ResponseEntity.status(HttpStatus.ACCEPTED).body("Appointment Deleted");
			
		} else {
			
			return	ResponseEntity.status(HttpStatus.ACCEPTED).body("Id not exist");

		}
		

		
		 
	}

}
