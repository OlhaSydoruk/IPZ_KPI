package com.example.model;

import com.example.model.converter.ListToStringConverter;
import jakarta.persistence.*;

import java.util.List;


@Entity

public class Author {

    public Author(Long id, String name, int age, String gender, List<String> notifications, List<Article> articles, byte[] photo) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.notifications = notifications;
        this.articles = articles;
        this.photo = photo;
    }

    public Author() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int age;
    private String gender;

    @Convert(converter = ListToStringConverter.class)
    private List<String> notifications;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Article> articles;


    @Lob
    private byte[] photo;

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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public List<String> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<String> notifications) {
        this.notifications = notifications;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }
}


