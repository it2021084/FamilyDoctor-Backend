package gr.hua.dit.ds.springbootdemo.dao;

import gr.hua.dit.ds.springbootdemo.entity.Doctor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class DoctorDAOImpl implements DoctorDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public  List<Doctor> getDoctors(){
        TypedQuery<Doctor> query=entityManager.createQuery("from Doctor", Doctor.class);
        return query.getResultList();
    }

    @Override
    public Doctor getDoctor(Integer doctor_id) {
        return entityManager.find(Doctor.class,doctor_id);
    }

    @Override
    @Transactional
    public Doctor saveDoctor(Doctor doctor){
        System.out.println("doctor"+ doctor.getId());
        if(doctor.getId()==null){
            entityManager.persist(doctor);
        }else {
            entityManager.merge(doctor);
        }
        return doctor;
    }

    @Override
    @Transactional
    public void deleteDoctor(Integer doctor_id){
        System.out.println("Deleting doctor with id:"+ doctor_id);
        entityManager.remove(entityManager.find(Doctor.class,doctor_id));
    }






}
