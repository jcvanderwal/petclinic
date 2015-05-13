package com.example.petclinic.app;

import java.util.List;
import java.util.SortedSet;

import javax.inject.Inject;

import com.example.petclinic.dom.Owner;
import com.example.petclinic.dom.Owners;
import com.example.petclinic.dom.Pet;
import com.example.petclinic.dom.PetSpecies;
import com.example.petclinic.dom.Pets;
import com.example.petclinic.dom.Visit;
import com.example.petclinic.dom.Visits;
import com.google.common.base.Predicate;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.RenderType;
import org.apache.isis.applib.annotation.ViewModel;

@ViewModel
public class Homepage {

    public Homepage() {
    }

    public String title() {
        return "Homepage";
    }

    @CollectionLayout(render = RenderType.EAGERLY)
    public List<Visit> getActiveVisits() {
        return container.allMatches(Visit.class, (Predicate<Visit>) input -> input.getCheckOutTime() == null);
    }

    @CollectionLayout(render = RenderType.EAGERLY)
    public List<Visit> getInactiveVisits() {
        return container.allMatches(Visit.class, (Predicate<Visit>) input -> input.getCheckOutTime() != null);
    }

    public Homepage visitNewPetAndOwner(
            final String petName,
            final PetSpecies species,
            final String ownerFirstName,
            final String ownerLastName,
            final @ParameterLayout(multiLine = 3) String notes
    ) {
        vists.addVisit(pets.addPet(petName, species, owners.addOwner(ownerFirstName, ownerLastName)), notes);
        return this;
    }

    public Homepage visitForExistingPet(
            final Pet pet,
            final @ParameterLayout(multiLine = 3) String notes
    ) {
        vists.addVisit(pet, notes);
        return this;
    }

    public Homepage visitForExistingOwner(
            final Owner owner,
            final Pet pet,
            final @ParameterLayout(multiLine = 3) String notes
    ) {
        vists.addVisit(pet, notes);
        return this;
    }

    public SortedSet<Pet> choices1VisitForExistingOwner(
            final Owner owner,
            final Pet pet,
            final String notes
    ) {
        return owner == null ? null : owner.getPets();
    }

    @Inject
    private Visits vists;

    @Inject
    private Pets pets;

    @Inject
    private Owners owners;

    @Inject
    private DomainObjectContainer container;
}
