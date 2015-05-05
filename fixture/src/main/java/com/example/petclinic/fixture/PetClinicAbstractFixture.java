package com.example.petclinic.fixture;

import com.example.petclinic.dom.Pet;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.objectstore.jdo.applib.service.support.IsisJdoSupport;

public abstract class PetClinicAbstractFixture extends FixtureScript {

    public void deleteFrom(final Class<Pet> cls) {
        isisJdoSupport.executeUpdate(String.format("delete from \"%s\"", cls.getSimpleName()));
    }

    @javax.inject.Inject
    private IsisJdoSupport isisJdoSupport;

}
