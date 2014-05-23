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

package com.example.petclinic.fixture;

import com.example.petclinic.dom.Owner;
import com.example.petclinic.dom.Owners;
import com.example.petclinic.dom.Pet;
import com.example.petclinic.dom.PetSpecies;
import com.example.petclinic.dom.Pets;

import org.apache.isis.applib.fixtures.AbstractFixture;
import org.apache.isis.objectstore.jdo.applib.service.support.IsisJdoSupport;

public class PetClinicFixture extends AbstractFixture {

    @Override
    public void install() {

        isisJdoSupport.executeUpdate("delete from \"Pet\"");

        installObjects();

        getContainer().flush();
    }

    private void installObjects() {

        create("Bello", PetSpecies.DOG, owners.addOwner("Bill", "Gates"));
        create("Hector", PetSpecies.DOG, owners.addOwner("Larry", "Ellison"));
        create("Tweety", PetSpecies.BIRD, owners.addOwner("Mark", "Zuckerberg"));
    }

    // //////////////////////////////////////

    private Pet create(final String name, final PetSpecies petSpecies, final Owner owner) {
        return pets.addPet(name, petSpecies, owner);
    }
    

    // //////////////////////////////////////
    // Injected services
    // //////////////////////////////////////

    @javax.inject.Inject
    private Pets pets;

    @javax.inject.Inject
    private Owners owners;

    @javax.inject.Inject
    private IsisJdoSupport isisJdoSupport;

}
