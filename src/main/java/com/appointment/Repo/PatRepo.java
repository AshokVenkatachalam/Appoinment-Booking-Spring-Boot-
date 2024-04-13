package com.appointment.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.appointment.model.AppointmentModel;
import com.appointment.model.PatModel;

public interface PatRepo extends JpaRepository<PatModel, Long>{

	boolean existsByEmail(String email);

	PatModel findByEmail(String email);

	
}
