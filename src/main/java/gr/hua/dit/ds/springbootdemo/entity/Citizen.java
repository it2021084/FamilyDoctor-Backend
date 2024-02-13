package gr.hua.dit.ds.springbootdemo.entity;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;

@Entity
public class Citizen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private String homeLocation;

    @Column
    private String email;

    @Column
    private Integer familyMembers=1;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @OneToMany(mappedBy = "citizen",cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<FamilyDetails> familyDetails;




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

    public String getHomeLocation() {
        return homeLocation;
    }

    public void setHomeLocation(String homeLocation) {
        this.homeLocation = homeLocation;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getFamilyMembers() {
        return familyMembers;
    }

    public void setFamilyMembers(Integer familyMembers) {
        this.familyMembers = familyMembers;
    }

    public Citizen(Long id, String firstName, String lastName, String homeLocation, String email,Integer familyMembers, Doctor doctor) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.homeLocation = homeLocation;
        this.email = email;
        this.doctor = doctor;
        this.familyMembers=familyMembers;
    }

    @Override
    public String toString() {
        return "Citizen{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", familyDetails='" + familyDetails + '\'' +
                ", homeLocation='" + homeLocation + '\'' +
                ", email='" + email + '\'' +
                "familyMembers" +familyMembers + '\'' +
                ", doctor=" + doctor +
                '}';
    }



    public Citizen() {
        this.familyDetails = new ArrayList<>();
    }

    public void addFamilyDetails(FamilyDetails familyDetails) {
        this.familyDetails.add(familyDetails);
        familyDetails.setCitizen(this);
    }



    public List<FamilyDetails> getFamilyDetails() {
        return familyDetails;
    }

    public void setFamilyDetails(List<FamilyDetails> familyDetails) {
        this.familyDetails = familyDetails;
    }



}
