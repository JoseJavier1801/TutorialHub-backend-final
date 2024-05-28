package com.josejavier.model;

import com.josejavier.DTO.PetitionDTO;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "petition")
public class Petition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "message", length = 256, nullable = false)
    private String message;

    @Column(name = "state")
    private String state;

    @Column(name = "Date", columnDefinition = "DATE")
    private LocalDate Date;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private Client client;

    @ManyToOne
    @JoinColumn(name = "id_classroom", nullable = false)
    private Classroom classroom;

    // Constructor, getters y setters

    public Petition() {

    }

    public PetitionDTO toDTO(){
        PetitionDTO dto = new PetitionDTO();
        dto.setId(this.id);
        dto.setMessage(this.message);
        dto.setState(this.state);
        dto.setDate(this.Date);
        dto.setClientId(this.client.getId());
        dto.setClassroomId(this.classroom.getId());
        return dto;
    }

    public Petition(PetitionDTO dto){
        this.id = dto.getId();
        this.message = dto.getMessage();
        this.state = dto.getState();
        this.Date = dto.getDate();
        this.client = new Client();
        this.client.setId(dto.getClientId());
        this.classroom = new Classroom();
        this.classroom.setId(dto.getClassroomId());
    }

    public Petition(String message, String state, LocalDate date, Client client, Classroom classroom) {
        this.id = -1;
        this.message = message;
        this.state = state;
        this.Date = date;
        this.client = client;
        this.classroom = classroom;
    }
    // Getters y setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public LocalDate getDate() {
        return Date;
    }

    public void setDate(LocalDate date) {
        this.Date = date;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }
}
