package com.josejavier.model;

import com.josejavier.DTO.HomeworkDTO;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Blob;
import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "homework")
public class Homework {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;

    @Lob
    @Column(name = "archive")
    private byte[] archive;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "datetime")
    private Date datetime;

    @Column(name = "description")
    private String description;

    public Homework(HomeworkDTO dto){
        this.id = dto.getId();
        this.client = dto.getClient();
        this.teacher = dto.getTeacher();
        this.archive = dto.getArchive();
        this.datetime = dto.getDatetime();
        this.description = dto.getDescription();
    }

    public Homework(HomeworkDTO dto, Client client, Teacher teacher) {
        this.id = dto.getId();
        this.client = client;
        this.teacher = teacher;
        this.archive = dto.getArchive();
        this.datetime = dto.getDatetime();
        this.description = dto.getDescription();
    }

    public HomeworkDTO toDTO(){
        HomeworkDTO dto = new HomeworkDTO();
        dto.setId(this.id);
        dto.setClientId(this.client.getId());
        dto.setTeacherId(this.teacher.getId());
        dto.setArchive(this.archive);
        dto.setDatetime(this.datetime);
        dto.setDescription(this.description);
        return dto;
    }
}
