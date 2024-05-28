package com.josejavier.Controller;

import com.josejavier.DTO.HomeworkDTO;
import com.josejavier.service.HomeworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Homework")
public class HomeworkController {

    private final HomeworkService homeworkService;

   @Autowired
    public HomeworkController(HomeworkService homeworkService) {
        this.homeworkService = homeworkService;
    }

    @PostMapping
    public ResponseEntity<HomeworkDTO> createHomework(@RequestBody HomeworkDTO homeworkDTO) {
        HomeworkDTO createdHomework = homeworkService.createHomework(homeworkDTO);
        return ResponseEntity.ok(createdHomework);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HomeworkDTO> getHomeworkById(@PathVariable int id) {
        HomeworkDTO homeworkDTO = homeworkService.getHomeworkById(id);
        return ResponseEntity.ok(homeworkDTO);
    }

    @GetMapping
    public ResponseEntity<List<HomeworkDTO>> getAllHomeworks() {
        List<HomeworkDTO> homeworkList = homeworkService.getAllHomeworks();
        return ResponseEntity.ok(homeworkList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HomeworkDTO> updateHomework(@PathVariable int id, @RequestBody HomeworkDTO homeworkDTO) {
        HomeworkDTO updatedHomework = homeworkService.updateHomework(id, homeworkDTO);
        return ResponseEntity.ok(updatedHomework);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHomework(@PathVariable int id) {
        homeworkService.deleteHomework(id);
        return ResponseEntity.noContent().build();
    }
}
