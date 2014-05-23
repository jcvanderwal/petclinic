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
import org.apache.isis.applib.annotation.ActionSemantics;
import org.apache.isis.applib.annotation.ActionSemantics.Of;
import org.apache.isis.applib.annotation.Bookmarkable;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.NotContributed;
import org.apache.isis.applib.annotation.NotContributed.As;
import org.apache.isis.applib.annotation.NotInServiceMenu;
import org.apache.isis.applib.annotation.Render;
import org.apache.isis.applib.annotation.Render.Type;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.clock.ClockService;

public class Visits {

    // //////////////////////////////////////
    // Identification in the UI
    // //////////////////////////////////////

    public String getId() {
        return "visit";
    }

    public String iconName() {
        return "Visit";
    }

    // //////////////////////////////////////
    // List (action)
    // //////////////////////////////////////

    @Bookmarkable
    @ActionSemantics(Of.SAFE)
    @MemberOrder(sequence = "1")
    public List<Visit> allVisits() {
        return container.allInstances(Visit.class);
    }

    // //////////////////////////////////////
    // Create (action)
    // //////////////////////////////////////

    @MemberOrder(sequence = "2")
    public Visit addVisit(
            Pet pet,
            @Named("Note") String note) {
        final Visit obj = container.newTransientInstance(Visit.class);
        obj.setPet(pet);
        obj.checkIn(note);
        container.persistIfNotAlready(obj);
        return obj;
    }

    // //////////////////////////////////////

    @Render(Type.EAGERLY)
    @NotInServiceMenu
    @NotContributed(As.ACTION)
    public List<Visit> visits(Pet pet) {
        return container.allMatches(
                new QueryDefault<Visit>(Visit.class, "findByPet", "pet", pet));
    }

    @Hidden
    public List<Visit> findClosedSinceDate(DateTime dateTime) {
        return container.allMatches(
                new QueryDefault<Visit>(Visit.class, "findClosedSinceDate", "dateTime", dateTime));
    }

    @Hidden
    public List<Visit> findActive() {
        return container.allMatches(
                new QueryDefault<Visit>(Visit.class, "findActive"));
    }

    // //////////////////////////////////////
    // Injected services
    // //////////////////////////////////////

    @javax.inject.Inject
    DomainObjectContainer container;

    @Inject
    ClockService clockService;

}
