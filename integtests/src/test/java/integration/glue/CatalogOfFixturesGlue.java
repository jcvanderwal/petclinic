/**
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package integration.glue;

import com.example.petclinic.dom.Pet;
import com.example.petclinic.fixture.PetClinicFixture;

import cucumber.api.java.Before;

import org.apache.isis.core.specsupport.scenarios.InMemoryDB;
import org.apache.isis.core.specsupport.specs.CukeGlueAbstract;

public class CatalogOfFixturesGlue extends CukeGlueAbstract {

    
    @Before(value={"@unit", "@PetClinicFixture"}, order=20000)
    public void unitFixtures() throws Throwable {
        final InMemoryDB inMemoryDB = new InMemoryDBForPetClinic(this.scenarioExecution());
        inMemoryDB.getElseCreate(Pet.class, "Foo");
        inMemoryDB.getElseCreate(Pet.class, "Bar");
        inMemoryDB.getElseCreate(Pet.class, "Baz");
        putVar("isis", "in-memory-db", inMemoryDB);
    }

    // //////////////////////////////////////

    @Before(value={"@integration", "@PetClinicFixture"}, order=20000)
    public void integrationFixtures() throws Throwable {
        scenarioExecution().install(new PetClinicFixture());
    }
    

}
