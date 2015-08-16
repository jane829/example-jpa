package org.github.jane829.students.controller;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.github.jane829.students.Application;
import org.github.jane829.students.domain.Student;
import org.github.jane829.students.repository.StudentRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.core.Is.is;

@DatabaseSetup("students.xml")
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@IntegrationTest("server.port:0")

public class StudentsRestAssuredIntegrationTest
{
    private static final String NUMBER_FIELD = "number";
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String DOMAIN = "/students";

    @Autowired
    private StudentRepository studentRepository;

    @Mock
    private WebApplicationContext application;
    private MockMvc mockMvc;
    @Value("${local.server.port}")
    private int serverPort;
    private Student exampleStudentA;
    private Student exampleStudentB;

    @Before
    public void setUp() throws Exception
    {
        RestAssured.port = serverPort;

        studentRepository.deleteAll();
        exampleStudentA = StudentUtils.createExampleStudentA();
        exampleStudentB = StudentUtils.createExampleStudentB();

        studentRepository.save(exampleStudentA);
        studentRepository.save(exampleStudentB);
    }


    @Test
    public void should_save_students_when_post()
    {
        given()
                .body(StudentUtils.createSecondStudent())
                .contentType(ContentType.JSON)
                .when()
                .post(DOMAIN)
                .then().statusCode(HttpStatus.SC_OK)

                .body("firstName", is("mei"))
                .body(NUMBER_FIELD, is("1234568"));
    }

    @Test
    public void should_not_save_student_when_already_existed()
    {

        given()
                .body(exampleStudentA)
                .contentType(ContentType.JSON)
                .when()
                .post(DOMAIN)
                .then().
                statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }

    @Test
    public void should_return_students_when_get() throws Exception
    {
        when().get(DOMAIN + "/" + exampleStudentA.getNumber())
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body(FIRST_NAME, is(exampleStudentA.getFirstName()))
                .body(NUMBER_FIELD, is(exampleStudentA.getNumber()));


        when().get(DOMAIN)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body(FIRST_NAME, hasItems(exampleStudentA.getFirstName(), exampleStudentB.getFirstName()))
                .body(LAST_NAME, hasItems(exampleStudentA.getLastName(), exampleStudentB.getLastName()))
                .body(NUMBER_FIELD, hasItems(exampleStudentA.getNumber(), exampleStudentB.getNumber()));

    }


    @Test
    public void should_update__student_when_update()
    {
        exampleStudentA.setFirstName("haha");
        given()
                .body(exampleStudentA)
                .contentType(ContentType.JSON)
                .when()
                .put(DOMAIN)
                .then()
                .body(FIRST_NAME, is("haha"));
    }


    @Test
    public void should_delete_student_when_delete()
    {
        when()
                .delete(DOMAIN + "/" + exampleStudentA.getNumber())
                .then()
                .statusCode(HttpStatus.SC_OK);

    }
}
