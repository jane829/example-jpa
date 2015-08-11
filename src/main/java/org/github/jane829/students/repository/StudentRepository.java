package org.github.jane829.students.repository;

import org.github.jane829.students.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer>
{
    List<Student> findByNumber(String number);

    void deleteByNumber(String number);
}
