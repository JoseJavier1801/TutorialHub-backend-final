package com.josejavier.Controller;

import com.josejavier.DTO.AssessmentDTO;
import com.josejavier.model.Assessment;
import com.josejavier.service.AssessmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/assessments") // Cambiado a "/assessments" para que coincida con la configuración del controlador
public class AssessmentController {

    @Autowired
    private  AssessmentService assessmentService;

    /**
     * envía una lista de todas las evaluaciones existentes en el sistema a través de una respuesta HTTP
     * @return List<AssessmentDTO>
     */
    @GetMapping
    public ResponseEntity<List<AssessmentDTO>> getAllAssessments() {
        List<Assessment> assessments = assessmentService.getAllAssessments();
        List<AssessmentDTO> assessmentDTOs = assessments.stream()
                .map(Assessment::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(assessmentDTOs);
    }

    /**
     *  envía una lista de evaluaciones de un profesor a través de una respuesta HTTP
     * @param teacherId
     * @return List<AssessmentDTO>
     */

    @GetMapping("/{teacherId}")
    public ResponseEntity<List<AssessmentDTO>> getAssessmentById(@PathVariable("teacherId") Integer teacherId) {

        List<Assessment> assessments = assessmentService.getAssessmentsByTeacherId(teacherId); // Obtener la lista de evaluaciones

        // Imprimir los datos de las Assessment encontradas
        if (assessments != null && !assessments.isEmpty()) {
            List<AssessmentDTO> assessmentDTOs = new ArrayList<>();
            for (Assessment assessment : assessments) {
                System.out.println("Assessment encontrada: " + assessment.toString());
                AssessmentDTO assessmentDTO = assessment.toDTO();
                assessmentDTOs.add(assessmentDTO);
            }
            return ResponseEntity.ok(assessmentDTOs);
        } else {
            // Manejo si no se encuentran evaluaciones o si la lista está vacía
            return ResponseEntity.notFound().build();
        }
    }


    /**
     *  envía una evaluación a través de una respuesta HTTP
     * @param assessmentDTO
     * @return AssessmentDTO
     */
    @PostMapping
    public ResponseEntity<Assessment> createAssessment(@RequestBody AssessmentDTO assessmentDTO) {
        try{
            Assessment assessment = new Assessment(assessmentDTO);
            Assessment createdAssessment = assessmentService.createOrUpdateAssement(assessment);
            return ResponseEntity.ok(createdAssessment);
        }catch (RuntimeException e){
            // Manejar la excepción apropiadamente, por ejemplo, registrarla
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }

    /**
     *  Actualiza una evaluación existente en el sistema a través de una respuesta HTTP
     * @param teacherId
     * @param assessmentDTO
     * @return AssessmentDTO
     */

    @PutMapping("/{teacherId}")
    public ResponseEntity<AssessmentDTO> updateAssessment(@PathVariable(name = "teacherId") Integer teacherId, @RequestBody AssessmentDTO assessmentDTO) {
        try{
            //verifica si la valoracion con la id dada existe
            Assessment existingAssessment = assessmentService.getAssessmentById(teacherId);
            if(existingAssessment == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            //Actualiza todos los campos de la valoracion existentes con los valores proporcionados en el DTO
            existingAssessment.setAssessment(assessmentDTO.getAssessment());
            existingAssessment.setComment(assessmentDTO.getComment());

            //Guarda la actualizacion en el servicio
            Assessment updatedAssessment = assessmentService.createOrUpdateAssement(existingAssessment);

            //Crea y devuelve el DTO actualizado
            AssessmentDTO updatedAssessmentDTO = new AssessmentDTO();
            updatedAssessmentDTO.setId(updatedAssessment.getId());
            updatedAssessmentDTO.setAssessment(updatedAssessment.getAssessment());
            updatedAssessmentDTO.setComment(updatedAssessment.getComment());
            return ResponseEntity.ok(updatedAssessmentDTO);
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }

    /**
     *  Elimina una evaluación existente en el sistema a través de una respuesta HTTP
     * @param id
     * @return ResponseEntity<Void>
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssessment(@PathVariable(name = "id") Integer id) {
        assessmentService.deleteAssessment(id);
        return ResponseEntity.noContent().build();
    }
    /**
     * Obtiene el promedio de las evaluaciones por ID del profesor a través de una respuesta HTTP
     * @param teacherId ID del profesor
     * @return ResponseEntity<Double>
     */
    @GetMapping("/average/{teacherId}")
    public ResponseEntity<Double> getAverageAssessmentByTeacherId(@PathVariable("teacherId") int teacherId) {
        System.out.println("teacherId: " + teacherId);
        try {
            Double averageAssessment = assessmentService.getAverageAssessmentByTeacherId(teacherId);
            if (averageAssessment != null) {
                return ResponseEntity.ok(averageAssessment);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (RuntimeException e) {
            throw new RuntimeException("Error retrieving average assessment by teacher ID", e);
        }
    }




}