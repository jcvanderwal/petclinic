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

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.MemberOrder;

@DomainService
public class Owners {

    public String getId() {
        return "owner";
    }

    public String iconName() {
        return "Owner";
    }

    public List<Owner> allOwners() {
        return container.allInstances(Owner.class);
    }

    public List<Owner> findByName(String name) {
        return container.allMatches(Owner.class, Owner.Predicates.contains(name));
    }

    @MemberOrder(sequence = "2")
    public Owner addOwner(
            final String firstName,
            final String lastName) {
        final Owner obj = container.newTransientInstance(Owner.class);
        obj.setFirstName(firstName);
        obj.setLastName(lastName);
        container.persistIfNotAlready(obj);
        return obj;
    }

    @Inject
    DomainObjectContainer container;

}
