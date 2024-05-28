package com.josejavier.DTO;

public class AssessmentDTO {
    private Integer id;
    private String comment;
    private double assessment;
    private Integer teacherId;
    private Integer clientId;

    public AssessmentDTO() {

    }

    public AssessmentDTO(Integer id, String comment, double assessment, Integer teacherId, Integer clientId) {
        this.id = id;
        this.comment = comment;
        this.assessment = assessment;
        this.teacherId = teacherId;
        this.clientId = clientId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public double getAssessment() {
        return assessment;
    }

    public void setAssessment(double assessment) {
        this.assessment = assessment;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }
}
