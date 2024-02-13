package gr.hua.dit.ds.springbootdemo.rest;

import gr.hua.dit.ds.springbootdemo.dao.CitizenDAO;
import gr.hua.dit.ds.springbootdemo.entity.Citizen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/citizen")
public class CitizenRestController {

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
}