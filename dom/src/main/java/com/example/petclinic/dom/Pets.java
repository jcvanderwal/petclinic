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
package com.example.petclinic.dom;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.MemberOrder;

@DomainService
public class Pets {

    public String getId() {
        return "pet";
    }

    public String iconName() {
        return "Pet";
    }

    public List<Pet> allPets() {
        return container.allInstances(Pet.class);
    }

    public List<Pet> findByName(String name) {
        List<Pet> result = new ArrayList<>();
        for (Pet pet : allPets()) {
            if (pet.getName().contains(name))
            result.add(pet);
        }
        return result;
    }

    @MemberOrder(sequence = "2")
    public Pet addPet(
            final String name,
            final PetSpecies petSpecies) {
        final Pet obj = container.newTransientInstance(Pet.class);
        obj.setName(name);
        obj.setSpecies(petSpecies);
        container.persistIfNotAlready(obj);
        return obj;
    }

    @Inject
    DomainObjectContainer container;

}
