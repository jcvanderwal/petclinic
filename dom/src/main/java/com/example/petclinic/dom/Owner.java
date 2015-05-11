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

import java.util.SortedSet;
import java.util.TreeSet;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.DatastoreIdentity;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.Unique;
import javax.jdo.annotations.Version;
import javax.jdo.annotations.VersionStrategy;

import com.google.common.base.Predicate;
import com.google.common.collect.ComparisonChain;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.RenderType;
import org.apache.isis.applib.annotation.Title;

@PersistenceCapable(identityType = IdentityType.DATASTORE)
@DatastoreIdentity(strategy = IdGeneratorStrategy.IDENTITY, column = "id")
@Version(strategy = VersionStrategy.VERSION_NUMBER, column = "version")
@Unique(name = "Owner_firstName_lastName_UNQ", members = { "firstName", "lastName" })
public class Owner implements Comparable<Owner> {

    //region > First Name (property)
    private String firstName;

    @Column(allowsNull = "false")
    @Title(sequence = "2")
    @MemberOrder(sequence = "1")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }
    //endregion

    //region > Last Name (property)
    private String lastName;

    @Column(allowsNull = "false")
    @Title(sequence = "1", append = ", ")
    @MemberOrder(sequence = "1")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }
    //endregion

    //region > pets (collection)
    @Persistent(mappedBy = "owner", dependentElement = "false")
    private SortedSet<Pet> pets = new TreeSet<Pet>();

    @MemberOrder(sequence = "3")
    @CollectionLayout(render = RenderType.EAGERLY)
    public SortedSet<Pet> getPets() {
        return pets;
    }

    public void setPets(final SortedSet<Pet> pets) {
        this.pets = pets;
    }
    //endregion

    //region > compareTo
    @Override
    public int compareTo(Owner other) {
        return ComparisonChain.start()
                .compare(this.getLastName(), other.getLastName())
                .compare(this.getFirstName(), other.getFirstName())
                .result();
    }
    //endregion

    //region > Injected
    @javax.inject.Inject
    @SuppressWarnings("unused")
    private DomainObjectContainer container;
    //endregion

    //region > predicates
    public static class Predicates {

        public static Predicate<Owner> contains(final String string) {
            return new Predicate<Owner>() {
                @Override
                public boolean apply(final Owner owner) {
                    return owner.getFirstName().toLowerCase().contains(string.toLowerCase()) ||
                            owner.getLastName().toLowerCase().contains(string.toLowerCase());
                }
            };
        }
    }
    //endregion
}
