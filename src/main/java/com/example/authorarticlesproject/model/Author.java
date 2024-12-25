package com.example.authorarticlesproject.model;

import jakarta.persistence.*;


@Entity
@Table(name = "authors")

public class Author {

    public Author(Long id, String name, int age, String gender, Long articles_id, byte[] photo) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.articles_id = articles_id;
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
    private Long articles_id;


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

    public Long getArticles_id() {
        return articles_id;
    }

    public void setArticles_id(Long articles_id) {
        this.articles_id = articles_id;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }
}
