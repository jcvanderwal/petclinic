package com.example.petclinic.app;

import javax.inject.Inject;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.HomePage;

public class PetClinicDashboardService {

    @HomePage
    public PetClinicDashboard dashboard() {
        return container.newViewModelInstance(PetClinicDashboard.class, "id");
    }

    @Inject
    private DomainObjectContainer container;

}
