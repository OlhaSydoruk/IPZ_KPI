package com.example.authorarticlesproject.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "authors")

public class Author {
    public Author() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int age;
    private String gender;
    private Long articles_id;

    // Store multiple photo paths for file system storage
    @ElementCollection
    @CollectionTable(name = "author_photo_paths", joinColumns = @JoinColumn(name = "author_id"))
    @Column(name = "photo_path")
    private List<String> photoPaths = new ArrayList<>();

    // Store multiple binary photos for database storage
    @ElementCollection
    @CollectionTable(name = "author_photos", joinColumns = @JoinColumn(name = "author_id"))
    @Column(name = "photo", columnDefinition = "LONGBLOB")
    private List<byte[]> photos = new ArrayList<>();



    public Author(Long id, String name, int age, String gender, Long articles_id, List<String> photoPaths, List<byte[]> photos) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.articles_id = articles_id;
        this.photoPaths = photoPaths;
        this.photos = photos;
    }

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

    public List<String> getPhotoPaths() {
        return photoPaths;
    }

    public void setPhotoPaths(List<String> photoPaths) {
        this.photoPaths = photoPaths;
    }

    public List<byte[]> getPhotos() {
        return photos;
    }

    public void setPhotos(List<byte[]> photos) {
        this.photos = photos;
    }

}
