package com.josejavier.service;

import com.josejavier.model.Teacher;
import com.josejavier.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherService {

    @Autowired
    private TeacherRepository repo;

    /**
     *  funcion par optener todos los profesores de la base de datos
     * @return List<Teacher>
     */

    public List<Teacher> getAllTeachers() {
        return repo.findAll();
    }

    /**
     *  funcion par optener un profesor por su ID de la base de datos
     * @param id
     * @return Teacher
     */
    public Teacher getTeacherById(int id) {

        Optional<Teacher> teacher = repo.findById(id);

        if (teacher.isPresent()) {
            return teacher.get();
        } else {
            throw new RuntimeException("Teacher not found with id: " + id);
        }
    }

    /**
     *  Función para crear o actualizar un usuario en la base de datos
     * @param teacher
     * @return Teacher
     */
    public Teacher createOrUpdateTeacher(Teacher teacher) {
        Teacher end;

        if (teacher.getId() != 0) { // update
            Optional<Teacher> result = repo.findById(teacher.getId());
            if (result.isPresent()) {
                Teacher existingTeacher = result.get();
                existingTeacher.setName(teacher.getName());
                existingTeacher.setDate(teacher.getDate());
                existingTeacher.setUsername(teacher.getUsername());
                existingTeacher.setMail(teacher.getMail());
                existingTeacher.setPhone(teacher.getPhone());
                existingTeacher.setPhoto(teacher.getPhoto());
                existingTeacher.setTitle(teacher.getTitle());
                existingTeacher.setBiography(teacher.getBiography());

                // Hashear la contraseña antes de guardarla
                String hashedPassword = hashPassword(teacher.getPassword());
                existingTeacher.setPassword(hashedPassword);

                end = repo.save(existingTeacher);
            } else {
                throw new RuntimeException("Teacher not found with id: " + teacher.getId());
            }
        } else { // insert
            // Hashear la contraseña antes de guardarla
            String hashedPassword = hashPassword(teacher.getPassword());
            teacher.setPassword(hashedPassword);

            end = repo.save(teacher);
        }

        return end;
    }

    // Método para hashear la contraseña
    private String hashPassword(String password) {
        String hash=org.apache.commons.codec.digest.DigestUtils.sha256Hex(password);

        return  hash;
    }

    /**
     *  Función para borrar un usuario por su ID de la base de datos
     * @param id
     */
    public void deleteTeacher(int id) {
        Optional<Teacher> result = repo.findById(id);
        if (result.isPresent()) {
            repo.deleteById(id);
        } else {
            throw new RuntimeException("Teacher not found with id: " + id);
        }
    }

    /**
     *  Función para buscar un usuario por su nombre de usuario y su contraseña en la base de datos y devolverlo o un error si no existe o no coincide la contraseña
     * @param username
     * @param password
     * @return Teacher
     */
    public Teacher findByUsernameAndPassword(String username, String password) {

        return repo.findByUsernameAndPassword(username, password);
    }



}

