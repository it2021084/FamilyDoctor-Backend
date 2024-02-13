package gr.hua.dit.ds.springbootdemo.dao;

import gr.hua.dit.ds.springbootdemo.entity.Citizen;

import java.util.List;
public interface CitizenDAO {
    public List<Citizen> getCitizens();
    public Citizen getCitizen(Long citizen_id);
    public  Citizen saveCitizen(Citizen citizen);
    public void deleteCitizen(Integer citizen_id);
    void updateCitizen(Citizen citizen);




}
