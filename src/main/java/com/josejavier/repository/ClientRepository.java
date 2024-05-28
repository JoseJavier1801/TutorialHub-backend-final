package com.josejavier.repository;

import com.josejavier.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
    /**
     *  funcion par optener un cliente por su username o mail
     * @param username
     * @param mail
     * @return Client
     */
    @Query("SELECT c FROM Client c WHERE c.username = :username OR c.mail = :mail")
    Client findByUsernameOrMail(@Param("username") String username, @Param("mail") String mail);


    @Query("SELECT c FROM Client c WHERE c.id = :id")
    Client findbyid(int id);

    /**
     *  funcion par optener un cliente por su username y password
     * @param username
     * @param password
     * @return Client
     */
    @Query("SELECT c FROM Client c WHERE c.username = :username and c.password = :password")
    Client findbyUsernameAndPassword(@Param("username") String username, @Param("password") String password);


////////nuevas funciones /////////////////////////
@Query(value = "SELECT DISTINCT " +
        "    c.name AS client_name, " +
        "    c.photo AS client_photo, " +
        "    c.mail AS client_email, " +
        "    c.phone AS client_phone, " +
        "    c.date AS client_date " +
        "FROM " +
        "    petition p " +
        "JOIN " +
        "    classroom cl ON p.id_classroom = cl.id " +
        "JOIN " +
        "    client c ON p.id_user = c.id " +
        "WHERE " +
        "    cl.id_teacher = ?1 AND p.state IN ('Aceptada')", nativeQuery = true)
List<Object[]> findDistinctClientInfoByTeacherId(Long teacherId);
    @Query(value = "SELECT DISTINCT " +
            "    c.name AS client_name, " +
            "    c.photo AS client_photo, " +
            "    c.mail AS client_email, " +
            "    c.phone AS client_phone, " +
            "    c.date AS client_date " +
            "FROM " +
            "    petition p " +
            "JOIN " +
            "    classroom cl ON p.id_classroom = cl.id " +
            "JOIN " +
            "    client c ON p.id_user = c.id " +
            "WHERE " +
            "    cl.id_teacher = :teacherId AND p.state IN ('Aceptada') " +
            "ORDER BY c.name", nativeQuery = true)
    List<Object[]> findDistinctClientInfoByTeacherIdOrder(@Param("teacherId") Long teacherId);

    @Query(value = "SELECT DISTINCT " +
            "    c.name AS client_name, " +
            "    c.photo AS client_photo, " +
            "    c.mail AS client_email, " +
            "    c.phone AS client_phone, " +
            "    c.date AS client_date " +
            "FROM " +
            "    petition p " +
            "JOIN " +
            "    classroom cl ON p.id_classroom = cl.id " +
            "JOIN " +
            "    client c ON p.id_user = c.id " +
            "WHERE " +
            "    cl.id_teacher = :teacherId AND p.state IN ('Aceptada') AND cl.id = :classId", nativeQuery = true)
    List<Object[]> findDistinctClientInfoByTeacherIdAndClassId(@Param("teacherId") Long teacherId, @Param("classId") Long classId);

}
