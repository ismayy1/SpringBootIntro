package com.tpe.controller;

import com.tpe.domain.Student;
import com.tpe.dto.StudentDTO;
import com.tpe.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/students")    // http://localhost:8080/students + HTTP Methods
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping // http://localhost:8080/students + GET Request
    public ResponseEntity<List<Student>> getAll() { // ResponseEntity -> Entire response obj with Headers, Response Body, HTTP Status Code.
        List<Student> students = studentService.getAllStudents();
        return ResponseEntity.ok(students); // sends the parameter along with 200 OK status code
    }

    @PostMapping    // http://localhost:8080/students + POST Request + JSON body
    public ResponseEntity<Map<String, String>> createStudent(@Valid @RequestBody Student student) {   // to give the multiple messages as response

        studentService.saveStudent(student);
        Map<String, String> map = new HashMap<>();

        map.put("message", "Student: '" + student.getFirstName() + " " + student.getLastName() + "' created successfully.");
        map.put("status", "SUCCESS");

        return new ResponseEntity<>(map, HttpStatus.CREATED);   // bcs we created an obj, the status code is '201' -> CREATED
    }

    @GetMapping("/query") // http://localhost:8080/students/query?id=1  + GET Request - QUERY
    public ResponseEntity<Student> getStudentById(@RequestParam("id") Long id) {
        Student student = studentService.findStudentById(id);
        return ResponseEntity.ok(student);
    }

    @GetMapping("/{id}") // http://localhost:8080/students/1  + GET Request - Path Var
    public ResponseEntity<Student> getStudentByIdPathVar(@PathVariable("id") Long id) {
        Student student = studentService.findStudentById(id);
        return ResponseEntity.ok(student);
    }

    @DeleteMapping("/{id}")  // http://localhost:8080/students/1 + DELETE Request
    public ResponseEntity<Map<String, String>> removeStudentById(@PathVariable("id") Long id) {
        studentService.deleteStudentById(id);
        Map<String, String> map = new HashMap<>();

        map.put("message", "Student deleted successfully.");
        map.put("status", "SUCCESS");

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PutMapping("/{id}")    // http://localhost:8080/students/1 + PUT Request + JSON RequestBody
    public ResponseEntity<String> updateStudentById(@PathVariable("id") Long id, @RequestBody StudentDTO studentDTO) {

        studentService.updateStudent(id, studentDTO);
        String message = "StudentUpdated successfully.";

        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
