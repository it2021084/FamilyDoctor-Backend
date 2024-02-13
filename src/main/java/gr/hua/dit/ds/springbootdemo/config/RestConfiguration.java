package gr.hua.dit.ds.springbootdemo.config;


import gr.hua.dit.ds.springbootdemo.entity.Citizen;
import gr.hua.dit.ds.springbootdemo.entity.Doctor;
import gr.hua.dit.ds.springbootdemo.entity.FamilyDetails;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
public class RestConfiguration implements RepositoryRestConfigurer {

    @Override
    public void configureRepositoryRestConfiguration(
            RepositoryRestConfiguration config, CorsRegistry cors) {
        config.exposeIdsFor(Citizen.class);
        config.exposeIdsFor(Doctor.class);
        config.exposeIdsFor(FamilyDetails.class);

    }
}
