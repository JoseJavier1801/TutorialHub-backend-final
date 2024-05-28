package com.josejavier.Controller;

import com.josejavier.model.Petition;
import com.josejavier.DTO.PetitionDTO;
import com.josejavier.service.PetitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/petitions")
public class PetitionController {

    @Autowired
    private  PetitionService petitionService;

    /**
     *  Función para crear o actualizar una petición en la base de datos
     * @param petitionDTO
     * @return Petition
     */
    @PostMapping
    public ResponseEntity<PetitionDTO> createPetition(@RequestBody PetitionDTO petitionDTO) {
        try {
            Petition petitionToCreate = new Petition(petitionDTO);
            Petition createdPetition = petitionService.createOrUpdatePetition(petitionToCreate);
            petitionDTO.setId(createdPetition.getId());
            return ResponseEntity.ok(petitionDTO);
        } catch (RuntimeException e) {
            // Manejar la excepción apropiadamente, por ejemplo, registrarla
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     *  Función para actualizar una petición existente en la base de datos
     * @param id
     * @param petitionDTO
     * @return Petition
     */
    @PutMapping("/{id}")
    public ResponseEntity<PetitionDTO> updatePetition(@PathVariable("id") int id, @RequestBody PetitionDTO petitionDTO) {
        try {
            // Verificar si la petición con la ID dada existe
            Petition existingPetition = petitionService.getPetitionById(id);

            if (existingPetition == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            //Actualiza todos los campos de la petiicon existente con los valores proporcionados en el DTO
            existingPetition.setMessage(petitionDTO.getMessage());
            existingPetition.setState(petitionDTO.getState());
            existingPetition.setDate(petitionDTO.getDate());


            // Guardar la actualización en el servicio
            Petition updatedPetition = petitionService.createOrUpdatePetition(existingPetition);

            // Crear y devolver el DTO actualizado
            PetitionDTO updatedPetitionDTO = new PetitionDTO();
            updatedPetitionDTO.setId(updatedPetition.getId());
            updatedPetitionDTO.setMessage(updatedPetition.getMessage());
            updatedPetitionDTO.setState(updatedPetition.getState());
            updatedPetitionDTO.setDate(updatedPetition.getDate());

            return ResponseEntity.ok(updatedPetitionDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }   catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }

    /**
     *  Función para eliminar una petición existente en la base de datos a través de una respuesta HTTP
     * @param id
     * @return ResponseEntity
     */

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePetition(@PathVariable("id") int id) {
        petitionService.deletePetition(id);
        return ResponseEntity.ok("Petition deleted successfully");
    }

    /**
     *  Función para obtener todas las peticiones existentes en la base de datos a través de una respuesta HTTP
     * @return List<Petition>
     */

    @GetMapping
    public ResponseEntity<List<PetitionDTO>> getAllPetitions() {
        List<Petition> petitions = petitionService.getAllPetitions();

        // Convertir la lista de entidades Classroom a una lista de DTOs
        List<PetitionDTO> petitionDTOs = petitions.stream()
                .map(Petition::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(petitionDTOs);
    }

    /**
     *  Función para obtener una petición existente en la base de datos a través de una respuesta HTTP
     * @param clientId
     * @return List<Petition>
     */

    @GetMapping("/{clientId}")
    public ResponseEntity <List<Petition>> getPetitionsByClientId(@PathVariable("clientId") int clientId, @RequestParam("state") String state) {
        List<Petition> petitions = petitionService.getPetitionsByClientIdAndState(clientId, state);
        if (petitions.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(petitions);
    }
    /**
     *  Función para obtener una petición existente en la base de datos a través de una respuesta HTTP
     * @param teacherId
     * @return
     */
    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<List<Petition>> getPetitionsByTeacherAndState(@PathVariable("teacherId") int teacherId, @RequestParam("state") String state) {
        List<Petition> petitions = petitionService.getMyPetitionTeacher(teacherId, state);
        if (petitions.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(petitions);
    }

    /**
     *  Función para actualizar el estado y el mensaje de una petición existente en la base de datos a través de una respuesta HTTP
     * @param petitionId
     * @param newState
     * @param newMessage
     */
    @PutMapping("/petitions/{id}")
    public void updatePetitionStateAndMessage(@PathVariable("id") Integer petitionId,
                                              @RequestParam("state") String newState,
                                              @RequestParam("message") String newMessage) {
        petitionService.updatePetitionStateAndMessage(petitionId, newState, newMessage);
    }




}