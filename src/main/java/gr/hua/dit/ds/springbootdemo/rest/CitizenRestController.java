package gr.hua.dit.ds.springbootdemo.rest;

import gr.hua.dit.ds.springbootdemo.dao.CitizenDAO;
import gr.hua.dit.ds.springbootdemo.entity.Citizen;
import gr.hua.dit.ds.springbootdemo.entity.Doctor;
import gr.hua.dit.ds.springbootdemo.entity.FamilyDetails;
import gr.hua.dit.ds.springbootdemo.repo.CitizenRepository;
import gr.hua.dit.ds.springbootdemo.repo.DoctorRepository;
import gr.hua.dit.ds.springbootdemo.service.FamilyDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/citizens")
@CrossOrigin(origins = "http://localhost:5173")

public class CitizenRestController {



        @Autowired
        private CitizenRepository citizenRepository;

        @Autowired
        private DoctorRepository doctorRepository;

        @Autowired
        private FamilyDetailsService familyDetailsService;
    @Autowired
    private CitizenDAO citizenDAO;

    @GetMapping("")
    public List<Citizen> getCitizens(){
        return citizenDAO.getCitizens();
    }

    @PostMapping("")
    public Citizen saveCitizen(@RequestBody Citizen citizen){
        return  citizenDAO.saveCitizen(citizen);
    }
        @GetMapping("/doctorsInSameCity")
        public ResponseEntity<List<Doctor>> getDoctorsInSameCity(@RequestParam String homeLocation) {
            try {
                List<Doctor> doctorsInSameCity = citizenRepository.findDoctorsByHomeLocation(homeLocation);
                return ResponseEntity.ok(doctorsInSameCity);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
        }

        @PostMapping("/registerFamilyDoctor")
        public ResponseEntity<String> registerFamilyDoctor(@RequestParam String citizenEmail, @RequestParam String homeLocation) {
            try {
                // Find the Citizen by email
                Optional<Citizen> optionalCitizen = citizenRepository.findByEmail(citizenEmail);
                if (optionalCitizen.isPresent()) {
                    Citizen citizen = optionalCitizen.get();

                    // Retrieve doctors in the same city
                    ResponseEntity<List<Doctor>> doctorsResponse = getDoctorsInSameCity(homeLocation);
                    List<Doctor> availableDoctors = doctorsResponse.getBody();

                    // Attempt to register with available doctors
                    if (availableDoctors != null) {
                        for (Doctor doctor : availableDoctors) {
                            ResponseEntity<String> registrationResult = registerWithDoctor(citizen, doctor.getId());
                            if (registrationResult.getStatusCode() == HttpStatus.OK) {
                                return registrationResult; // Successful registration with a doctor
                            }
                        }
                    }

                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No available doctors in the same city");
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Citizen not found");
                }
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error registering family doctor");
            }
        }


        private ResponseEntity<String> registerWithDoctor(Citizen citizen, Long doctorId) {
            // Find the Doctor by ID
            Optional<Doctor> optionalDoctor = doctorRepository.findById(doctorId);
            if (optionalDoctor.isPresent()) {
                Doctor doctor = optionalDoctor.get();

                // Check if the doctor can accept more patients
                if (doctor.getCitizens().size() < doctor.getMaxPatients()) {
                    // Set the family doctor for the citizen
                    citizen.setDoctor(doctor);
                    citizenRepository.save(citizen);

                    // Add the citizen to the doctor's list of patients
                    doctor.getCitizens().add(citizen);
                    doctorRepository.save(doctor);

                    return ResponseEntity.ok("Family doctor registered successfully");
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Doctor cannot accept more patients");
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Doctor not found");
            }
        }

        private List<Doctor> getAvailableDoctors() {

            return doctorRepository.findDoctorsWithAvailableSlots();
        }


        @GetMapping("/{id}")
        public ResponseEntity<Citizen> getCitizenById(@PathVariable Long id) {
            Optional<Citizen> optionalCitizen = citizenRepository.findById(id);
            return optionalCitizen.map(citizen -> new ResponseEntity<>(citizen, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }

        @PutMapping("/updateCitizen")
        public ResponseEntity<String> updateCitizen(@RequestBody Citizen updatedCitizen, Authentication authentication) {
            // Get the currently authenticated citizen's username
            String currentUsername = authentication.getName();

            Optional<Citizen> optionalCitizen = citizenRepository.findByEmail(currentUsername);
            if (optionalCitizen.isPresent()) {
                Citizen existingCitizen = optionalCitizen.get();

                // Update only allowed fields, adjust accordingly
                existingCitizen.setFirstName(updatedCitizen.getFirstName());
                existingCitizen.setLastName(updatedCitizen.getLastName());
                existingCitizen.setEmail(updatedCitizen.getEmail());
                existingCitizen.setHomeLocation(updatedCitizen.getHomeLocation());
                existingCitizen.setFamilyMembers(updatedCitizen.getFamilyMembers());

                citizenRepository.save(existingCitizen);

                return ResponseEntity.ok("Citizen information updated successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Citizen not found");
            }
        }


        @PostMapping("/registerFamilyMember")
        public ResponseEntity<String> registerFamilyMember(@RequestBody FamilyDetails familyDetails, Authentication authentication) {
            try {
                // Get the currently authenticated citizen's username
                String currentUsername = authentication.getName();

                Optional<Citizen> optionalCitizen = citizenRepository.findByEmail(currentUsername);
                if (optionalCitizen.isPresent()) {
                    Citizen citizen = optionalCitizen.get();

                    // Save family member details
                    familyDetailsService.saveFamilyDetails(familyDetails, citizen.getId());

                    return ResponseEntity.ok("Family member registered successfully");
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Citizen not found");
                }
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error registering family member");
            }
        }

    }

