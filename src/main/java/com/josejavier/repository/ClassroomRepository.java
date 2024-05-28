package com.josejavier.repository;

import com.josejavier.model.Classroom;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassroomRepository extends JpaRepository<Classroom, Integer> {
    /**
     * funcion par optener las aulas de un profesor por su ID
     *
     * @param userId
     * @return
     */

    @Query("SELECT c.id AS client_id, c.photo AS client_photo, c.name AS client_name, " +
            "c.mail AS client_mail, c.date AS client_date, c.phone AS client_phone, " +
            "t.title AS teacher_title, t.biography AS teacher_biography, " +
            "cl.id AS classroom_id, cl.description, cl.type, cl.category, " +
            "ST_AsText(cl.location) AS location, cl.direction, cl.postalCode AS postal_code, cl.price,cl.video, " +
            "cl.province, cl.localidad, cl.duration " +
            "FROM Client c " +
            "JOIN Teacher t ON c.id = t.id " +
            "JOIN Classroom cl ON t.id = cl.teacher.id " +
            "WHERE c.id = :userId")
    List<Object[]> getUserClassroomDetails(@Param("userId") Integer userId);

    /**
     * funcion par optener las aulas de un profesor por su ID
     *
     * @param classroomId
     * @return List<Object [ ]>
     */

    @Query("SELECT c.id AS client_id, c.photo AS client_photo, c.name AS client_name, " +
            "c.mail AS client_mail, c.date AS client_date, c.phone AS client_phone, " +
            "t.title AS teacher_title, t.biography AS teacher_biography, " +
            "cl.id AS classroom_id, cl.description, cl.type, cl.category, cl.price, cl.video, " +
            "ST_AsText(cl.location) AS location, cl.direction, cl.postalCode AS postal_code, " +
            "cl.province, cl.localidad, cl.duration " +
            "FROM Client c " +
            "JOIN Teacher t ON c.id = t.id " +
            "JOIN Classroom cl ON t.id = cl.teacher.id " +
            "WHERE cl.id = :classroom_id")
    List<Object[]> getClassRoomByID(@Param("classroom_id") Integer classroomId);

    /**
     * funcion par optener las aulas de un profesor por su categoria, localidad y postalCode
     *
     * @param category

     * @return List<Object [ ]>
     */

    @Query(value = "SELECT " +
            "c.id AS classroom_id, " +
            "c.description AS classroom_description, " +
            "c.type AS classroom_type, " +
            "c.category AS classroom_category, " +
            "c.location AS classroom_location, " +
            "c.direction AS classroom_direction, " +
            "c.postal_code AS classroom_postal_code, " +
            "c.province AS classroom_province, " +
            "c.localidad AS classroom_localidad, " +
            "c.duration AS classroom_duration, " +
            "c.price AS classroom_price, " +
            "c.video AS classroom_video, " +
            "c.id_teacher AS classroom_id_teacher, " +
            "cl.name AS teacher_name, " +
            "cl.photo AS teacher_photo, " +
            "cl.mail AS teacher_mail, " +
            "cl.phone AS teacher_phone, " +
            "t.biography AS teacher_biography, " +
            "t.title AS teacher_title " +
            "FROM " +
            "classroom c " +
            "JOIN teacher t ON t.id = c.id_teacher " +
            "JOIN client cl ON cl.id = t.id " +
            "WHERE " +
            "(:category IS NULL OR c.category = :category) ", nativeQuery = true)
    List<Object[]> searchClassrooms(
            @Param("category") String category

    );

    /**
     * funcion par optener las aulas de un profesor por su ID
     *
     * @param teacherId
     * @return List<Object [ ]>
     */
    @Query(value = "SELECT " +
            "c.id AS classroom_id, " +
            "c.description AS classroom_description, " +
            "c.type AS classroom_type, " +
            "c.category AS classroom_category, " +
            "(c.location) AS classroom_location, " +
            "c.direction AS classroom_direction, " +
            "c.postal_code AS classroom_postal_code, " +
            "c.province AS classroom_province, " +
            "c.localidad AS classroom_localidad, " +
            "c.duration AS classroom_duration, " +
            "c.price AS classroom_price, " +
            "c.video AS classroom_video, " +
            "c.id_teacher AS classroom_id_teacher, " +
            "cl.name AS teacher_name, " +
            "cl.photo AS teacher_photo, " +
            "cl.mail AS teacher_mail, " +
            "cl.phone AS teacher_phone, " +
            "t.biography AS teacher_biography, " +
            "t.title AS teacher_title " +
            "FROM " +
            "classroom c " +
            "JOIN teacher t ON t.id = c.id_teacher " +
            "JOIN client cl ON cl.id = t.id " +
            "WHERE " +
            "t.id = :teacherId", nativeQuery = true)
    List<Object[]> findByTeacherId(@Param("teacherId") Integer teacherId);

    /**
     * funcion par optener todas las aulas
     *
     * @return List<Object [ ]>
     */


    @Query(value = "SELECT " +
            "c.id AS classroom_id, " +
            "c.description AS classroom_description, " +
            "c.type AS classroom_type, " +
            "c.category AS classroom_category, " +
            "c.location AS classroom_location, " +
            "c.direction AS classroom_direction, " +
            "c.postal_code AS classroom_postal_code, " +
            "c.province AS classroom_province, " +
            "c.localidad AS classroom_localidad, " +
            "c.duration AS classroom_duration, " +
            "c.price AS classroom_price, " +
            "c.video AS classroom_video, " +
            "c.id_teacher AS classroom_id_teacher, " +
            "cl.name AS teacher_name, " +
            "cl.photo AS teacher_photo, " +
            "cl.mail AS teacher_mail, " +
            "cl.phone AS teacher_phone, " +
            "t.biography AS teacher_biography, " +
            "t.title AS teacher_title, " +
            "ST_Distance(" +
            "    ST_SetSRID(c.location, 4326), " +
            "    ST_SetSRID(ST_GeogFromText('POINT(' || :longitude || ' ' || :latitude || ')'), 4326) " +
            ") AS distance " +
            "FROM " +
            "classroom c " +
            "JOIN " +
            "teacher t ON t.id = c.id_teacher " +
            "JOIN " +
            "client cl ON cl.id = t.id " +
            "WHERE " +
            "ST_Distance(" +
            "    ST_SetSRID(c.location, 4326), " +
            "    ST_SetSRID(ST_GeogFromText('POINT(' || :longitude || ' ' || :latitude || ')'), 4326) " +
            ") <= :radius_in_meters " +
            "ORDER BY distance ASC", nativeQuery = true)
    List<Object[]> getAllClassByPoint(@Param("latitude") double latitude, @Param("longitude") double longitude,
                                      @Param("radius_in_meters") double radiusInMeters);



    ///////////////////////////////////////nuevas funciones /////////////////////////////////////////////////
    @Query(value = "SELECT " +
            "c.id AS classroom_id, " +
            "c.description AS classroom_description, " +
            "c.type AS classroom_type, " +
            "c.category AS classroom_category, " +
            "c.location AS classroom_location, " +
            "c.direction AS classroom_direction, " +
            "c.postal_code AS classroom_postal_code, " +
            "c.province AS classroom_province, " +
            "c.localidad AS classroom_localidad, " +
            "c.duration AS classroom_duration, " +
            "c.price AS classroom_price, " +
            "c.video AS classroom_video, " +
            "c.id_teacher AS classroom_id_teacher, " +
            "cl.name AS teacher_name, " +
            "cl.photo AS teacher_photo, " +
            "cl.mail AS teacher_mail, " +
            "cl.phone AS teacher_phone, " +
            "t.biography AS teacher_biography, " +
            "t.title AS teacher_title " +
            "FROM " +
            "classroom c " +
            "JOIN teacher t ON t.id = c.id_teacher " +
            "JOIN client cl ON cl.id = t.id", nativeQuery = true)
    List<Object[]> getallClassroomDetails();
    @Query(value = "SELECT " +
            "c.id AS classroom_id, " +
            "c.description AS classroom_description, " +
            "c.type AS classroom_type, " +
            "c.category AS classroom_category, " +
            "c.location AS classroom_location, " +
            "c.direction AS classroom_direction, " +
            "c.postal_code AS classroom_postal_code, " +
            "c.province AS classroom_province, " +
            "c.localidad AS classroom_localidad, " +
            "c.duration AS classroom_duration, " +
            "c.price AS classroom_price, " +
            "c.video AS classroom_video, " +
            "c.id_teacher AS classroom_id_teacher, " +
            "cl.name AS teacher_name, " +
            "cl.photo AS teacher_photo, " +
            "cl.mail AS teacher_mail, " +
            "cl.phone AS teacher_phone, " +
            "t.biography AS teacher_biography, " +
            "t.title AS teacher_title " +
            "FROM " +
            "classroom c " +
            "JOIN teacher t ON t.id = c.id_teacher " +
            "JOIN client cl ON cl.id = t.id " +
            "ORDER BY cl.name", nativeQuery = true)
    List<Object[]> getallClassroomDetailsOPtionOne();
    @Query(value = "SELECT " +
            "c.id AS classroom_id, " +
            "c.description AS classroom_description, " +
            "c.type AS classroom_type, " +
            "c.category AS classroom_category, " +
            "c.location AS classroom_location, " +
            "c.direction AS classroom_direction, " +
            "c.postal_code AS classroom_postal_code, " +
            "c.province AS classroom_province, " +
            "c.localidad AS classroom_localidad, " +
            "c.duration AS classroom_duration, " +
            "c.price AS classroom_price, " +
            "c.video AS classroom_video, " +
            "c.id_teacher AS classroom_id_teacher, " +
            "cl.name AS teacher_name, " +
            "cl.photo AS teacher_photo, " +
            "cl.mail AS teacher_mail, " +
            "cl.phone AS teacher_phone, " +
            "t.biography AS teacher_biography, " +
            "t.title AS teacher_title " +
            "FROM " +
            "classroom c " +
            "JOIN teacher t ON t.id = c.id_teacher " +
            "JOIN client cl ON cl.id = t.id " +
            "WHERE c.price BETWEEN :minPrice AND :maxPrice " + // Agregar la condición de precio
            "ORDER BY c.price", nativeQuery = true)
    List<Object[]> getallClassroomDetailsOptionByPriceRange(@Param("minPrice") double minPrice, @Param("maxPrice") double maxPrice);



}

