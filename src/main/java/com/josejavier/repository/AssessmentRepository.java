package com.josejavier.repository;

import com.josejavier.model.Assessment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AssessmentRepository extends JpaRepository<Assessment, Integer> {

    /**
     *  funcion par optener las calificaciones de un profesor por su ID
     * @param teacherId
     * @return List<Assessment>
     */
    @Query("SELECT a FROM Assessment a WHERE a.teacher.id = :teacherId")
    List<Assessment> findByTeacherId(@Param("teacherId") Integer teacherId);

    @Query("SELECT AVG(a.assessment) FROM Assessment a WHERE a.teacher.id = :teacherId")
    Optional<Double> findAverageAssessmentByTeacherId(@Param("teacherId") Integer teacherId);
}

