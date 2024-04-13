package com.appointment.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.appointment.model.AppointmentModel;

public interface AppointmentRepo extends JpaRepository<AppointmentModel, Long>{

	AppointmentModel findByPatemailAndDocemail(String sessionemail, String docemail);

	List<AppointmentModel> findByPatemail(String sessionemail);

	List<AppointmentModel> findByDocemail(String sessionemail);

	AppointmentModel findByDocemailAndPatemail(String sessionemail, String string);

}
