package org.github.jane829.students.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.github.jane829.students.domain.Student;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class StudentUtils
{
    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    public static byte[] convertObjectToJsonBytes(Object object) throws IOException
    {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        return mapper.writeValueAsBytes(object);
    }

    public static String createStringWithLength(int length)
    {
        StringBuilder builder = new StringBuilder();

        for (int index = 0; index < length; index++) {
            builder.append("a");
        }

        return builder.toString();
    }


    public static Student createStudent()
    {
        return new Student("1234567", "mei", "han", "female");
    }

    public static List<Student> getStudents()
    {
        List<Student> students = new ArrayList<>();
        students.add(new Student("1234567", "LIN", "Han", "FEMALE"));
        students.add(new Student("1234568", "LIN", "Wang", "FEMALE"));
        students.add(new Student("1234569", "LIN", "Xie", "FEMALE"));
        students.add(new Student("1234560", "LIN", "Gao", "FEMALE"));
        students.add(new Student("1234561", "LIN", "Li", "MALE"));


        return students;
    }

    public static Student createSecondStudent()
    {
        return new Student("1234568", "mei", "lan", "female");
    }

    public static Student createExampleStudentA()
    {
        return new Student("1122335","xiong","da","MALE");
    }

    public static Student createExampleStudentB()
    {
        return new Student("1122334","hong","xiao","FEMALE");
    }
}
