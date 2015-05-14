package com.example.petclinic.fixture.scenarios;

import com.example.petclinic.fixture.modules.PetFixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;

public class PetClinicDemoFixture extends FixtureScript {

    @Override protected void execute(final ExecutionContext executionContext) {

        executionContext.executeChild(this, new PetFixture());

    }

}
