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

import org.joda.time.DateTime;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.clock.ClockService;

@DomainService
public class Visits {

    public String getId() {
        return "visit";
    }

    public String iconName() {
        return "Visit";
    }


    @MemberOrder(sequence = "1")
    @Action(semantics = SemanticsOf.SAFE)
    public List<Visit> allVisits() {
        return container.allInstances(Visit.class);
    }

    @MemberOrder(sequence = "2")
    public Visit addVisit(
            final Pet pet,
            final @ParameterLayout(multiLine = 3) String notes) {
        final Visit obj = container.newTransientInstance(Visit.class);
        obj.setPet(pet);
        obj.setCheckInTime(DateTime.now()) ;
        obj.setNotes(notes);
        container.persistIfNotAlready(obj);
        return obj;
    }

    @javax.inject.Inject
    DomainObjectContainer container;

    @Inject
    ClockService clockService;

}
