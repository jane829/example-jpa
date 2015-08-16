package org.github.jane829.students.service;

import org.github.jane829.students.domain.Student;
import org.github.jane829.students.exceptions.SameStudentException;
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
        List<Student> students = studentRepository.findByNumber(student.getNumber());


        if (students != null && students.size() != 0) {
            throw new SameStudentException();
        }
        return studentRepository.save(student);
    }

    public List<Student> query(String number)
    {
        return studentRepository.findByNumber(number);
    }

    public void delete(String number)
    {
        studentRepository.deleteByNumber(number);
    }

    public Student update(Student student)
    {
        Student savedStudent = studentRepository.findByNumber(student.getNumber()).get(0);

        savedStudent.setGender(student.getGender());
        savedStudent.setFirstName(student.getFirstName());
        savedStudent.setLastName(student.getLastName());

        return studentRepository.save(savedStudent);
    }

    public List<Student> queryAll()
    {
        return studentRepository.findAll();
    }
}
