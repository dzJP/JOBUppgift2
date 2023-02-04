package com.student.information.system.controller;

import com.student.information.system.dto.StudentDTO;
import com.student.information.system.model.Student;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

public class StudentRestControllerTest {

    private Student studentExample;
    private StudentDTO studentDTOExample;

    @Before
    public void initializeStudents() {
        studentExample = new Student();
        studentExample.setStudentNumber(1);
        studentExample.setId("1");
        studentExample.setName("test student");

        studentDTOExample = new StudentDTO();
        studentDTOExample.setStudentNumber(studentExample.getStudentNumber());
        studentDTOExample.setId(studentDTOExample.getId());
        studentDTOExample.setName(studentDTOExample.getName());
    }

    @Test
    public void testGetAllStudentsReturnOneStudent() {
        StudentRestController studentRestController = new StudentRestController();
        Assert.assertEquals("getAllStudents should return one element",
                Collections.singletonList(studentDTOExample),studentRestController.getAllStudents());
    }

    @Test
    public void happyPathScenario() {

    }
}
