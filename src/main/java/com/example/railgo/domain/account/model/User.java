package com.example.railgo.domain.account.model;

import com.example.railgo.domain.account.valueObject.Email;
import com.example.railgo.domain.account.valueObject.HashPassword;
import com.example.railgo.domain.account.valueObject.PhoneNumber;
import com.example.railgo.domain.account.valueObject.Role;
import com.example.railgo.domain.utils.valueObject.Id;
import java.time.LocalDate;

public class User {
    private Id id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private Role role;
    private Email email;
    private PhoneNumber phoneNumber;
    private HashPassword hashPassword;
    private boolean isAccountLocked;
    private boolean isAccountActive;

    public User(String id, String firstName, String lastName, LocalDate dateOfBirth, String role, String email, String phoneNumber, HashPassword hashPassword, boolean isAccountLocked, boolean isAccountActive) {
        this.id = new Id(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.role = new Role(role);
        this.email = new Email(email);
        this.phoneNumber = new PhoneNumber(phoneNumber);
        this.hashPassword = hashPassword;
        this.isAccountLocked = isAccountLocked;
        this.isAccountActive = isAccountActive;
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

    public String getHashPassword() {
        return this.hashPassword.getHashedPassword();
    }

    public void setHashPassword(String password) {
        this.hashPassword = new HashPassword(password);
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
