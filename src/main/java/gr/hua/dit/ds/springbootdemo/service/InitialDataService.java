package gr.hua.dit.ds.springbootdemo.service;

import com.github.javafaker.Faker;
import gr.hua.dit.ds.springbootdemo.entity.*;
import gr.hua.dit.ds.springbootdemo.repo.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;


import jakarta.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Service
public class InitialDataService {

    private static final int LAST_CITIZEN_ID = 10;
    private static final int LAST_DOCTOR_ID = 10;
    private static final int LAST_FAMILYDETAILS_ID=5;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CitizenRepository citizenRepository;
    private final DoctorRepository doctorRepository;
    private final FamilyDetailsRepository familyDetailsRepository;

    private final PasswordEncoder passwordEncoder;

    public InitialDataService(UserRepository userRepository,
                              RoleRepository roleRepository,
                              CitizenRepository citizenRepository,
                              DoctorRepository doctorRepository,
                              FamilyDetailsRepository familyDetailsRepository,
                              PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.citizenRepository = citizenRepository;
        this.doctorRepository = doctorRepository;
        this.familyDetailsRepository=familyDetailsRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private void createUsersAndRoles() {
        final List<String> rolesToCreate = List.of("ROLE_USER", "ROLE_DOCTOR" ,"ROLE_ADMIN");
        for (final String roleName : rolesToCreate) {
            roleRepository.findByName(roleName).orElseGet(() -> {
                roleRepository.save(new Role(roleName));
                return null;
            });
        }

        this.userRepository.findByUsername("user").orElseGet(() -> {
            User user = new User("user", "user@familydoctor.gr", this.passwordEncoder.encode("1234"));
            Set<Role> roles = new HashSet<>();
            roles.add(this.roleRepository.findByName("ROLE_USER").orElseThrow());
            roles.add(this.roleRepository.findByName("ROLE_ADMIN").orElseThrow());
            user.setRoles(roles);
            userRepository.save(user);
            return null;
        });

        this.userRepository.findByUsername("doctor").orElseGet(() -> {
            User user = new User("doctor", "doctor@familydoctor.gr", this.passwordEncoder.encode("1234"));
            Set<Role> roles = new HashSet<>();
            roles.add(this.roleRepository.findByName("ROLE_DOCTOR").orElseThrow());
            user.setRoles(roles);
            userRepository.save(user);
            return null;
        });

        this.userRepository.findByUsername("admin").orElseGet(() -> {
            User user = new User("admin", "admin@familydoctor.gr", this.passwordEncoder.encode("1234"));
            Set<Role> roles = new HashSet<>();
            roles.add(this.roleRepository.findByName("ROLE_USER").orElseThrow());
            roles.add(this.roleRepository.findByName("ROLE_ADMIN").orElseThrow());
            user.setRoles(roles);
            userRepository.save(user);
            return null;
        });


    }



    private void createCitizens() {

        final Faker faker = new Faker();


        final String specificHomeLocation1 = "Athens";
        final String specificEmail1 = "citizen1@familydoctor.com"; // Provide a unique email address
        final int specificFamilyMembers1 = 4; // Provide the desired number of family members

        // Check if the citizen with the specific email already exists
        if (!citizenRepository.findByEmail(specificEmail1).isPresent()) {
            Citizen citizen = new Citizen();
            citizen.setFirstName(faker.name().firstName());
            citizen.setLastName(faker.name().lastName());
            citizen.setEmail(specificEmail1);
            citizen.setHomeLocation(specificHomeLocation1);
            citizen.setFamilyMembers(specificFamilyMembers1);

            this.citizenRepository.save(citizen);
        }

        final String specificHomeLocation2 = "Athens";
        final String specificEmail2 = "citizen2@familydoctor.com"; // Provide a unique email address
        final int specificFamilyMembers2 = 4; // Provide the desired number of family members

        // Check if the citizen with the specific email already exists
        if (!citizenRepository.findByEmail(specificEmail2).isPresent()) {
            Citizen citizen = new Citizen();
            citizen.setFirstName(faker.name().firstName());
            citizen.setLastName(faker.name().lastName());
            citizen.setEmail(specificEmail2);
            citizen.setHomeLocation(specificHomeLocation2);
            citizen.setFamilyMembers(specificFamilyMembers2);

            this.citizenRepository.save(citizen);
        }

        for (int i = 3; i <= LAST_CITIZEN_ID; i++) {
            final String firstName = faker.name().firstName();
            final String lastName = faker.name().lastName();
            final String email = faker.internet().emailAddress();
            final String homeLocation= faker.address().city();
            final int familyMembers=faker.number().numberBetween(1,6);

            this.citizenRepository.findByEmail(email).orElseGet(()-> {
                Citizen citizen =new Citizen();
                citizen.setFirstName(firstName);
                citizen.setLastName(lastName);
                citizen.setEmail(email);
                citizen.setHomeLocation(homeLocation);
                citizen.setFamilyMembers(familyMembers);

                this.citizenRepository.save(citizen);
                return null;
            });
        }
    }

    private void createDoctors() {
        final Faker faker = new Faker();


        final String specificLocation1 = "Athens";
        final String specificDoctorEmail1 = "doctor1@familydoctor.com"; // Provide a unique email address
        final int specificMaxPatients1 = 6; // Provide the desired number of family members

        // Check if the citizen with the specific email already exists
        if (!doctorRepository.findByEmail(specificDoctorEmail1).isPresent()) {
            Doctor doctor  = new Doctor();
            doctor.setFirstName(faker.name().firstName());
            doctor.setLastName(faker.name().lastName());
            doctor.setEmail(specificDoctorEmail1);
            doctor.setLocation(specificLocation1);
            doctor.setMaxPatients(specificMaxPatients1);

            this.doctorRepository.save(doctor);
        }

        final String specificLocation2 = "Athens";
        final String specificDoctorEmail2 = "doctor2@familydoctor.com"; // Provide a unique email address
        final int specificMaxPatients2 = 6; // Provide the desired number of family members

        // Check if the citizen with the specific email already exists
        if (!doctorRepository.findByEmail(specificDoctorEmail2).isPresent()) {
            Doctor doctor  = new Doctor();
            doctor.setFirstName(faker.name().firstName());
            doctor.setLastName(faker.name().lastName());
            doctor.setEmail(specificDoctorEmail2);
            doctor.setLocation(specificLocation2);
            doctor.setMaxPatients(specificMaxPatients2);

            this.doctorRepository.save(doctor);
        }



        for (int i = 3; i <= LAST_DOCTOR_ID; i++) {
            final String firstName = faker.name().firstName();
            final String lastName = faker.name().lastName();
            final String email = faker.internet().emailAddress();
            final String location= faker.address().city();
            final int maxPatients=faker.number().numberBetween(1,15);

            this.doctorRepository.findByEmail(email).orElseGet(()-> {
                Doctor doctor  =new Doctor();
                doctor.setFirstName(firstName);
                doctor.setLastName(lastName);
                doctor.setEmail(email);
                doctor.setLocation(location);
                doctor.setMaxPatients(maxPatients);

                this.doctorRepository.save(doctor);
                return null;
            });
        }
    }


    private void createFamilyDetails() {
        for (int i = 1; i <= LAST_FAMILYDETAILS_ID; i++) {
            final Faker faker = new Faker(new Random(i));
            final String firstName = faker.name().firstName();
            final String lastName = faker.name().lastName();

            this.familyDetailsRepository.findByFirstName(firstName).orElseGet(() -> {
                FamilyDetails familyDetails = new FamilyDetails();
                familyDetails.setFirstName(firstName);
                familyDetails.setLastName(lastName);

                this.familyDetailsRepository.save(familyDetails);
                return null;
            });
        }
    }


    @PostConstruct
    public void setup() {
        createUsersAndRoles();
        createCitizens();
        createDoctors();
        createFamilyDetails();
    }
}
