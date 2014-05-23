package com.example.petclinic.app;

import java.util.List;

import javax.inject.Inject;

import com.example.petclinic.dom.Visit;
import com.example.petclinic.dom.Visits;

import org.joda.time.DateTime;

import org.apache.isis.applib.AbstractViewModel;
import org.apache.isis.applib.annotation.Disabled;
import org.apache.isis.applib.annotation.Render;
import org.apache.isis.applib.annotation.Render.Type;

public class PetClinicDashboard extends AbstractViewModel {

    private String memento;

    @Override
    public String viewModelMemento() {
        return memento;
    }

    @Override
    public void viewModelInit(String memento) {
        this.memento = memento;
    }

    @Render(Type.EAGERLY)
    @Disabled
    public List<Visit> activeVisits() {
        return visits.findActive();
    }

    @Render(Type.EAGERLY)
    @Disabled
    public List<Visit> visitsClosedToday() {
        return visits.findClosedSinceDate(DateTime.now().minusDays(1));
    }

    @Inject
    private Visits visits;
}
