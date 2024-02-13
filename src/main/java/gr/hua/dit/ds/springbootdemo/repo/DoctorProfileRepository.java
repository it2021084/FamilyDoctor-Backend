package gr.hua.dit.ds.springbootdemo.repo;


import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import gr.hua.dit.ds.springbootdemo.entity.DoctorProfile;
import org.springframework.stereotype.Repository;

@Repository
@Hidden

public interface DoctorProfileRepository extends JpaRepository<DoctorProfile, Integer>{
}
