package gr.hua.dit.ds.springbootdemo.dao;

import gr.hua.dit.ds.springbootdemo.entity.Citizen;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CitizenDAOImpl implements CitizenDAO{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public List<Citizen> getCitizens(){
        TypedQuery<Citizen> query=entityManager.createQuery("from Citizen", Citizen.class);
        return query.getResultList();
    }


    @Override
    public Citizen getCitizen(Long citizen_id) {
        return entityManager.find(Citizen.class, citizen_id);

    }


    @Override
    @Transactional
    public Citizen saveCitizen(Citizen citizen){
        System.out.println("citizen"+ citizen.getId());
        if(citizen.getId()==null){
            entityManager.persist(citizen);
        }else {
            entityManager.merge(citizen);
        }
        return citizen;
    }

    @Override
    @Transactional
    public void deleteCitizen(Integer citizen_id){
        System.out.println("Deleting citizen with id:" +citizen_id);
        entityManager.remove((entityManager.find(Citizen.class,citizen_id)));
    }

    @Override
    public void updateCitizen(Citizen citizen) {
        entityManager.merge(citizen);
    }


}
