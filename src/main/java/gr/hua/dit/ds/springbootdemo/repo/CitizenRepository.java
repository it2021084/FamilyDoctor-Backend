package gr.hua.dit.ds.springbootdemo.repo;


import gr.hua.dit.ds.springbootdemo.entity.Citizen;
import gr.hua.dit.ds.springbootdemo.entity.Doctor;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(path= "citizen")
public interface CitizenRepository extends JpaRepository<Citizen, Long> {
    Optional<Citizen> findByEmail(String email);
    List<Doctor> findDoctorsByHomeLocation(String homeLocation);
}


