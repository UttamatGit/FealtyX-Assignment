package org.fealtyx.sms_ai.controllers;

import jakarta.validation.Valid;
import org.fealtyx.sms_ai.model.Student;
import org.fealtyx.sms_ai.services.OllamaService;
import org.fealtyx.sms_ai.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;
    private final OllamaService ollamaService;

    @Autowired
    public StudentController(StudentService studentService, OllamaService ollamaService) {
        this.studentService = studentService;
        this.ollamaService = ollamaService;
    }

    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable int id) {
        return studentService.getStudentById(id);
    }

    @PostMapping
    public Student createStudent(@Valid @RequestBody Student student) {
        return studentService.createStudent(student);
    }

    @PutMapping("/{id}")
    public Student updateStudent(@PathVariable int id, @Valid @RequestBody Student student) {
        return studentService.updateStudent(id, student);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable int id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/summary")
    public String getStudentSummary(@PathVariable int id) {
        Student student = studentService.getStudentById(id);
        return ollamaService.generateStudentSummary(student);
    }
}