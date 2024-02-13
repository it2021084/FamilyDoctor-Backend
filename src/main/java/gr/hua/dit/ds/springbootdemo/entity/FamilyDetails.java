package gr.hua.dit.ds.springbootdemo.entity;

import jakarta.persistence.*;

@Entity
public class FamilyDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String firstName;
    @Column
    private String lastName;


    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name="citizen_id" )
    private Citizen citizen;



    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;


    public FamilyDetails() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Citizen getCitizen() {
        return citizen;
    }

    public void setCitizen(Citizen citizen) {
        this.citizen = citizen;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }



    public FamilyDetails(Long id, String firstName, String lastName, Citizen citizen) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.citizen = citizen;
        citizen.setFamilyMembers(citizen.getFamilyMembers()+1);
    }

    @Override
    public String toString() {
        return "FamilyDetails{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", citizen=" + citizen +
                '}';
    }


}
