package com.appointment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appointment.Repo.DocRepo;
import com.appointment.model.DocModel;

@RestController
@RequestMapping("/api/")
public class DocController {

	@Autowired
	private DocRepo docrepo;

	public DocController(DocRepo docrepo) {
		this.docrepo = docrepo;
	}

	@PostMapping("/docregister")
	public ResponseEntity<String> docRegister(@RequestBody DocModel docmodel) {

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
		
	DocModel checkuser=	docrepo.findByEmail(docmodel.getEmail());
	if (checkuser != null) {
		if (checkuser.getPassword().equals(docmodel.getPassword()) ) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body("Login Success");
		}else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Wrong Password");
		}
	} else {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Login Failed");
	}
	}

	

}
