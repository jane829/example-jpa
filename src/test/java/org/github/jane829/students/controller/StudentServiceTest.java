package org.github.jane829.students.controller;

import org.github.jane829.students.domain.Student;
import org.github.jane829.students.exceptions.SameStudentException;
import org.github.jane829.students.repository.StudentRepository;
import org.github.jane829.students.service.StudentService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class StudentServiceTest
{
    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception
    {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void should_save_student_when_not_existed() throws Exception
    {
        // given
        Student student = StudentUtils.createStudent();
        when(studentRepository.findByNumber(any())).thenReturn(null);
        when(studentRepository.save(student)).thenReturn(student);

        // when
        Student savedStudent = studentService.save(student);

        // then
        assertThat(savedStudent.getNumber(), is(student.getNumber()));
    }

    @Test
    public void should_throw_exception_if_same_student_existed() throws Exception
    {
        thrown.expect(SameStudentException.class);

        // given
        Student student = StudentUtils.createStudent();
        List<Student> students = new ArrayList<>();
        students.add(student);
        when(studentRepository.findByNumber(any())).thenReturn(students);

        // when
        studentService.save(student);
    }

    @Test
    public void should_be_ok_after_deleted()
    {

        String studentNumber = StudentUtils.createStudent().getNumber();
        studentService.delete(studentNumber);

        verify(studentRepository).deleteByNumber(studentNumber);

    }

    @Test
    public void should_return_student_when_existed()
    {
        // given
        Student student = StudentUtils.createStudent();
        List<Student> students = new ArrayList<>();
        students.add(student);
        when(studentRepository.findByNumber(student.getNumber())).thenReturn(students);

        // when
        List<Student> returnedStudents =  studentService.query(student.getNumber());

        // then
        assertThat(returnedStudents.size(),is(1));
        assertThat(returnedStudents.get(0).getFirst_name(),is(student.getFirst_name()));
        assertThat(returnedStudents.get(0).getGender(),is(student.getGender()));
        assertThat(returnedStudents.get(0).getLast_name(),is(student.getLast_name()));
    }

    @Test
    public void should_update_student()
    {
        // given
        Student targetStudent = StudentUtils.createStudent();

        Student updatedStudent = StudentUtils.createStudent();
        updatedStudent.setGender("MALE");


        List<Student> students = new ArrayList<>();
        students.add(updatedStudent);
        when(studentRepository.findByNumber(targetStudent.getNumber())).thenReturn(students);
        when(studentRepository.save(updatedStudent)).thenReturn(updatedStudent);

        // when
        String result = studentService.update(targetStudent).getGender();

        // then
        assertThat(result, is(updatedStudent.getGender()));

    }
}
