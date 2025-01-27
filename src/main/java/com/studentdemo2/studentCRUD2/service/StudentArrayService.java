package com.studentdemo2.studentCRUD2.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

import com.studentdemo2.studentCRUD2.model.Student;

@Service
public class StudentArrayService {
    ArrayList<Student> students = new ArrayList<>();
    Long seqId = 100l;
    
    public StudentArrayService() {
        students.add(new Student(seqId++,"Viswa",22));
        students.add(new Student(seqId++,"Axel",21));
        students.add(new Student(seqId++,"Anu",21));
        students.add(new Student(seqId++,"Rahul",23));
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public Student addStudents(Student students) {
        students.setId(seqId++);
        this.students.add(students);
        return students;
    }

    public StudentArrayService(ArrayList<Student> students) {
        this.students = students;
    }

    public Optional<Student> getStudentById(Long id) {
        return students.stream()
                .filter(student -> student.getId().equals(id))
                .findFirst();
    }

    public Student updateStudent(Long id, Student updatedStudent) {
        for (Student student : students) {
            if (student.getId().equals(id)) {
                student.setName(updatedStudent.getName());
                student.setAge(updatedStudent.getAge());
                return student;
            }
        }
        return null;
    }

    public boolean deleteStudent(Long id) {
        Optional<Student> studentOpt = getStudentById(id);
        if (studentOpt.isPresent()) {
            students.remove(studentOpt.get());
            return true;
        }
        return false;
    }

}
