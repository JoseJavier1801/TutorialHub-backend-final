package com.josejavier.model;


import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "client")
@Inheritance(strategy = InheritanceType.JOINED)
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "photo", columnDefinition = "bytea") // Cambia el tipo de dato de la columna photo a bytea
    private byte[] photo; // Cambia el tipo de dato de la propiedad photo a byte[]

    @Column(name = "name")
    private String name;

    @Column(name = "username")
    private String username;

    @Column(name = "mail")
    private String mail;

    @Column(name = "password")
    private String password;

    @Column(name = "date",columnDefinition = "DATE")
    private LocalDate date;

    @Column(name = "phone")
    private String phone;

    @OneToMany(mappedBy = "client")
    private List<Assessment> assessments;

    public Client() {

    }

    public Client(int id, byte[] photo, String name, String username, String mail, String password, LocalDate date, String phone) {
        this.id = id;
        this.photo = photo;
        this.name = name;
        this.username = username;
        this.mail = mail;
        this.password = password;
        this.date = date;
        this.phone = phone;

    }

    // Getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
