package com.studentdemo2.studentCRUD2.controller;

// import java.util.Optional;

import com.studentdemo2.studentCRUD2.model.Student;
import com.studentdemo2.studentCRUD2.service.StudentArrayService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/api/v1")
public class StudentViewController {

    @Autowired
    private StudentArrayService studentService;

    @GetMapping("/students")
    public String getAllStudents(Model model) {
        model.addAttribute("students", studentService.getStudents());
        System.out.println("Rendering studentList page");
        return "studentList";
    }

    @PostMapping("/student-view")
    public String createStudent(@ModelAttribute Student student, Model model) {
        studentService.addStudents(student);
        System.out.println(student);
        model.addAttribute("students", studentService.getStudents());
        return "redirect:/api/v1/students";
    }

    // @PutMapping("/students/{id}")
    // public ResponseEntity<Student> updateStudent(@PathVariable("id") Long id, 
    //                                               @RequestParam("name") String name, 
    //                                               @RequestParam("age") int age) {
    //     Student updatedStudent = new Student(id, name, age);
    //     Student student = studentService.updateStudent(id, updatedStudent);
        
    //     if (student != null) {
    //         return ResponseEntity.ok(student);
    //     } else {
    //         return ResponseEntity.notFound().build();
    //     }
    // }
    

    @PutMapping("/students/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable("id") Long id, @RequestBody Student updatedStudent) {
        Student student = studentService.updateStudent(id, updatedStudent);
        System.out.println(updatedStudent);
        if (student != null) {
            return ResponseEntity.ok(student);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/students/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable("id") Long id) {
        boolean isDeleted = studentService.deleteStudent(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}


