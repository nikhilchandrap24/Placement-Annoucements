package com.example.placementannouncements;

public class Users {
    String email, fullName, rollNumber, branch;
    public Users(String email, String fullName){
        this.email = email;
        this.fullName = fullName;
    }

    public Users(String email, String fullName, String rollNumber, String branch) {
        this.email = email;
        this.fullName = fullName;
        this.rollNumber = rollNumber;
        this.branch = branch;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }
}
