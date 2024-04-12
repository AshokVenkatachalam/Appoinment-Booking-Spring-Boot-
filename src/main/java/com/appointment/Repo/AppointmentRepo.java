package com.appointment.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.appointment.model.AppointmentModel;

public interface AppointmentRepo extends JpaRepository<AppointmentModel, Long>{

}
