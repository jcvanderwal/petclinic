package com.example.petclinic.app;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.HomePage;
import org.apache.isis.applib.annotation.NatureOfService;

@DomainService(nature = NatureOfService.DOMAIN)
public class HomepageService {

    @HomePage
    public Homepage homepage() {
        return new Homepage();
    }

}
