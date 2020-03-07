package com.project.project.entity;

import javax.persistence.*;

@Entity
@Table(name = "USER")
public class Email {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "EMAIL", unique = true)
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("{ com.project.project.entity.EMAIL " + getId())
                .append("}")
                .toString();
    }
}
