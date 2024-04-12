package com.appointment.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.appointment.model.DocModel;

public interface DocRepo extends JpaRepository<DocModel, Long>{

	DocModel findByEmail(String email);

	boolean existsByEmail(String email);

}
