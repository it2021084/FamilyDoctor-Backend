package gr.hua.dit.ds.springbootdemo.repo;

import gr.hua.dit.ds.springbootdemo.entity.FamilyDetails;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@RepositoryRestResource(path= "family_details")
public interface FamilyDetailsRepository extends JpaRepository<FamilyDetails, Long> {
    Optional<FamilyDetails> findByFirstName(String firstName);


}