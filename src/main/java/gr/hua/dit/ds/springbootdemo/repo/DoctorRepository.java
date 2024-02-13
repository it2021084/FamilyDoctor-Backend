package gr.hua.dit.ds.springbootdemo.repo;

import gr.hua.dit.ds.springbootdemo.entity.Doctor;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
@Hidden
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Optional<Doctor> findByEmail(String email);
    @Query("SELECT d FROM Doctor d WHERE size(d.citizens) < d.maxPatients")
    List<Doctor> findDoctorsWithAvailableSlots();



}