package com.josejavier.DTO;

import java.time.LocalDate;

public class PetitionDTO {
    private Integer id;
    private String message;
    private String state;
    private LocalDate date;
    private Integer clientId;  // Representa el id del cliente asociado a la petición
    private Integer classroomId;  // Representa el id del aula asociada a la petición

    // Constructores, getters y setters

    public PetitionDTO() {

    }

    public PetitionDTO(Integer id, String message, String state, LocalDate date, Integer clientId, Integer classroomId) {
        this.id = id;
        this.message = message;
        this.state = state;
        this.date = date;
        this.clientId = clientId;
        this.classroomId = classroomId;
    }

    // Getters y setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Integer getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(Integer classroomId) {
        this.classroomId = classroomId;
    }
}
