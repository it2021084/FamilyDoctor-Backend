package gr.hua.dit.ds.springbootdemo.repo;



import gr.hua.dit.ds.springbootdemo.entity.CitizenProfile;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Hidden

public interface CitizenProfileRepository extends JpaRepository<CitizenProfile, Integer> {
}
