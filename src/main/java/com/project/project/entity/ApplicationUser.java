package com.project.project.entity;

import javax.persistence.*;

@Entity
@Table(name = "USER")
public class ApplicationUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "USERNAME", nullable = false, updatable = false)
    private String userName;

    @Column(name = "NAME")
    private String firstName;

    @Column(name = "SURNAME")
    private String lastName;
    @Column(name = "Password")
    private String password;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstname) {
        this.firstName = firstname;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("{ com.project.project.entity.ApplicationUser " + getId())
                .append("}")
                .toString();
    }
}
