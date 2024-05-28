package com.josejavier.repository;

import com.josejavier.model.Petition;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PetitionRepository extends JpaRepository<Petition, Integer> {
    /**
     *  funcion para poder modifcar la peticion por su ID
     * @param petitionId
     * @param newState
     * @param newMessage
     */
    @Modifying
    @Transactional
    @Query("UPDATE Petition p SET p.state = :newState, p.message = :newMessage WHERE p.id = :petitionId")
    void updateStateAndMessageById(Integer petitionId, String newState, String newMessage);
    /**
     *  funcion par optener las peticiones de un profesor por su ID
     * @param teacherId
     * @return
     */
    @Query(value = "SELECT p.id, p.message, p.state, p.Date, c.name AS client_name, c.photo AS client_photo, c.mail AS client_email " +
            "FROM petition p " +
            "JOIN classroom cl ON p.id_classroom = cl.id " +
            "JOIN client c ON p.id_user = c.id " +
            "WHERE cl.id_teacher = ?1"+" AND p.state IN ('Pendiente')", nativeQuery = true)
    List<Object[]> getMyPetitionTeacher(int teacherId);
    @Query(value = "SELECT p.id, p.message, p.state, p.Date, c.name AS client_name, c.photo AS client_photo, c.mail AS client_email " +
            "FROM petition p " +
            "JOIN classroom cl ON p.id_classroom = cl.id " +
            "JOIN client c ON p.id_user = c.id " +
            "WHERE cl.id_teacher = ?1"+" AND p.state IN ( 'Aceptada')", nativeQuery = true)
    List<Object[]> getMyPetitionTeacherAcep(int teacherId);
    @Query(value = "SELECT p.id, p.message, p.state, p.Date, c.name AS client_name, c.photo AS client_photo, c.mail AS client_email " +
            "FROM petition p " +
            "JOIN classroom cl ON p.id_classroom = cl.id " +
            "JOIN client c ON p.id_user = c.id " +
            "WHERE cl.id_teacher = ?1"+" AND p.state IN ('Denegada')", nativeQuery = true)
    List<Object[]> getMyPetitionTeacherDene(int teacherId);



    /**
     *  funcion par optener las peticiones de un cliente por su ID y el estado

     * @return List<Object[]>
     */
    @Query(value = "SELECT p.id, p.message, p.state, p.Date, " +
            "c_teacher.name AS teacher_name, c_teacher.photo AS teacher_photo, " +
            "c_teacher.mail AS teacher_email, c_teacher.phone AS teacher_phone " +
            "FROM petition p " +
            "JOIN classroom cl ON p.id_classroom = cl.id " +
            "JOIN client c ON p.id_user = c.id " +
            "JOIN client c_teacher ON cl.id_teacher = c_teacher.id " +
            "WHERE c.id = ?1 AND p.state IN ('Aceptada')", nativeQuery = true)
    List<Object[]> getPetitionsByClientIdAcep(int clientId);

    @Query(value = "SELECT p.id, p.message, p.state, p.Date, " +
            "c_teacher.name AS teacher_name, c_teacher.photo AS teacher_photo, " +
            "c_teacher.mail AS teacher_email, c_teacher.phone AS teacher_phone " +
            "FROM petition p " +
            "JOIN classroom cl ON p.id_classroom = cl.id " +
            "JOIN client c ON p.id_user = c.id " +
            "JOIN client c_teacher ON cl.id_teacher = c_teacher.id " +
            "WHERE c.id = ?1 AND p.state IN ('Denegada')", nativeQuery = true)
    List<Object[]> getPetitionsByClientIdDene(int clientId);

    @Query(value = "SELECT p.id, p.message, p.state, p.Date, " +
            "c_teacher.name AS teacher_name, c_teacher.photo AS teacher_photo, " +
            "c_teacher.mail AS teacher_email, c_teacher.phone AS teacher_phone " +
            "FROM petition p " +
            "JOIN classroom cl ON p.id_classroom = cl.id " +
            "JOIN client c ON p.id_user = c.id " +
            "JOIN client c_teacher ON cl.id_teacher = c_teacher.id " +
            "WHERE c.id = ?1 AND p.state IN ('Pendiente')", nativeQuery = true)
    List<Object[]> getPetitionsByClientIdPen(int clientId);


}
