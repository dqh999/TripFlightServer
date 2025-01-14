package com.airline.booking.domain.account.model;

import com.airline.booking.domain.utils.valueObject.Email;
import com.airline.booking.domain.account.valueObject.HashPassword;
import com.airline.booking.domain.utils.valueObject.PhoneNumber;
import com.airline.booking.domain.account.valueObject.Role;
import com.airline.booking.domain.utils.model.BaseModel;
import com.airline.booking.domain.utils.valueObject.Id;

import java.time.LocalDate;

public class User extends BaseModel {
    private Id id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String gender;
    private Role role;
    private Email email;
    private PhoneNumber phoneNumber;
    private HashPassword password;
    private boolean isAccountLocked;
    private boolean isAccountActive;
    public User() {}
    public User(String id, String firstName, String lastName, LocalDate dateOfBirth, String gender, String role, String email, String phoneNumber, String password, boolean isAccountLocked, boolean isAccountActive) {
        this.id = new Id(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.role = new Role(role);
        this.email = new Email(email);
        this.phoneNumber = new PhoneNumber(phoneNumber);
        this.password = new HashPassword(password);
        this.isAccountLocked = isAccountLocked;
        this.isAccountActive = isAccountActive;
    }

    public void initializeNewId() {
        this.id = new Id();
    }

    public String getId() {
        return id.getValue();
    }

    public void setId(String id) {
        this.id = new Id(id);
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

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRole() {
        return role.getValue();
    }

    public void setRole(String role) {
        this.role = new Role(role);
    }

    public String getEmail() {
        return this.email.getValue();
    }

    public void setEmail(String email) {
        this.email = new Email(email);
    }

    public String getPhoneNumber() {
        return this.phoneNumber.getValue();
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = new PhoneNumber(phoneNumber);
    }

    public String getPassword() {
        return this.password.getValue();
    }

    public void setPassword(String password) {
        this.password = new HashPassword(password);
    }

    public HashPassword getHashedPassword() {
        return this.password;
    }

    public boolean isAccountLocked() {
        return isAccountLocked;
    }

    public void setAccountLocked(boolean accountLocked) {
        isAccountLocked = accountLocked;
    }

    public boolean isAccountActive() {
        return isAccountActive;
    }

    public void setAccountActive(boolean accountActive) {
        isAccountActive = accountActive;
    }
}
