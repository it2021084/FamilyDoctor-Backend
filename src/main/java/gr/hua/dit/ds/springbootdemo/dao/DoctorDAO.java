package gr.hua.dit.ds.springbootdemo.dao;

import gr.hua.dit.ds.springbootdemo.entity.Doctor;

import java.util.List;
public interface DoctorDAO {
    public List<Doctor> getDoctors();
    public Doctor getDoctor(Integer doctor_id);
    public Doctor saveDoctor(Doctor doctor);
    public void deleteDoctor(Integer doctor_id);

}
