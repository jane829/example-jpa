package org.github.jane829.students.controller;

import org.github.jane829.students.domain.Student;
import org.github.jane829.students.service.StudentService;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/students")
public class StudentController
{
    private StudentService studentService;

    @Inject
    public StudentController(StudentService studentService)
    {
        this.studentService = studentService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public Student save(@RequestBody @Valid Student student) throws Exception
    {
        return studentService.save(student);
    }

    @RequestMapping(value = "/{student_number}", method = RequestMethod.GET)
    public Student query(@PathVariable("student_number") String number)
    {
        return studentService.query(number).get(0);
    }

    @RequestMapping(value = "/{student_number}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("student_number") String number)
    {
        studentService.delete(number);
    }

    @RequestMapping(value = "/{student_number}", method = RequestMethod.PUT)
    public Student update(@PathVariable("student_number") String studentNumber, @RequestBody Student student)
    {
        return studentService.update(studentNumber, student);
    }

}
