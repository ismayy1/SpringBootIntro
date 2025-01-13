package com.tpe.service;

import com.tpe.domain.Student;
import com.tpe.dto.StudentDTO;
import com.tpe.exception.ConflictException;
import com.tpe.exception.ResourceNotFoundException;
import com.tpe.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

//    find Student By ID
    public Student findStudent(Long id) {
        return studentRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Not student found with the ID: " + id));

//        return new StudentDTO(student);
    }

//    find Student By Last Name
public List<Student> findStudent(String lastName) {
    return studentRepository.findByLastName(lastName);
}

    public void deleteStudentById(Long id) {
        Student student = findStudent(id);
//        studentRepository.delete(student);
        studentRepository.deleteById(id);
    }

//    Updater method


    public void updateStudent(Long id, StudentDTO studentDTO) {
        Student existingStudent = findStudent(id);

        boolean emailExists = studentRepository.existsByEmail(studentDTO.getEmail());

        if (emailExists && !studentDTO.getEmail().equals(existingStudent.getEmail())) {
            throw new ConflictException("Email already exists. Please provide different one.");
        }

//         same as above
//        if (studentDTO.getEmail().equals(existingStudent.getEmail())) {
////            call to Updater logic
////            return updateCount
//        } else if (studentRepository.existsByEmail(studentDTO.getEmail())) {
////            call to updater logic
////            return updateCount
//        } else {

//        }

        existingStudent.setFirstName(studentDTO.getName());
        existingStudent.setLastName(studentDTO.getLastName());
        existingStudent.setGrade(studentDTO.getGrade());
        existingStudent.setEmail(studentDTO.getEmail());
        existingStudent.setPhoneNumber(studentDTO.getPhoneNumber());

        studentRepository.save(existingStudent);

    }

    public Page<Student> findAllWithPage(Pageable pageable) {
        return studentRepository.findAll(pageable);
    }

    public List<Student> findAllWithGrade(Integer grade) {
        return studentRepository.findAllWithGrade(grade);
    }

    public StudentDTO findStudentDTOById(Long id) {
        return studentRepository.findStudentDTOById(id).orElseThrow(() ->
                new ResourceNotFoundException("Not student DTO found with the ID: " + id));
    }
}
