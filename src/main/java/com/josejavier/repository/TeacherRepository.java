package com.josejavier.repository;

import com.josejavier.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TeacherRepository extends JpaRepository<Teacher, Integer> {

    /**
     *  funcion par optener un profesor por su username o password para iniciar sesion
     * @param username
     * @param password
     * @return Teacher
     */

    @Query("SELECT t FROM Teacher t WHERE t.username = :username or t.password = :password")
    Teacher findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);


}
