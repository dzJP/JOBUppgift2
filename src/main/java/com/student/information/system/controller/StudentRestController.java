package com.student.information.system.controller;

import com.student.information.system.dto.StudentDTO;
import com.student.information.system.model.Student;
import com.student.information.system.service.StudentService;
import com.student.information.system.util.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ragcrix
 */
@RestController
@RequestMapping("/students")
public class StudentRestController {


    private StudentService studentService;

    @Autowired
    public StudentRestController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping(value = "/")
    public List<StudentDTO> getAllStudents() {
        return ObjectMapperUtils.mapAll(studentService.findAll(), StudentDTO.class);
    }

    @GetMapping(value = "/byStudentNumber/{studentNumber}")
    public StudentDTO getStudentByStudentNumber(@PathVariable("studentNumber") Long studentNumber) {
        if (studentNumber != null) {
            Student student = studentService.findByStudentNumber(studentNumber);
            if (student != null) {
                return ObjectMapperUtils.map(student, StudentDTO.class);
            } else {
                return new StudentDTO();
            }
        } else {
            return null;
        }
    }

    @GetMapping(value = "/byEmail/{email}")
    public StudentDTO getStudentByEmail(@PathVariable("email") String email) {
        return ObjectMapperUtils.map(studentService.findByEmail(email), StudentDTO.class);
    }

    @GetMapping(value = "/orderByGpa")
    public List<StudentDTO> findAllByOrderByGpaDesc() {
        return ObjectMapperUtils.mapAll(studentService.findAllByOrderByGpaDesc(), StudentDTO.class);
    }

    @PostMapping(value = "/save")
    public ResponseEntity<?> saveOrUpdateStudent(@RequestBody StudentDTO studentDTO) {
        studentService.saveOrUpdateStudent(ObjectMapperUtils.map(studentDTO, Student.class));
        return new ResponseEntity("Student added successfully", HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{studentNumber}")
    public ResponseEntity<?> deleteStudentByStudentNumber(@PathVariable long studentNumber) {
        studentService.deleteStudentById(studentService.findByStudentNumber(studentNumber).getId());
        return new ResponseEntity("Student deleted successfully", HttpStatus.OK);
    }

}
