package com.student.information.system.controller;
import com.student.information.system.dto.StudentDTO;
import com.student.information.system.model.Student;
import com.student.information.system.service.StudentService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@RunWith(SpringRunner.class)
public class StudentRestControllerSimpleTest {

    private StudentRestController studentRestController;

    @MockBean
    private StudentService studentService;

    @Before
    public void initializeStudentRestController() {
        this.studentRestController = new StudentRestController(studentService);
    }

    @Test
    public void testServiceFindAllReturnEmptyListShouldFindAllReturnEmpty() {
        assertTrue(studentRestController.getAllStudents().size() == 0);
    }

    @Test
    public void testServiceFindAllReturnOneStudentShouldReturnListWithOneElement() {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setEmail("dummy@email.com");
        studentDTO.setId("dummyId");
        studentDTO.setName("firstNameLastName");
        Student student = new Student();
        student.setEmail("dummy@email.com");
        student.setId("dummyId");
        student.setName("firstNameLastName");
        List<StudentDTO> expectedStudents = List.of(studentDTO);
        when(studentService.findAll()).thenReturn(List.of(student));
        assertEquals("Should return at least one item", studentRestController.getAllStudents(),expectedStudents);

    }

    @Test
    public void testFindByStudentNumberShouldSucceed() {
        Student student = new Student();
        student.setId("dummyid");
        student.setStudentNumber(11L);
        when(studentService.findByStudentNumber(anyLong())).thenReturn(student);
        assertThat(studentRestController.getStudentByStudentNumber(11L),
                Matchers.hasProperty("studentNumber", Matchers.notNullValue()));
        assertThat(studentRestController.getStudentByStudentNumber(11L).getStudentNumber(),
                Matchers.is(11L));
    }

    @Test
    public void testFindByStudentNumberShouldCallStudentService() {
        studentRestController.getStudentByStudentNumber(11L);
        verify(studentService, Mockito.times(1)).findByStudentNumber(11L);
    }

    @Test
    public void testIfStudentNumberIsEmptyShouldNotCallDatabase() {
        studentRestController.getStudentByStudentNumber(null);
        verify(studentService,Mockito.never()).findByStudentNumber(anyLong());
    }
}
