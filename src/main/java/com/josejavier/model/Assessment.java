package com.josejavier.model;



import com.josejavier.DTO.AssessmentDTO;
import jakarta.persistence.*;



@Entity
@Table(name = "assessment")
public class Assessment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "Comment")
    private String comment;

    @Column(name = "Assessment")
    private double assessment;

    @ManyToOne
    @JoinColumn(name = "Teacher_ID", nullable = false)
    private Teacher teacher;

    @ManyToOne
    @JoinColumn(name = "User_ID", nullable = false)
    private Client client;

    public Assessment() {
    }

    public AssessmentDTO toDTO() {
        AssessmentDTO dto = new AssessmentDTO();
        dto.setId(this.id);
        dto.setComment(this.comment);
        dto.setAssessment(this.assessment);
        dto.setTeacherId(this.teacher.getId());
        dto.setClientId(this.client.getId());
        return dto;
    }


    public Assessment(AssessmentDTO dto){
        this.id = dto.getId();
        this.comment = dto.getComment();
        this.assessment = dto.getAssessment();
        this.teacher = new Teacher();
        this.teacher.setId(dto.getTeacherId());
        this.client = new Client();
        this.client.setId(dto.getClientId());
    }

    public Assessment(String comment, double assessment, Teacher teacher, Client client) {
        this.comment = comment;
        this.assessment = assessment;
        this.teacher = teacher;
        this.client = client;
    }

    public int getId() {
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

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
