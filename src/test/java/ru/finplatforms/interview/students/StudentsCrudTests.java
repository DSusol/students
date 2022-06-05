package ru.finplatforms.interview.students;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.servlet.ModelAndView;

import ru.finplatforms.interview.students.controllers.UIStudentDTO;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StudentsCrudTests {

    @Autowired
    MockMvc mockMvc;

    @Test
    @Order(1)
    void when_start_application_then_have_dummy_student_list_loaded() throws Exception {
        List<UIStudentDTO> studentList = getSavedStudentList();
        assertThat(studentList.size()).isNotEqualTo(0);
    }

    @Test
    @Order(2)
    void when_delete_all_students_then_get_empty_saved_student_list() throws Exception {
        //given
        List<UIStudentDTO> studentList = getSavedStudentList();

        //when
        for (UIStudentDTO student : studentList) {
            mockMvc.perform(get("/students/" + student.getId() + "/delete"));
        }

        //then
        studentList = getSavedStudentList();
        assertThat(studentList.size()).isEqualTo(0);
    }

    @Test
    @Order(3)
    void when_saving_new_student_then_confirm_it_is_successfully_saved() throws Exception {
        //when
        mockMvc.perform(post("/students/new")
                .param("firstName", "TestName"));

        //then
        List<UIStudentDTO> studentList = getSavedStudentList();
        assertThat(studentList.size()).isEqualTo(1);
        assertThat(studentList.get(0).getFirstName()).isEqualTo("TestName");
    }

    @Test
    @Order(4)
    void when_updating_student_then_confirm_it_is_successfully_update() throws Exception {
        //given
        List<UIStudentDTO> studentList = getSavedStudentList();
        Long givenStudentId = studentList.get(0).getId();

        //when
        mockMvc.perform(post("/students/" + givenStudentId + "/update")
                .param("id", String.valueOf(givenStudentId))
                .param("firstName", "AnotherTestName"));

        //then
        studentList = getSavedStudentList();
        assertThat(studentList.size()).isEqualTo(1);
        assertThat(studentList.get(0).getFirstName()).isEqualTo("AnotherTestName");
    }

    private List<UIStudentDTO> getSavedStudentList() throws Exception {
        MvcResult result = mockMvc.perform(get("/")).andReturn();
        ModelAndView modelAndView = result.getModelAndView();
        assertThat(modelAndView).isNotNull();

        List<?> studentList = (List<?>) modelAndView.getModel().get("students");
        return studentList.stream()
                .map(student -> (UIStudentDTO) student)
                .collect(Collectors.toList());
    }
}