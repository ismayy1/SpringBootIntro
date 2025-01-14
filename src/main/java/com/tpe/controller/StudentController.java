package com.tpe.controller;

import com.tpe.domain.Student;
import com.tpe.dto.StudentDTO;
import com.tpe.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/students")    // http://localhost:8080/students + HTTP Methods
public class StudentController {

    Logger logger = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    private StudentService studentService;

    @GetMapping("/welcome")
    public String welcome(HttpServletRequest request) {
        logger.warn("--------- Welcome {}", request.getServletPath());
        return "Welcome to the Student Controller.";
    }

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
        Student student = studentService.findStudent(id);
        return ResponseEntity.ok(student);
    }

    @GetMapping("/{id}") // http://localhost:8080/students/1  + GET Request - Path Var
    public ResponseEntity<Student> getStudentByIdPathVar(@PathVariable("id") Long id) {
        Student student = studentService.findStudent(id);
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

    @GetMapping("/page") // http://localhost:8080/students/page?page=1&size=2&sort=firstName&direction=ASC + GET Request
    public ResponseEntity<Page<Student>> getAllWithPage(@RequestParam("page") int page,
                                                        @RequestParam("size") int size,
                                                        @RequestParam("sort") String prop,
                                                        @RequestParam("direction") Sort.Direction direction) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, prop));
        Page<Student> studentPage = studentService.findAllWithPage(pageable);
        return ResponseEntity.ok(studentPage);
    }

    @GetMapping("/querylastname")    // http://localhost:8080/students/querylastname?lastName=Smith + GET Request
    public ResponseEntity<List<Student>> getStudentByLastName(@RequestParam("lastName") String lastName) {
        List<Student> students = studentService.findStudent(lastName);
        return ResponseEntity.ok(students);
    }

    @GetMapping("/query/{grade}")    // http://localhost:8080/students/query/70 + GET Request
    public ResponseEntity<List<Student>> getStudentsByGrade(@PathVariable("grade") Integer grade) {
        List<Student> students = studentService.findAllWithGrade(grade);
        return ResponseEntity.ok(students);
    }

    @GetMapping("/query/dto")   // http://localhost:8080/students/query/dto?id=1 + GET Request
    public ResponseEntity<StudentDTO> getStudentDTOById(@RequestParam("id") Long id) {
        StudentDTO studentDTO = studentService.findStudentDTOById(id);
        return ResponseEntity.ok(studentDTO);
    }
}
