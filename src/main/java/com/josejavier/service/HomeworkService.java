package com.josejavier.service;

import com.josejavier.DTO.HomeworkDTO;
import com.josejavier.model.Client;
import com.josejavier.model.Homework;
import com.josejavier.model.Teacher;
import com.josejavier.repository.ClientRepository;
import com.josejavier.repository.HomerworkRepository;
import com.josejavier.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HomeworkService {

    @Autowired
    private HomerworkRepository homeworkRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    public HomeworkDTO createHomework(HomeworkDTO dto) {
        Client client = clientRepository.findById(dto.getClientId())
                .orElseThrow(() -> new RuntimeException("Client not found"));
        Teacher teacher = teacherRepository.findById(dto.getTeacherId())
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        Homework homework = new Homework(dto, client, teacher);
        Homework savedHomework = homeworkRepository.save(homework);
        return savedHomework.toDTO();
    }

    public HomeworkDTO getHomeworkById(int id) {
        Homework homework = homeworkRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Homework not found"));
        return homework.toDTO();
    }

    public List<HomeworkDTO> getAllHomeworks() {
        return homeworkRepository.findAll().stream()
                .map(Homework::toDTO)
                .collect(Collectors.toList());
    }

    public HomeworkDTO updateHomework(int id, HomeworkDTO dto) {
        Homework existingHomework = homeworkRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Homework not found"));

        Client client = clientRepository.findById(dto.getClientId())
                .orElseThrow(() -> new RuntimeException("Client not found"));
        Teacher teacher = teacherRepository.findById(dto.getTeacherId())
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        existingHomework.setClient(client);
        existingHomework.setTeacher(teacher);
        existingHomework.setArchive(dto.getArchive());
        existingHomework.setDatetime(dto.getDatetime());
        existingHomework.setDescription(dto.getDescription());

        Homework updatedHomework = homeworkRepository.save(existingHomework);
        return updatedHomework.toDTO();
    }

    public void deleteHomework(int id) {
        if (!homeworkRepository.existsById(id)) {
            throw new RuntimeException("Homework not found");
        }
        homeworkRepository.deleteById(id);
    }
}