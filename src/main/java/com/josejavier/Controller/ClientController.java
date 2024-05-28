package com.josejavier.Controller;

import com.josejavier.JWT.JWTConfig;
import com.josejavier.model.Client;
import com.josejavier.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {
    @Autowired
    ClientService service;

    @Autowired
    JWTConfig jwtConfig;

    /**
     *  función para obtener todos los clientes
     * @return List<Client>
     */
    @GetMapping
    public ResponseEntity<List<Client>> getAllClients() {
        List<Client> clients = service.getAllClients();
        return ResponseEntity.ok(clients);
    }

    /**
     *  Función para obtener un cliente por su ID
     * @param id
     * @return Client
     */
    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable("id") int id) {

        Client client = service.getClientById(id);
        return ResponseEntity.ok(client);
    }

    /**
     *  Función para crear o actualizar un cliente
     * @param client
     * @return Client
     */

    @PostMapping
    public ResponseEntity<Client> CreateClient(@RequestBody Client client) {
        Client end = service.createClient(client);
        return ResponseEntity.ok(end);
    }

    /**
     *  Función para borrar un cliente por su ID en la base de datos
     * @param id
     * @return String "User deleted successfully"
     */

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteClient(@PathVariable("id") int id) {
        service.deleteClient(id);
        return ResponseEntity.ok("User deleted successfully");
    }

    /**
     *  Función para actualizar un cliente por su ID en la base de datos
     * @param id
     * @param client
     * @return Client
     */

    @PutMapping("/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable("id") int id, @RequestBody Client client) {
        client.setId(id);
        Client end = service.createClient(client);
        return ResponseEntity.ok(end);
    }

    /**
     *  Función para iniciar sesión de un cliente en la base de datos
     * @param
     * @return Client
     */
    @PostMapping("/login")
    public ResponseEntity<Client> login(@RequestBody Client loginData) {
        String username = loginData.getUsername();
        String password = loginData.getPassword();


        // Hashear la contraseña proporcionada
        String hashedPassword = hashPassword(password);

        // Llama al método del servicio con la contraseña hasheada
        Client client = service.findByUsernameAndPassword(username, hashedPassword);

        if (client != null) {
            return ResponseEntity.ok(client);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    // Método para hashear la contraseña
    private String hashPassword(String password) {
        String hash=org.apache.commons.codec.digest.DigestUtils.sha256Hex(password);

        return  hash;
    }

    /**
     *  Función para buscar un cliente por nombre de usuario o correo electrónico para comprobar si existe
     * @param client
     * @return Client
     */

    @PostMapping("/existsByUsernameOrEmail")
    private ResponseEntity<Client> existsByUsernameOrEmail(@RequestBody Client client) {
        String username = client.getUsername();
        String email = client.getMail();

        // Llama al método del servicio para buscar el cliente por nombre de usuario o correo electrónico
        Client existingClient = service.getClientByUsernameOrEmail(username, email);

        if (existingClient != null) {
            // Si el cliente existe, devuelve el cliente encontrado
            return ResponseEntity.ok(existingClient);
        } else {
            // Si el cliente no existe, devuelve un código de estado NOT FOUND
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/distinct-client-info/{teacherId}/{classId}/{option}")
    public ResponseEntity<List<Object[]>> getDistinctClientInfoByTeacherId(
            @PathVariable("teacherId") int teacherId,
            @PathVariable("classId") Integer classId,
            @PathVariable("option") int option
    ) {
        if (option < 1 || option > 3) {
            throw new IllegalArgumentException("Invalid option: " + option);
        }

        List<Object[]> clientInfo = service.findDistinctClientInfoByTeacherId(Long.valueOf(teacherId), option, classId);
        return ResponseEntity.ok(clientInfo);
    }



}

