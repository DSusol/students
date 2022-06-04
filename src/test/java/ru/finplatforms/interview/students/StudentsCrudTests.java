package ru.finplatforms.interview.students;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.servlet.ModelAndView;

import ru.finplatforms.interview.students.controllers.UIStudentDTO;

@SpringBootTest
@AutoConfigureMockMvc
class StudentsCrudTests {

    @Autowired
    MockMvc mockMvc;

    @Test
    void students_crud_operations_verification() throws Exception {

        // CHECK 1 - students are loaded to database when application starts
        List<UIStudentDTO> currentStudentList = getSavedStudentList();
        assertThat(currentStudentList.size()).isNotEqualTo(0);



        // CHECK 2 - deleting all students
        for (UIStudentDTO studentDTO : currentStudentList) {
            mockMvc.perform(get("/students/" + studentDTO.getId() + "/delete"));
        }

        currentStudentList = getSavedStudentList();
        assertThat(currentStudentList.size()).isEqualTo(0);



        // CHECK 3 - creating new student
        mockMvc.perform(post("/students/new")
                .param("firstName", "TestName"));

        currentStudentList = getSavedStudentList();
        assertThat(currentStudentList.size()).isEqualTo(1);

        UIStudentDTO savedStudent = currentStudentList.get(0);
        assertThat(savedStudent.getFirstName()).isEqualTo("TestName");



        // CHECK 4 - updating existing student
        Long savedStudentId = savedStudent.getId();
        mockMvc.perform(post("/students/" + savedStudentId + "/update")
                .param("id", String.valueOf(savedStudentId))
                .param("firstName", "AnotherTestName"));

        currentStudentList = getSavedStudentList();
        assertThat(currentStudentList.size()).isEqualTo(1);

        savedStudent = currentStudentList.get(0);
        assertThat(savedStudent.getFirstName()).isEqualTo("AnotherTestName");
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