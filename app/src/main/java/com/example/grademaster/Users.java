package com.example.grademaster;

import org.mindrot.jbcrypt.BCrypt;

public class Users {
    private String fullName;
    private String email;
    private boolean isEmailVerified;
    private String passwordHash;

    // Default constructor required for calls to DataSnapshot.getValue(User.class)
    public Users() {
    }

    // Parameterized constructor
    public Users(String fullName, String email, boolean isEmailVerified, String password) {
        this.fullName = fullName;
        this.email = email;
        this.isEmailVerified = isEmailVerified;
        this.passwordHash = hashPassword(password);
    }

    // Method to hash the password
    private String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    // Method to verify password
    public boolean checkPassword(String plainPassword) {
        return BCrypt.checkpw(plainPassword, this.passwordHash);
    }

    // Getters and Setters
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean getIsEmailVerified() {
        return isEmailVerified;
    }

    public void setIsEmailVerified(boolean isEmailVerified) {
        this.isEmailVerified = isEmailVerified;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPassword(String password) {
        this.passwordHash = hashPassword(password);
    }

    @Override
    public String toString() {
        return "User{" +
                "fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", isEmailVerified=" + isEmailVerified +
                ", password='" + passwordHash + '\'' +
                '}';
    }
}


