import com.fasterxml.jackson.databind.ObjectMapper;
import org.github.jane829.students.controller.StudentController;
import org.github.jane829.students.domain.Student;
import org.github.jane829.students.service.StudentService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class StudentsTest
{
    private MockMvc mockMvc;

    @Mock
    private StudentService studentService;
    @InjectMocks
    private StudentController studentController;
    private Student student;

    @Before
    public void setUp() throws Exception
    {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(studentController).build();

        student = StudentUtils.createStudent();
    }

    @Test
    public void should_save_student_when_not_existed() throws Exception{
        // given
        Student to_save_student = student;
        when(studentService.save(any(Student.class))).thenReturn(to_save_student);

        // when
        mockMvc.perform(post("/student/save").contentType(StudentUtils.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(to_save_student)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.number", is("1234567")))
                .andExpect(jsonPath("$.first_name", is("mei")))
                .andExpect(jsonPath("$.last_name", is("han")));
    }

    @Test
    public void should_throw_exception_when_same_student_existed() throws Exception
    {
        // given
        Student to_save_student = student;

        Student returnedStudent = student;
        returnedStudent.setNumber("-1");
        when(studentService.save(any(Student.class))).thenReturn(returnedStudent);

        // when
        mockMvc.perform(post("/student/save").contentType(StudentUtils.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(to_save_student)))
                .andExpect(jsonPath("$.number", is("-1")));

    }

    @Test
    public void should_return_student_info_when_find_one_saved() throws Exception
    {
        // given
        Student to_find_student = student;
        List<Student> returnedStudents = new ArrayList<>();
        returnedStudents.add(student);
        when(studentService.query(to_find_student.getNumber())).thenReturn(returnedStudents);

        // when
        mockMvc.perform(get("/student/query/{number}", student.getNumber()).contentType(StudentUtils.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.number", is(student.getNumber())))
                .andExpect(jsonPath("$.first_name", is(student.getFirst_name())))
                .andExpect(jsonPath("$.last_name", is(student.getLast_name())));

    }

    @Test
    public void should_return_null_after_deleted() throws Exception
    {
        // given
        Student to_delete_stu = student;
        when(studentService.delete(student.getNumber())).thenReturn(null);

        // when
        Student returnedStu = studentController.delete(to_delete_stu.getNumber());

        // then
        assertNull(returnedStu);
    }

    @Test
    public void should_update_student() throws Exception
    {
        // given
        Student to_update_stu = student;
        to_update_stu.setFirst_name("Lin");


        when(studentService.update(any())).thenReturn(to_update_stu);

        // when
        mockMvc.perform(get("/student/update/{student}", student.getNumber() + "|" + to_update_stu.getFirst_name())
                .contentType(StudentUtils.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.first_name", is(to_update_stu.getFirst_name())));
    }

    @Test
    public void should_return_all_same_name_student() throws Exception
    {
        // given
        String first_name = "Lin";
        List<Student> students = StudentUtils.getStudents();
        when(studentService.query(any())).thenReturn(students);

        mockMvc.perform(get("/student/queryByFirstName/{firstName}", first_name)
                .contentType(StudentUtils.APPLICATION_JSON_UTF8))
                .andDo(print());

        int size = studentController.queryWithSameFirstName(first_name).size();
        assertThat(size, is(5));
    }
}
