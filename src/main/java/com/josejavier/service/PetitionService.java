package com.josejavier.service;

import com.josejavier.model.Petition;
import com.josejavier.model.Client;
import com.josejavier.model.Classroom;
import com.josejavier.model.Teacher;
import com.josejavier.repository.PetitionRepository;
import com.josejavier.repository.ClientRepository;
import com.josejavier.repository.ClassroomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PetitionService {

    @Autowired
    private  PetitionRepository petitionRepository;
    @Autowired
    private  ClientRepository clientRepository;
    @Autowired
    private ClassroomRepository classroomRepository;

    /**
     *  Función para crear o actualizar una petición en la base de datos
     * @param petition
     * @return Petition
     */
    public Petition createOrUpdatePetition(Petition petition) {
        Petition newPetition = null;
        try {
            // Verificar si el cliente y el aula existen antes de guardar la petición
            Integer clientId = petition.getClient() != null ? petition.getClient().getId() : null;
            Integer classroomId = petition.getClassroom() != null ? petition.getClassroom().getId() : null;

            if (clientId == null || getClientById(clientId) == null) {
                // El cliente no existe, puedes manejarlo según tus necesidades
                throw new RuntimeException("Client does not exist");
            }

            if (classroomId == null || getClassroomById(classroomId) == null) {
                // El aula no existe, puedes manejarlo según tus necesidades
                throw new RuntimeException("Classroom does not exist");
            }

            newPetition = petitionRepository.save(petition);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error creating/updating petition", e);
        }
        return newPetition;
    }

    /**
     *  Función para borrar una petición de la base de datos
     * @param id
     */
    public void deletePetition(Integer id) {
        try {
            petitionRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting petition", e);
        }
    }

    /**
     *  Función para obtener todas las peticiónes de la base de datos
     * @return List<Petition>
     */
    public List<Petition> getAllPetitions() {
        try {
            List<Petition> petitions = petitionRepository.findAll();
            return petitions;
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving petitions", e);
        }
    }

    /**
     *  Función para obtener una petición por su ID de la base de datos
     * @param id
     * @return Petition
     */

    public Petition getPetitionById(Integer id) {
        try {
            Optional<Petition> optionalPetition = petitionRepository.findById(id);
            return optionalPetition.orElse(null);
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving petition by ID", e);
        }
    }

    /**
     *  Función para obtener un cliente por su ID de la base de datos
     * @param clientId
     * @return Client
     */
    public Client getClientById(Integer clientId) {
        return clientRepository.findById(clientId).orElse(null);
    }

    /**
     *  Función para obtener un aula por su ID de la base de datos
     * @param classroomId
     * @return Classroom
     */

    public Classroom getClassroomById(Integer classroomId) {
        return classroomRepository.findById(classroomId).orElse(null);
    }
    public List<Petition> getMyPetitionTeacher(int teacherId, String state) {
        List<Object[]> results;
        List<Petition> petitions = new ArrayList<>();

        if (state.equals("Pendiente")) {
            results = petitionRepository.getMyPetitionTeacher(teacherId);
        } else if (state.equals("Aceptada")) {
            results = petitionRepository.getMyPetitionTeacherAcep(teacherId);
        } else if (state.equals("Denegada")) {
            results = petitionRepository.getMyPetitionTeacherDene(teacherId);
        } else {
            // Manejar el caso en el que el estado no coincida con ninguno de los valores esperados
            // Por ejemplo, lanzar una excepción o establecer un valor predeterminado
            return petitions; // Retorna una lista vacía
        }

        for (Object[] result : results) {
            Petition petition = new Petition();
            petition.setId((Integer) result[0]);
            petition.setMessage((String) result[1]);
            petition.setState((String) result[2]);

            Date dateSql = (Date) result[3];
            if (dateSql != null) {
                petition.setDate(dateSql.toLocalDate());
            } else {
                petition.setDate(LocalDate.now());
            }

            Client client = new Client();
            client.setName((String) result[4]);
            client.setPhoto((byte[]) result[5]);
            client.setMail((String) result[6]);

            petition.setClient(client);
            petitions.add(petition);
        }
        System.out.println(petitions);
        return petitions;
    }

    /**
     *  Función para poder modifcar la peticion por su ID
     * @param petitionId
     * @param newState
     * @param newMessage
     */
    public void updatePetitionStateAndMessage(Integer petitionId, String newState, String newMessage) {
        petitionRepository.updateStateAndMessageById(petitionId, newState, newMessage);
    }

    /**
     *  Función par optener las peticiones de un cliente por su ID y el estado
     * @param clientId
     * @return List<Object[]>
     */
    public List<Petition> getPetitionsByClientIdAndState(int clientId, String state) {
        List<Object[]> results;
        List<Petition> petitions = new ArrayList<>();

        if (state.equals("Aceptada")) {
            results = petitionRepository.getPetitionsByClientIdAcep(clientId);
            System.out.println("he llegado");
        } else if (state.equals("Denegada")) {
            results = petitionRepository.getPetitionsByClientIdDene(clientId);
            System.out.println("he llegado");
        } else if (state.equals("Pendiente")) {
            results = petitionRepository.getPetitionsByClientIdPen(clientId);
            System.out.println("he llegado");
        } else {
            // Manejar el caso en el que el estado no coincida con ninguno de los valores esperados
            // Por ejemplo, lanzar una excepción o establecer un valor predeterminado
            return petitions; // Retorna una lista vacía
        }

        for (Object[] result : results) {
            Petition petition = new Petition();
            petition.setId((Integer) result[0]);
            petition.setMessage((String) result[1]);
            petition.setState((String) result[2]);

            Date dateSql = (Date) result[3];
            if (dateSql != null) {
                petition.setDate(dateSql.toLocalDate());
            } else {
                petition.setDate(LocalDate.now());
            }

            Client client = new Client();
            client.setName((String) result[4]);
            client.setPhoto((byte[]) result[5]);
            client.setMail((String) result[6]);

            petition.setClient(client);
            petitions.add(petition);
        }

        System.out.println(petitions);
        return petitions;
    }


}