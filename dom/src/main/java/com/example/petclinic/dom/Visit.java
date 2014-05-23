package com.example.petclinic.dom;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.DatastoreIdentity;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.Queries;
import javax.jdo.annotations.Query;
import javax.jdo.annotations.Version;
import javax.jdo.annotations.VersionStrategy;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import org.apache.isis.applib.annotation.Disabled;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.Immutable;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.MultiLine;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.util.ObjectContracts;

@PersistenceCapable(identityType = IdentityType.DATASTORE)
@DatastoreIdentity(strategy = IdGeneratorStrategy.IDENTITY, column = "id")
@Version(strategy = VersionStrategy.VERSION_NUMBER, column = "version")
@Queries({
        @Query(name = "findByPet", language = "JDOQL",
                value = "SELECT FROM com.example.petclinic.dom.Visit "
                        + "WHERE pet == :pet")
})
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

    private String note;

    @MemberOrder(sequence = "4")
    @MultiLine(numberOfLines = 5)
    @Column(allowsNull = "true")
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    // //////////////////////////////////////

    @Hidden
    public Visit checkIn(final @Named("Note") String note) {
        setCheckInTime(DateTime.now());
        appendNote(": *Check-in*", note);
        return this;
    }

    public Visit checkOut(final @Named("Note") String note) {
        setCheckOutTime(DateTime.now());
        appendNote(": *Check-out*", note);
        return this;
    }

    public String disableCheckOut(String diagnose) {
        return getCheckOutTime() != null ? "Already checked out" : null;
    }

    // //////////////////////////////////////

    public Visit logEvent(@Named("Note") String note) {
        appendNote(": *Log*", note);
        return this;
    }

    private void appendNote(String event, String note) {
        final String currentNote = getNote() == null ? "" : getNote();
        setNote(currentNote
                .concat(LocalDate.now().toString()
                        .concat(event)
                        .concat("\n")
                        .concat(note)
                        .concat("\n")));
    }

    // //////////////////////////////////////

    @Override
    public int compareTo(Visit o) {
        return ObjectContracts.compare(this, o, "pet,checkInTime");
    }

}
