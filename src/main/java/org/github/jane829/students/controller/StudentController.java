package org.github.jane829.students.controller;

import org.github.jane829.students.domain.Student;
import org.github.jane829.students.service.StudentService;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/student")
public class StudentController
{
    private StudentService studentService;

    @Inject
    public StudentController(StudentService studentService)
    {
        this.studentService = studentService;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Student save(@RequestBody @Valid Student student)
    {
        return studentService.save(student);
    }

    @RequestMapping(value = "/query/{number}", method = RequestMethod.GET)
    public Student query(@PathVariable("number") String number)
    {
        return studentService.query(number).get(0);
    }

    @RequestMapping(value = "/delete/{number}", method = RequestMethod.GET)
    public Student delete(@PathVariable("number") String number)
    {
        return studentService.delete(number);
    }

    @RequestMapping(value = "/update/{student}", method = RequestMethod.GET)
    public Student update(@PathVariable("student") String student)
    {
        return studentService.update(student);
    }

    @RequestMapping(value = "/student/queryByFirstName/{firstName}")
    public List<Student> queryWithSameFirstName(@PathVariable("firstName") String firstName)
    {
        List<Student> students = studentService.query(firstName);
        return students;
    }
}
