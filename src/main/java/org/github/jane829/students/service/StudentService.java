package org.github.jane829.students.service;

import org.github.jane829.students.domain.Student;
import org.github.jane829.students.repository.StudentRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class StudentService
{
    private StudentRepository studentRepository;

    @Inject
    public StudentService(StudentRepository studentRepository)
    {
        this.studentRepository = studentRepository;
    }

    public Student save(Student student) throws Exception
    {
        Student stu = studentRepository.findByNumber(student.getNumber());

        if (stu != null) {
            System.out.println("Existed!");
            student.setNumber("-1");
            return student;
        }
        return studentRepository.save(student);
    }

    public List<Student> query(String number)
    {
        return null;
    }

    public Student delete(String number)
    {
        return studentRepository.deleteByNumber(number);
    }

    public Student update(Student student)
    {
        Student stu = null;
        return studentRepository.save(stu);
    }
}
