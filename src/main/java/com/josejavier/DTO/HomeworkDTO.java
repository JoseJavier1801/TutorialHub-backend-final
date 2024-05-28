package com.josejavier.DTO;

import com.josejavier.model.Client;
import com.josejavier.model.Teacher;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class HomeworkDTO {

    private int id;
    private int clientId;
    private int teacherId;
    private byte[] archive;
    private Date datetime;
    private String description;
    private Teacher teacher;
    private Client client;

}