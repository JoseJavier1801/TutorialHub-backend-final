package com.josejavier.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;


import java.time.LocalDate;
import java.util.List;

@JsonIgnoreProperties({"classrooms"}) // Ignora la propiedad "classrooms" durante la serialización
@Entity
@Table(name = "teacher")
public class Teacher extends Client {

    @Column(name = "title")
    private String title;
    @Column(name = "biography")
    private String biography;

    @OneToMany(mappedBy = "teacher",fetch = FetchType.LAZY)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    private List<Classroom> classrooms;

    @OneToMany(mappedBy = "teacher" ,fetch = FetchType.LAZY)
    private List<Assessment> assessments;


    public Teacher() {
    }

    public Teacher(int id, byte[] photo, String name, String username, String mail, String password, LocalDate date, String phone, String title, String biography) {
        super(id, photo, name, username, mail, password, date, phone);
        this.title = title;
        this.biography = biography;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "title='" + title + '\'' +
                ", biography='" + biography + '\'' +
                ", classrooms=" + classrooms +
                ", assessments=" + assessments +
                '}';
    }
}
