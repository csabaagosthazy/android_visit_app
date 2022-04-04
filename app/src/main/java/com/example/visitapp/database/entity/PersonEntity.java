package com.example.visitapp.database.entity;

import androidx.annotation.NonNull;
import com.google.firebase.database.Exclude;
import java.util.HashMap;
import java.util.Map;


public class PersonEntity implements Comparable{

    private String idPerson;
    private String firstName;
    private String lastName;
    private String email;
    private boolean isEmployee;

    public PersonEntity(){
    }

    public PersonEntity(String firstName, String lastName, String email, boolean isEmployee) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.isEmployee = isEmployee;
    }

    @Exclude
    public String getIdPerson() {
        return idPerson;
    }

    public void setIdPerson(String idPerson) {
        this.idPerson = idPerson;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEmployee() {
        return isEmployee;
    }

    public void setEmployee(boolean employee) {
        isEmployee = employee;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null ) return false;
        if (obj == this) return true;
        if (!(obj instanceof PersonEntity)) return false;
        PersonEntity that = (PersonEntity) obj;
        return that.getEmail().equals(this.getEmail());
    }

    @Override
    public String toString() {
        return firstName + " "+ lastName;
    }

    @Override
    public int compareTo(@NonNull Object o) {
        return toString().compareTo(o.toString());
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("firstName", firstName);
        result.put("lastName", lastName);
        result.put("email", email);
        result.put("isEmployee", isEmployee);

        return result;
    }
}
