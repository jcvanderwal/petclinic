/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package webapp.prototyping;

import java.util.List;

import com.example.petclinic.dom.Pet;
import com.example.petclinic.dom.Pets;
import com.example.petclinic.fixture.PetClinicFixture;

import org.apache.isis.applib.AbstractService;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Prototype;
import org.apache.isis.core.runtime.fixtures.FixturesInstallerDelegate;

/**
 * Enables fixtures to be installed from the application.
 */
@Named("Prototyping")
public class PetClinicFixturesService extends AbstractService {

    @Prototype
    public String installFixtures() {
        final FixturesInstallerDelegate installer = new FixturesInstallerDelegate().withOverride();
        installer.addFixture(new PetClinicFixture());
        installer.installFixtures();
        return "Example fixtures installed";
    }

    // //////////////////////////////////////

    @Prototype
    public Pet installFixturesAndReturnFirst() {
        installFixtures();
        List<Pet> all = pets.allPets();
        return !all.isEmpty() ? all.get(0) : null;
    }

    
    // //////////////////////////////////////

    private Pets pets;
    public void injectPets(Pets pets) {
        this.pets = pets;
    }

}