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
    @Column(name = "id", nullable = false)
    private int id;
    private String number;
    private String firstName;
    private String lastName;
    private String gender;


    public Student()
    {
    }

    public Student(String number, String firstName, String lastName, String gender)
    {
        this.number = number;
        this.firstName = firstName;
        this.lastName = lastName;
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

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public void setNumber(String number)
    {
        this.number = number;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }


    public int getId()
    {
        return id;
    }

    public String getNumber()
    {
        return number;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public String getGender()
    {
        return gender;
    }


}
