package gr.hua.dit.ds.springbootdemo.rest;

import gr.hua.dit.ds.springbootdemo.dao.DoctorDAO;
import gr.hua.dit.ds.springbootdemo.entity.Citizen;
import gr.hua.dit.ds.springbootdemo.entity.Doctor;
import gr.hua.dit.ds.springbootdemo.repo.CitizenRepository;
import gr.hua.dit.ds.springbootdemo.repo.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/doctors")
@CrossOrigin(origins = "http://localhost:5173")
public class DoctorRestController {

    @Autowired
    private DoctorDAO doctorDAO;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private CitizenRepository citizenRepository;

    @GetMapping("")
    public List<Doctor> getDoctors(){
        return doctorDAO.getDoctors();
    }

    @PostMapping("")
    public Doctor saveDoctor(@RequestBody Doctor doctor){
        return  doctorDAO.saveDoctor(doctor);
    }



    @GetMapping("/{id}")
    public ResponseEntity<Doctor> getDoctorById(@PathVariable Long id) {
        Optional<Doctor> optionalDoctor = doctorRepository.findById(id);
        return optionalDoctor.map(doctor -> new ResponseEntity<>(doctor, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/updateDoctor")
    public ResponseEntity<String> updateDoctor(@RequestBody Doctor updatedDoctor, Authentication authentication) {
        // Get the currently authenticated doctor's username
        String currentUsername = authentication.getName();

        Optional<Doctor> optionalDoctor = doctorRepository.findByEmail(currentUsername);
        if (optionalDoctor.isPresent()) {
            Doctor existingDoctor = optionalDoctor.get();

            // Update only allowed fields, adjust accordingly
            existingDoctor.setFirstName(updatedDoctor.getFirstName());
            existingDoctor.setLastName(updatedDoctor.getLastName());
            existingDoctor.setEmail(updatedDoctor.getEmail());
            existingDoctor.setLocation(updatedDoctor.getLocation());
            existingDoctor.setMaxPatients(updatedDoctor.getMaxPatients());

            doctorRepository.save(existingDoctor);

            return ResponseEntity.ok("Doctor information updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Doctor not found");
        }
    }


    @GetMapping("/{id}/patients")
    public ResponseEntity<List<Citizen>> getPatientsByDoctorId(@PathVariable Long id) {
        System.out.println("Fetching patients for doctor ID: " + id);

        Optional<Doctor> doctorOptional = doctorRepository.findById(id);

        if (doctorOptional.isPresent()) {
            Doctor doctor = doctorOptional.get();
            List<Citizen> patients = doctor.getCitizens();
            System.out.println("Patients found: " + patients);
            return ResponseEntity.ok(patients);
        } else {
            System.out.println("Doctor not found for ID: " + id);
            return ResponseEntity.notFound().build();
        }
    }
}
