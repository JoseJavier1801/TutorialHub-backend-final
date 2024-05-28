package com.josejavier.Controller;



import com.josejavier.JWT.JWTConfig;
import com.josejavier.model.Teacher;
import com.josejavier.repository.TeacherRepository;
import com.josejavier.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teachers")
public class TeacherController {

    @Autowired
    TeacherService service;

    @Autowired
    JWTConfig jwtConfig;

    /**
     *  Función para obtener todos los profesores de la base de datos
     * @return List<Teacher>
     */

    @GetMapping
    public ResponseEntity<List<Teacher>> getAllTeachers() {
        List<Teacher> Teachers =service.getAllTeachers();
        return  ResponseEntity.ok(Teachers);
    }

    /**
     *  Función para obtener un profesor por su ID en la base de datos
     * @param id
     * @return Teacher
     */

    @GetMapping("/{id}")
    public ResponseEntity<Teacher> getTeacherById(@PathVariable("id") int id) {
        Teacher teacher=service.getTeacherById(id);
        return ResponseEntity.ok(teacher);
    }

    /**
     *  Función para crear o actualizar un profesor en la base de datos
     * @param teacher
     * @return Teacher
     */

    @PostMapping
    public ResponseEntity<Teacher> CreateTeacher(@RequestBody Teacher teacher) {
        Teacher end=service.createOrUpdateTeacher(teacher);
        return ResponseEntity.ok(end);
    }

    /**
     *  Función para borrar un profesor por su ID en la base de datos
     * @param id
     * @return String "Teacher deleted successfully"
     */

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTeacher(@PathVariable("id") int id) {
        service.deleteTeacher(id);
        return ResponseEntity.ok("Teacher deleted successfully");
    }

    /**
     *  Función para actualizar un profesor por su ID en la base de datos
     * @param id
     * @param teacher
     * @return Teacher
     */

    @PutMapping("/{id}")
    public ResponseEntity<Teacher> updateTeacher(@PathVariable("id") int id, @RequestBody Teacher teacher) {
        teacher.setId(id);
        Teacher end=service.createOrUpdateTeacher(teacher);
        return ResponseEntity.ok(end);
    }

    /**
     *  Función para iniciar sesión de un profesor en la base de datos
     * @param username
     * @param password
     * @return Teacher
     */
    @GetMapping("/login")
    public ResponseEntity<Teacher> loginTeacher(@RequestParam("username") String username, @RequestParam("password") String password) {
        Teacher teacher = service.findByUsernameAndPassword(username, password);
        if (teacher != null) {
            String token = jwtConfig.generateToken(username);
            System.out.println(token);
            return ResponseEntity.ok(teacher);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
