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
package com.example.petclinic.integtests.tests;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import javax.inject.Inject;

import com.example.petclinic.dom.Pet;
import com.example.petclinic.dom.PetSpecies;
import com.example.petclinic.dom.Pets;
import com.example.petclinic.fixture.modules.PetFixture;
import com.google.common.base.Throwables;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.fixturescripts.FixtureScripts;

import static org.assertj.core.api.Assertions.assertThat;

public class PetsIntegTest extends PetClinicIntegTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Inject
    FixtureScripts fixtureScripts;

    @Inject
    Pets pets;

    @Inject
    DomainObjectContainer container;

    public static class AllPets extends PetsIntegTest {

        @Test
        public void happyCase() throws Exception {

            // given
            PetFixture fs = new PetFixture();
            fixtureScripts.runFixtureScript(fs, null);
            nextTransaction();

            // when
            final List<Pet> all = wrap(pets).allPets();

            // then
            assertThat(all).hasSize(3);

        }

    }

    public static class FindByName extends PetsIntegTest {

        @Test
        public void happyCase() throws Exception {

            // given
            PetFixture fs = new PetFixture();
            fixtureScripts.runFixtureScript(fs, null);
            nextTransaction();

            // when
            final List<Pet> result = pets.findByName("e");

            // then
            assertThat(result).hasSize(3);

        }

    }

    public static class AddPet extends PetsIntegTest {

        @Test
        public void happyCase() throws Exception {

            // given
            // when
            wrap(pets).addPet("Pookie", PetSpecies.CAT);

            // then
            final List<Pet> all = wrap(pets).allPets();
            assertThat(all).hasSize(1);
        }

        @Test
        public void whenAlreadyExists() throws Exception {

            // given
            fixtureScripts.runFixtureScript(new PetFixture(), null);

            // then
            expectedException.expectCause(causalChainContains(SQLIntegrityConstraintViolationException.class));

            // when
            wrap(pets).addPet("Bello", PetSpecies.DOG);
            nextTransaction();
        }

        private static Matcher<? extends Throwable> causalChainContains(final Class<?> cls) {
            return new TypeSafeMatcher<Throwable>() {
                @Override
                protected boolean matchesSafely(Throwable item) {
                    final List<Throwable> causalChain = Throwables.getCausalChain(item);
                    for (Throwable throwable : causalChain) {
                        if(cls.isAssignableFrom(throwable.getClass())){
                            return true;
                        }
                    }
                    return false;
                }

                @Override
                public void describeTo(Description description) {
                    description.appendText("exception with causal chain containing " + cls.getSimpleName());
                }
            };
        }

    }

}
