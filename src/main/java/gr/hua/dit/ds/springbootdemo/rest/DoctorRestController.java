package gr.hua.dit.ds.springbootdemo.rest;

import gr.hua.dit.ds.springbootdemo.dao.DoctorDAO;
import gr.hua.dit.ds.springbootdemo.entity.Doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctor")
public class DoctorRestController {

    @Autowired
    private DoctorDAO doctorDAO;

    @GetMapping("")
    public List<Doctor> getDoctors(){
        return doctorDAO.getDoctors();
    }

    @PostMapping("")
    public Doctor saveDoctor(@RequestBody Doctor doctor){
        return  doctorDAO.saveDoctor(doctor);
    }
}