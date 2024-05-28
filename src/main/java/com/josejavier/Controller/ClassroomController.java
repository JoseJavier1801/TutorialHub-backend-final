package com.josejavier.Controller;

import com.josejavier.model.Classroom;
import com.josejavier.DTO.ClassroomDTO;
import com.josejavier.model.Teacher;
import com.josejavier.service.ClassroomService;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/classrooms")
public class ClassroomController {

    @Autowired
    private ClassroomService classroomService;

    /**
     *  Método para crear un aula en la base de datos a través de una respuesta HTTP
     * @param classroom
     * @return ResponseEntity
     */
    @PostMapping
    public ResponseEntity<ClassroomDTO> createClassroom(@RequestBody ClassroomDTO classroom) {
        try {
            Classroom classroomToCreate = new Classroom(classroom);
            // Asignar el ID del profesor directamente si está presente
            if (classroom.getTeacherID() != 0) {
                Teacher teacher = new Teacher();
                teacher.setId(classroom.getTeacherID());
                classroomToCreate.setTeacher(teacher);
            }
            Classroom createdClassroom = classroomService.createOrUpdateClassroom(classroomToCreate);
            classroom.setId(createdClassroom.getId());
            return ResponseEntity.ok(classroom);
        } catch (RuntimeException e) {
            // Manejar la excepción apropiadamente, por ejemplo, registrarla
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     *  Método para actualizar un aula existente en la base de datos a través de una respuesta HTTP
     * @param id
     * @param classroomDTO
     * @return ResponseEntity
     */


    @PutMapping("/{id}")
    public ResponseEntity<ClassroomDTO> updateClassroom(@PathVariable("id") int id, @RequestBody ClassroomDTO classroomDTO) {
        try {
            // Verificar si el aula con la ID dada existe
            Classroom existingClassroom = classroomService.getClassroomById(id);

            if (existingClassroom == null) {
                // Manejar la situación en la que el aula no existe
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            // Actualizar todos los campos del aula existente con los valores proporcionados en el DTO
            existingClassroom.setDescription(classroomDTO.getDescription());
            existingClassroom.setType(classroomDTO.getType());
            existingClassroom.setCategory(classroomDTO.getCategory());
            existingClassroom.setDirection(classroomDTO.getDirection());
            existingClassroom.setPostalCode(classroomDTO.getPostalCode());
            existingClassroom.setProvince(classroomDTO.getProvince());
            existingClassroom.setLocalidad(classroomDTO.getLocalidad());
            existingClassroom.setPrice(classroomDTO.getPrice()); // Corregir aquí
            existingClassroom.setVideo(classroomDTO.getVideo()); // Corregir aquí

            // Convertir la ubicación del DTO a un objeto Point
            GeometryFactory geometryFactory = new GeometryFactory();
            Coordinate coordinate = new Coordinate(classroomDTO.getLng(), classroomDTO.getLat());
            Point location = geometryFactory.createPoint(coordinate);

            // Guardar la ubicación en el objeto Classroom
            existingClassroom.setLocation(location);

            // Guardar la actualización en el servicio
            Classroom updatedClassroom = classroomService.createOrUpdateClassroom(existingClassroom);

            // Crear y devolver el DTO actualizado
            ClassroomDTO updatedClassroomDTO = updatedClassroom.toDTO();
            return ResponseEntity.ok(updatedClassroomDTO);
        } catch (RuntimeException e) {
            // Manejar la excepción apropiadamente, por ejemplo, registrarla
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    // Método para crear un objeto Point a partir de latitud y longitud
    private Point createPoint(Double lat, Double lng) {
        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
        Coordinate coordinate = new Coordinate(lng, lat);
        return geometryFactory.createPoint(coordinate);
    }

    // Método para formatear la ubicación como una cadena "latitud,longitud"
    private String formatLocation(Point location) {
        if (location == null) {
            return null;
        }
        return location.getY() + "," + location.getX();
    }


    /**
     *  Método para borrar un aula existente en la base de datos a través de una respuesta HTTP
     * @param id
     * @return ResponseEntity
     */

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteClassroom(@PathVariable("id") int id) {
        classroomService.deleteClassroom(Integer.valueOf(id));
        return ResponseEntity.ok("Classroom deleted successfully");
    }

    /**
     *  Método para obtener un aula existente en la base de datos a través de una respuesta HTTP
     * @param id
     * @return
     */

    @GetMapping("/{id}")
    public ResponseEntity<ClassroomDTO> getClassroomById(@PathVariable("id") int id) {
        Classroom classroom = classroomService.getClassroomById(Integer.valueOf(id));
        ClassroomDTO classroomDTO = classroom.toDTO();
        return ResponseEntity.ok(classroomDTO);
    }

    /**
     *  Método para obtener todas las aulas existentes en la base de datos a través de una respuesta HTTP
     * @param userId
     * @return List
     */
    @GetMapping("/userClassrooms/{userId}")
    public List<Object[]> getUserClassrooms(@PathVariable Integer userId) {
        return classroomService.getUserClassroomDetails(userId);
    }

    /**
     *  Método para obtener todas las aulas existentes en la base de datos a través de una respuesta HTTP
     * @param teacherId
     * @return List
     */
    @GetMapping("/teacher/{teacherId}")
    public List<ClassroomDTO> getClassroomsByTeacherId(@PathVariable("teacherId") Integer teacherId) {

        return classroomService.getClassroomsByTeacherId(teacherId);
    }

    /**
     *  Método para obtener todas las aulas existentes en la base de datos a través de una respuesta HTTP
     * @return List
     */
    @GetMapping("/details")
    public List<ClassroomDTO> getAllClassroomDetails() {
        List<ClassroomDTO> result = classroomService.getAllClassroomDetails();

        return result;
    }

    /**
     *  Método para obtener todas las aulas existentes en la base de datos a través de una respuesta HTTP que coincidan con los campos de búsqueda
     * @param category

     * @return List
     */
    @GetMapping("/seeker")
    public List<ClassroomDTO> searchClassrooms(
            @RequestParam("category") String category)
           {

        return classroomService.getAllClassroomSeeker(category);
    }
    @GetMapping("/classes-by-point")
    public ResponseEntity<List<ClassroomDTO>> getClassesByPoint(
            @RequestParam("lat") String lat,
            @RequestParam("lng") String lng,
            @RequestParam("radiusInMeters") double radiusInMeters) {
        try {
            double latitude = Double.parseDouble(lat);
            double longitude = Double.parseDouble(lng);
            // Llamar al servicio para obtener las aulas cercanas
            List<ClassroomDTO> classes = classroomService.getAllClassByPoint(latitude, longitude, radiusInMeters);

            return ResponseEntity.ok(classes);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/filtered")
    public List<ClassroomDTO> getClassroomsByFilter(
            @RequestParam("minPrice") double minPrice,
            @RequestParam("maxPrice") double maxPrice,
            @RequestParam("filterValue") int filterValue) {

        return classroomService.getClassroomsByFilter(minPrice, maxPrice, filterValue);
    }




}