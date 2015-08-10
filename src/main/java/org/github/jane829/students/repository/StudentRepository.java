package org.github.jane829.students.repository;

import org.github.jane829.students.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public abstract class StudentRepository implements JpaRepository<Student, Integer>
{
    public abstract List<Student> findByNumber(String number);

    public abstract void deleteByNumber(String number);
}
