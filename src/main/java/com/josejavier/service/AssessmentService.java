package com.josejavier.service;

import com.josejavier.model.Assessment;
import com.josejavier.model.Client;
import com.josejavier.model.Teacher;
import com.josejavier.repository.AssessmentRepository;
import com.josejavier.repository.ClientRepository;
import com.josejavier.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AssessmentService {
    @Autowired
    private AssessmentRepository assessmentRepository;

    @Autowired
    private TeacherService teacherService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private TeacherRepository teacherRepository;

    /**
     *  Función para crear o actualizar una valoración en la base de datos
     * @param assessment
     * @return Assessment
     */

    public Assessment createOrUpdateAssement(Assessment assessment) {
        Assessment newAssessment = null;
        try{
            //verifica si el cliente y el profesor existen antes de guardar la valoracion
            Integer teacherId = assessment.getTeacher() != null ? assessment.getTeacher().getId() : null;
            Integer clientId = assessment.getClient() != null ? assessment.getClient().getId() : null;

            if(teacherId == null || teacherService.getTeacherById(teacherId) == null){
                //el profesor no existe, puedes manejarlo por tus necesidades
                throw new RuntimeException("Teacher does not exist");
            }

            if(clientId == null || clientService.getClientById(clientId) == null){
                //el cliente no existe, puedes manejarlo por tus necesidades
                throw new RuntimeException("Client does not exist");
            }

            newAssessment = assessmentRepository.save(assessment);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("Error creating/updating assessment", e);
        }
        return newAssessment;
    }

    /**
     *  Función para obtener todas las valoraciones de la base de datos
     * @return List<Assessment>
     */

    public List<Assessment> getAllAssessments() {
        try{
            List<Assessment> assessments = assessmentRepository.findAll();
            return assessments;
        }catch (Exception e){
            throw new RuntimeException("Error retrieving assessments", e);
        }
    }

    /**
     *  Función para borrar una valoración por su ID en la base de datos
     * @param id
     */

    public void deleteAssessment(int id) {
        try{
            assessmentRepository.deleteById(id);
        }catch (Exception e){
            throw new RuntimeException("Error deleting assessment", e);
        }
    }

    /**
     *  Función para obtener una valoración por su ID en la base de datos
     * @param id
     * @return Assessment
     */

    public Assessment getAssessmentById(int id) {
        try{
            Optional<Assessment> optionalAssessment = assessmentRepository.findById(id);
            return optionalAssessment.orElse(null);
        }catch (Exception e){
            throw new RuntimeException("Error retrieving assessment by ID", e);
        }
    }

    /**
     *  Función para obtener las valoraciones de un cliente por su ID en la base de datos
     * @param teacherId
     * @return List<Assessment>
     */

    public List<Assessment> getAssessmentsByTeacherId(int teacherId) {

        return assessmentRepository.findByTeacherId(teacherId);
    }
    /**
     * Función para obtener la media del estado de las calificaciones de un profesor por su ID en la base de datos.
     *
     * @param teacherId El ID del profesor.
     * @return La media del estado de las calificaciones.
     */
    public Double getAverageAssessmentByTeacherId(int teacherId) {
        try {
            return assessmentRepository.findAverageAssessmentByTeacherId(teacherId).orElse(null);
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving average assessment by teacher ID", e);
        }
    }

}