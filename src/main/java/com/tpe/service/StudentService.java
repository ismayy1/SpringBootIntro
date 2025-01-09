package com.tpe.service;

import com.tpe.domain.Student;
import com.tpe.exception.ConflictException;
import com.tpe.exception.ResourceNotFoundException;
import com.tpe.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;


    public List<Student> getAllStudents() {
//        ASK: Do we need to handle anything, do we need to check anything?
//        If there are no check needed to be done, we need to perform logic operations,
//        we can simply go to the repo without anything else.
        return studentRepository.findAll(); // SELECT * FROM student
    }

    public void saveStudent(Student student) {
//        ASK: do we need handle or check anything
//        TODO: Check if email exists on the db
//        TODO: Throw a custom exception if it does so

        if (studentRepository.existsByEmail(student.getEmail())) {    // Derived method
            throw new ConflictException("Email address is already in use, Please try a different one.");
        }
        studentRepository.save(student);
    }

    public Student findStudentById(Long id) {
        return studentRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Not student found with the ID: " + id));
    }
}
