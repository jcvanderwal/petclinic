package com.example.petclinic.fixture.modules;/*
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

import com.example.petclinic.dom.Owner;
import com.example.petclinic.dom.Owners;
import com.example.petclinic.dom.Pet;
import com.example.petclinic.dom.PetSpecies;
import com.example.petclinic.dom.Pets;
import com.example.petclinic.fixture.PetClinicAbstractFixture;

public class PetFixture extends PetClinicAbstractFixture {

    public PetFixture() {
        withDiscoverability(Discoverability.DISCOVERABLE);
    }

    @Override protected void execute(final ExecutionContext ec) {

        deleteFrom(Pet.class);
        deleteFrom(Owner.class);

        create(ec, "Bello", PetSpecies.DOG, "Bill", "Gates");
        create(ec, "Hector", PetSpecies.CAT, "Linus", "Torvalds");
        create(ec, "Tweety", PetSpecies.BIRD, "Mark", "Zuckerberg");

    }

    private void create(
            final ExecutionContext ec,
            final String name,
            final PetSpecies species,
            final String firstName,
            final String lastName) {
        final Owner owner = owners.addOwner(firstName, lastName);
        final Pet pet = pets.addPet(name, species, owner);
        ec.addResult(this, pet);
    }

    // //////////////////////////////////////
    // Injected services
    // //////////////////////////////////////

    @javax.inject.Inject
    private Pets pets;

    @javax.inject.Inject
    private Owners owners;

}
