package com.example.silverbox.chatapplication.Beans;

import java.net.URI;

public class PersonBean {
   private String id, name, email, password, contact;
   private String DOB, gender;
   private URI profilepic;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getDOB() { return DOB; }

    public void setDOB(String DOB) { this.DOB = DOB; }

    public String getGender() { return gender; }

    public void setGender(String gender) { this.gender = gender; }

    public URI getProfilepic() { return profilepic; }

    public void setProfilepic(URI profilepic) { this.profilepic = profilepic; }
}
