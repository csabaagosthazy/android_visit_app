package com.example.visitapp.database.entity;

import androidx.annotation.NonNull;
import com.google.firebase.database.Exclude;
import java.util.HashMap;
import java.util.Map;


public class VisitorEntity implements Comparable{

    private String visitorId;
    private String firstName;
    private String lastName;
    private String email;

    public VisitorEntity(){
    }

    public VisitorEntity(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    @Exclude
    public String getVisitorId() {
        return visitorId;
    }

    public void setVisitorId(String id) {
        this.visitorId = id;
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


    @Override
    public boolean equals(Object obj) {
        if (obj == null ) return false;
        if (obj == this) return true;
        if (!(obj instanceof VisitorEntity)) return false;
        VisitorEntity that = (VisitorEntity) obj;
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

        return result;
    }
}
