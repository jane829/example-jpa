package org.github.jane829.students.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.github.jane829.students.domain.Student;
import org.github.jane829.students.exceptions.SameStudentException;
import org.github.jane829.students.service.StudentService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class StudentControllerTest
{
    private MockMvc mockMvc;

    @Mock
    private StudentService studentService;
    @InjectMocks
    private StudentController studentController;
    private Student student;

    @Before
    public void setUp() throws Exception
    {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(studentController)
                .setHandlerExceptionResolvers(simpleMappingExceptionResolver())
                .build();

        student = StudentUtils.createStudent();
    }

    public SimpleMappingExceptionResolver simpleMappingExceptionResolver()
    {
        SimpleMappingExceptionResolver resolver = new SimpleMappingExceptionResolver();
        resolver.setDefaultErrorView("errors");
        resolver.setDefaultStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return resolver;
    }

    @Test
    public void should_save_student_when_not_existed() throws Exception
    {
        // given
        Student saved = StudentUtils.createStudent();
        when(studentService.save(any(Student.class))).thenReturn(saved);

        // when
        mockMvc.perform(post("/students").contentType(StudentUtils.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(this.student)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.number", is("1234567")))
                .andExpect(jsonPath("$.first_name", is("mei")))
                .andExpect(jsonPath("$.last_name", is("han")));
    }

    @Test
    public void should_throw_exception_when_same_student_existed() throws Exception
    {
        // given
        when(studentService.save(any(Student.class))).thenThrow(SameStudentException.class);

        // when
        mockMvc.perform(post("/students")
                .contentType(StudentUtils.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(StudentUtils.createStudent())))
                .andExpect(status().isInternalServerError());
    }


    @Test
    public void should_return_student_info_when_find_one_saved() throws Exception
    {
        // given
        List<Student> returnedStudents = new ArrayList<>();
        returnedStudents.add(student);
        when(studentService.query(student.getNumber())).thenReturn(returnedStudents);

        // when
        mockMvc.perform(get("/students/{number}", student.getNumber()).contentType(StudentUtils.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.number", is(student.getNumber())))
                .andExpect(jsonPath("$.first_name", is(student.getFirst_name())))
                .andExpect(jsonPath("$.last_name", is(student.getLast_name())));

    }

    @Test
    public void should_return_null_after_deleted() throws Exception
    {
        // given
        when(studentService.delete(student.getNumber())).thenReturn(null);

        // when
        mockMvc.perform(delete("/students/{number}", student.getNumber()).contentType(StudentUtils.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        verify(studentService).delete(student.getNumber());
    }

    @Test
    public void should_update_student() throws Exception
    {
        // given
        Student updatedStudent = StudentUtils.createStudent();
        updatedStudent.setFirst_name("Lin");
        when(studentService.update(eq("1"), any(Student.class))).thenReturn(updatedStudent);

        // when
        mockMvc.perform(put("/students/1")
                .content(new ObjectMapper().writeValueAsString(student))
                .contentType(StudentUtils.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.first_name", is(updatedStudent.getFirst_name())));
    }

}
