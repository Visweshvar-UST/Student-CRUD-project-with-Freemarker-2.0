package com.studentdemo2.studentCRUD2.controller;

import com.studentdemo2.studentCRUD2.model.Student;
import com.studentdemo2.studentCRUD2.service.StudentArrayService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
public class StudentController {
    @Autowired
    StudentArrayService studentService;

    @GetMapping("/student")
    public ResponseEntity<?> getStudent(){
        if (!studentService.getStudents().isEmpty())
            return ResponseEntity.ok(studentService.getStudents());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/student")
    public ResponseEntity<?> createStudent(@RequestBody Student student){
        return new ResponseEntity<Student>(studentService.addStudents(student), HttpStatus.CREATED);
    }

    @GetMapping("/student/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable("id") Long id) {
        Student student = studentService.getStudents().stream()
                .filter(s -> s.getId().equals(id))
                .findFirst()
                .orElse(null);
        if (student != null) {
            return ResponseEntity.ok(student);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found");
    }

    @PutMapping("/student/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable("id") Long id, @RequestBody Student updatedStudent) {
        Student existingStudent = studentService.getStudents().stream()
                .filter(s -> s.getId().equals(id))
                .findFirst()
                .orElse(null);
        if (existingStudent != null) {
            existingStudent.setName(updatedStudent.getName());
            existingStudent.setAge(updatedStudent.getAge());
            return ResponseEntity.ok(existingStudent);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found");
    }

    @DeleteMapping("/student/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable("id") Long id) {
        boolean deleted = studentService.getStudents().removeIf(s -> s.getId().equals(id));
        if (deleted) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found");
    }
}
