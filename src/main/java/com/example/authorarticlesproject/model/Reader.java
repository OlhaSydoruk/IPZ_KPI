package com.example.authorarticlesproject.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "readers")
public class Reader {

    public Reader(Long id, String name, String surname, List<String> notifications) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.notifications = notifications;
    }

    public Reader() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    @ElementCollection
    @CollectionTable(name = "reader_notifications", joinColumns = @JoinColumn(name = "reader_id"))
    @Column(name = "notification")
    private List<String> notifications;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public List<String> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<String> notifications) {
        this.notifications = notifications;
    }
}
