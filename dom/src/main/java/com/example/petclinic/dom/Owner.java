package com.example.petclinic.dom;

import java.util.SortedSet;
import java.util.TreeSet;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.DatastoreIdentity;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.Version;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.annotation.Bounded;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Render;
import org.apache.isis.applib.annotation.Render.Type;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.util.ObjectContracts;


@PersistenceCapable(identityType=IdentityType.DATASTORE)
@DatastoreIdentity(strategy=IdGeneratorStrategy.IDENTITY, column="id")
@Version(strategy=VersionStrategy.VERSION_NUMBER, column="version")
@Bounded
public class Owner implements Comparable<Owner>{

    private String firstName;

    @Column(allowsNull="false")
    @MemberOrder(sequence = "1")
    @Title(sequence="2", prepend=", ")
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    // //////////////////////////////////////
    
    private String lastName;
    
    @Column(allowsNull="false")
    @MemberOrder(sequence = "2")
    @Title(sequence="1")
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
        
    // {{ Pets (Collection)
    @Persistent(mappedBy = "owner", dependentElement = "false")
    private SortedSet<Pet> pets = new TreeSet<Pet>();

    @Render(Type.EAGERLY)
    @MemberOrder(sequence = "1")
    public SortedSet<Pet> getPets() {
        return pets;
    }

    public void setPets(final SortedSet<Pet> pets) {
        this.pets = pets;
    }
    // }}
    
    @Override
    public int compareTo(Owner o) {
        return ObjectContracts.compare(this, o, "firstName");
    }

}
