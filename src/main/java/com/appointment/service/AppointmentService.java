package com.appointment.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.appointment.Repo.AppointmentRepo;
import com.appointment.model.AppointmentModel;

@Service
public class AppointmentService {
	
	@Autowired
	private AppointmentRepo appointrepo;

	
	
	public Object viewBookedAppointmetns(HttpSession session){
		
		String sessionemail = (String)session.getAttribute("email");
		
		System.out.println(sessionemail);
		
		List<AppointmentModel> getpateemail= appointrepo.findByPatemail(sessionemail);
		
		if (getpateemail.isEmpty()) {
			
			return false;
		} else {
//			System.out.println(getpateemail.toString());
			
			return getpateemail;
		}
	}
	
	

	public boolean deleteAppointment(@PathVariable Long id){
		
		boolean checkid= appointrepo.existsById(id);
		
		if (checkid) {
			
			appointrepo.deleteById(id);
			
			return	true;
			
		} else {
			
			return	false;

		}
	}

}
