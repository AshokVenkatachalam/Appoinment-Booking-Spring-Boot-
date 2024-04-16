package com.appointment.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.appointment.Repo.AppointmentRepo;
import com.appointment.Repo.DocRepo;
import com.appointment.Repo.PatRepo;
import com.appointment.model.AppointmentModel;
import com.appointment.model.DocModel;
import com.appointment.model.PatModel;

@Service
public class PatService {
	


	@Autowired
	private PatRepo patrepo;

	@Autowired
	private DocRepo docrepo;

	@Autowired
	private AppointmentRepo appointrepo;


	public boolean patRegister(PatModel patmodel) {

		boolean chaeckdoc = patrepo.existsByEmail(patmodel.getEmail());
		if (chaeckdoc) {
			return false;
		} else {
			PatModel save = new PatModel();
			save.setEmail(patmodel.getEmail());
			save.setName(patmodel.getName());
			save.setPassword(patmodel.getPassword());
			save.setDob(patmodel.getDob());
			patrepo.save(save);
			return true;
		}
	}


	public boolean patLogin( PatModel patmodel,HttpSession session) {

		PatModel checkuser = patrepo.findByEmail(patmodel.getEmail());
		if (checkuser != null) {
			if (checkuser.getPassword().equals(patmodel.getPassword())) {
				session.setAttribute("email", checkuser.getEmail());
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public List<DocModel> viewDoctors() {
		List<DocModel> listdoc = docrepo.findAll();
		return listdoc;
	}


	public boolean bookDoctor( AppointmentModel appointmodel,HttpSession session) {
		String sessionemail = (String) session.getAttribute("email");
		
		System.out.println(sessionemail);
		
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
			return true;
		} else {
			if (appointmentcheck.getDate().equals(appointmodel.getDate())) {
				return false;
			} else {
				AppointmentModel bookappointment = new AppointmentModel();
				bookappointment.setDate(appointmodel.getDate());
				bookappointment.setDocemail(appointmodel.getDocemail());
				bookappointment.setPatemail(sessionemail);
				bookappointment.setTime(appointmodel.getTime());
				bookappointment.setFlag(1);
				appointrepo.save(bookappointment);
				return true;
			}
		}
	}
		 
	
}
