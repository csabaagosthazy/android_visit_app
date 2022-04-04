package com.example.visitapp.database.entity;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "persons")
public class PersonEntity implements Comparable{
    @PrimaryKey(autoGenerate = true)
    private Long idPerson;

    @ColumnInfo(name = "first_name")
    private  String firstName;

    @ColumnInfo(name = "last_name")
    private String lastName;

    @ColumnInfo(name = "email")
    private String email;

    @ColumnInfo(name = "is_employee")
    private boolean isEmployee;

    @ColumnInfo(name = "password")
    private String password;

    @Ignore
    public PersonEntity(){
    }

    public PersonEntity(String firstName, String lastName, String email, boolean isEmployee, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.isEmployee = isEmployee;
        this.password = password;
    }


    public Long getIdPerson() {
        return idPerson;
    }

    public void setIdPerson(Long idPerson) {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null ) return false;
        if (obj == this) return true;
        if (!(obj instanceof PersonEntity)) return false;
        PersonEntity that = (PersonEntity) obj;
        return that.getEmail().equals(this.getEmail());
    }

    @NonNull
    @Override
    public String toString() {
        return firstName + " "+ lastName;
    }

    @Override
    public int compareTo(@NonNull Object o) {
        return toString().compareTo(o.toString());
    }
}