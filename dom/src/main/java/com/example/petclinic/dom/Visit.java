package com.example.petclinic.dom;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.DatastoreIdentity;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.Version;
import javax.jdo.annotations.VersionStrategy;

import org.joda.time.DateTime;

import org.apache.isis.applib.annotation.Disabled;
import org.apache.isis.applib.annotation.Immutable;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.MultiLine;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.util.ObjectContracts;

@PersistenceCapable(identityType = IdentityType.DATASTORE)
@DatastoreIdentity(strategy = IdGeneratorStrategy.IDENTITY, column = "id")
@Version(strategy = VersionStrategy.VERSION_NUMBER, column = "version")
@Immutable
public class Visit implements Comparable<Visit> {

    private Pet pet;

    @Column(name = "petId", allowsNull = "true")
    @MemberOrder(sequence = "1")
    @Title(sequence = "1")
    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    // //////////////////////////////////////

    private DateTime checkInTime;

    @MemberOrder(sequence = "2")
    @Column(allowsNull = "false")
    @Disabled
    @Persistent
    @Title(sequence = "2", prepend = "-")
    public DateTime getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(DateTime checkInTime) {
        this.checkInTime = checkInTime;
    }

    // //////////////////////////////////////

    private DateTime checkOutTime;

    @MemberOrder(sequence = "3")
    @Column(allowsNull = "true")
    @Disabled
    @Persistent
    @Title(sequence = "3", prepend = "-")
    public DateTime getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(DateTime checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    // //////////////////////////////////////

    private String diagnose;

    @MemberOrder(sequence = "4")
    @MultiLine(numberOfLines = 5)
    @Column(allowsNull = "true")
    public String getDiagnose() {
        return diagnose;
    }

    public void setDiagnose(String diagnose) {
        this.diagnose = diagnose;
    }

    // //////////////////////////////////////

    @Override
    public int compareTo(Visit o) {
        return ObjectContracts.compare(this, o, "pet,checkInTime");
    }

}
