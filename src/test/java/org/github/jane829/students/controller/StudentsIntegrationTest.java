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

public class StudentsIntegrationTest
{
    private static final String NUMBER_FIELD = "number";
    private static final String ITEMS_STUDENTS = "/students";
    private static final String SAVE_STUDENT = "/students";
    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";
    private static final String ITEM_UPDATE = "/students";
    private static final String ITEM_DELETE = "/students";
    @Autowired
    private StudentRepository studentRepository;

    @Mock
    private WebApplicationContext application;
    private MockMvc mockMvc;
    @Value("${local.server.port}")
    private int serverPort;

    @Before
    public void setUp() throws Exception
    {
        RestAssured.port = serverPort;
        studentRepository.deleteAll();
    }


    @Test
    public void should_save_students_when_post()
    {
        given()
                .body(StudentUtils.createSecondStudent())
                .contentType(ContentType.JSON)
                .when()
                .post(SAVE_STUDENT)
                .then().statusCode(HttpStatus.SC_OK)

                .body("first_name", is("mei"))
                .body(NUMBER_FIELD, is("1234568"));
    }

    @Test
    public void should_not_save_student_when_already_existed()
    {
        given()
                .body(StudentUtils.createSecondStudent())
                .contentType(ContentType.JSON)
                .when()
                .post(SAVE_STUDENT)
                .then().
                statusCode(HttpStatus.SC_OK);

        given()
                .body(StudentUtils.createSecondStudent())
                .contentType(ContentType.JSON)
                .when()
                .post(SAVE_STUDENT)
                .then().
                statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }

    @Test
    public void should_return_students_when_get() throws Exception
    {
        given()
                .body(StudentUtils.createStudent())
                .contentType(ContentType.JSON)
                .when()
                .post(SAVE_STUDENT)
                .then().
                statusCode(HttpStatus.SC_OK);
        given()
                .body(StudentUtils.createSecondStudent())
                .contentType(ContentType.JSON)
                .when()
                .post(SAVE_STUDENT)
                .then().
                statusCode(HttpStatus.SC_OK);


        when().get(ITEMS_STUDENTS + "/1234567")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body(FIRST_NAME, is("mei"))
                .body(NUMBER_FIELD, is("1234567"));


        when().get(ITEMS_STUDENTS)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body(FIRST_NAME, hasItems("mei", "mei"))
                .body(LAST_NAME, hasItems("lan", "han"))
                .body(NUMBER_FIELD, hasItems("1234567", "1234568"));

    }


    @Test
    public void should_update__student_when_update()
    {
        given()
                .body(StudentUtils.createSecondStudent())
                .contentType(ContentType.JSON)
                .when()
                .post(SAVE_STUDENT)
                .then().
                statusCode(HttpStatus.SC_OK);


        Student updateStudent = StudentUtils.createSecondStudent();
        updateStudent.setFirst_name("haha");
        given()
                .body(updateStudent)
                .contentType(ContentType.JSON)
                .when()
                .put(ITEM_UPDATE)
                .then()
                .body(FIRST_NAME, is("haha"));
    }


    @Test
    public void should_delete_student_when_delete()
    {
        given()
                .body(StudentUtils.createStudent())
                .contentType(ContentType.JSON)
                .when()
                .post(SAVE_STUDENT)
                .then().
                statusCode(HttpStatus.SC_OK);

        when()
                .delete(ITEM_DELETE + "/1234567")
                .then()
                .statusCode(HttpStatus.SC_OK);

    }
}
