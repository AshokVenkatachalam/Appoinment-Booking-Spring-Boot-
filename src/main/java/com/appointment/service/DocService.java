package com.appointment.service;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.appointment.Repo.AppointmentRepo;
import com.appointment.Repo.DocRepo;
import com.appointment.model.AppointmentModel;
import com.appointment.model.DocModel;

@Service
public class DocService {

	@Autowired
	private DocRepo docrepo;

	@Autowired
	private AppointmentRepo appointmentrepo;

//	public DocController(DocRepo docrepo, AppointmentRepo appointmentrepo) {
//		super();
//		this.docrepo = docrepo;
//		this.appointmentrepo = appointmentrepo;
//	}

	public boolean docRegister(DocModel docmodel) {

		System.out.println("Docregister");

		boolean chaeckdoc = docrepo.existsByEmail(docmodel.getEmail());
		if (chaeckdoc) {
			return true;
		} else {
			DocModel save = new DocModel();
			save.setEmail(docmodel.getEmail());
			save.setName(docmodel.getName());
			save.setPassword(docmodel.getPassword());
			save.setSpecialist(docmodel.getSpecialist());
			docrepo.save(save);
			return false;
		}
	}

	public boolean docLogin(DocModel docmodel, HttpSession session) {

		DocModel checkuser = docrepo.findByEmail(docmodel.getEmail());
		if (checkuser != null) {

			if (checkuser.getPassword().equals(docmodel.getPassword())) {

				session.setAttribute("email", checkuser.getEmail());
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public Object viewAppointmentRequest(HttpSession session) {

		String sessionemail = (String) session.getAttribute("email");

		List<AppointmentModel> checkdocemail = appointmentrepo.findByDocemail(sessionemail);

		if (checkdocemail.isEmpty()) {
			return false;
		} else {
			return checkdocemail;
		}

	}

	public String confirmAppointment(AppointmentModel appoinmentmodel, HttpSession session) {

		String sessionemail = (String) session.getAttribute("email");
		AppointmentModel appointmentfind = appointmentrepo.findByDocemailAndPatemail(sessionemail,
				appoinmentmodel.getPatemail());
		if (appointmentfind != null && appointmentfind.getFlag() == 1) {

			if (appointmentfind.getDate().equals(appoinmentmodel.getDate())
					&& appointmentfind.getTime().equals(appoinmentmodel.getTime())) {
				appointmentfind.setFlag(2);
				appointmentrepo.save(appointmentfind);
				return "Flag Updated";
			} else {
				return "ALready Updated in this date or time";
			}
		} else {

			return "ALready updated or DB ISSUE";
		}

	}

}
