package gr.hua.dit.ds.springbootdemo.service;

import gr.hua.dit.ds.springbootdemo.dao.CitizenDAO;
import gr.hua.dit.ds.springbootdemo.entity.Citizen;
import gr.hua.dit.ds.springbootdemo.entity.FamilyDetails;
import gr.hua.dit.ds.springbootdemo.repo.FamilyDetailsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class FamilyDetailsService {

    @Autowired
    private FamilyDetailsRepository familyDetailsRepository;
    @Autowired
    private CitizenDAO citizenDAO;



    @Transactional
    public void saveFamilyDetails(FamilyDetails familyDetails,Long citizen_id){
        try {
            Citizen citizen = citizenDAO.getCitizen(citizen_id);
            familyDetails.setCitizen(citizen);
            familyDetailsRepository.save(familyDetails);

            // Increment family members count
            citizen.setFamilyMembers(citizen.getFamilyMembers() + 1);
            citizenDAO.updateCitizen(citizen);
        } catch (Exception e) {

            throw new RuntimeException("Error saving family details", e);
        }
    }

    @Transactional
    public FamilyDetails getFamilyDetails(Long familyDetailsId){
        return familyDetailsRepository.findById(familyDetailsId).get();
    }

    public void deleteFamilyDetails(Long familyDetailsId){
        familyDetailsRepository.deleteById(familyDetailsId);
    }

}
