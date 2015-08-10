package org.github.jane829.students.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Student
{
    @Id
    @GeneratedValue
    @Column(name = "id",nullable = false)
    private int id;
    private String number;
    private String first_name;
    private String last_name;
    private String gender;


    public Student()
    {
    }

    public Student(String number, String first_name, String last_name, String gender)
    {
        this.number = number;
        this.first_name = first_name;
        this.last_name = last_name;
        this.gender = gender;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public void setGender(String gender)
    {
        this.gender = gender;
    }

    public void setLast_name(String last_name)
    {
        this.last_name = last_name;
    }

    public void setNumber(String number)
    {
        this.number = number;
    }

    public void setFirst_name(String first_name)
    {
        this.first_name = first_name;
    }


    public int getId()
    {
        return id;
    }

    public String getNumber()
    {
        return number;
    }

    public String getFirst_name()
    {
        return first_name;
    }

    public String getLast_name()
    {
        return last_name;
    }

    public String getGender()
    {
        return gender;
    }


}
