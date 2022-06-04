package ru.finplatforms.interview.students.controllers;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import ru.finplatforms.interview.students.domain.Student;
import ru.finplatforms.interview.students.services.StudentService;

class StudentControllerTest {

    @Mock
    StudentService studentService;

    @InjectMocks
    StudentController underTest;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(underTest).build();
    }

    @Test
    void studentList() throws Exception {
        //given
        List<Student> students = List.of(new Student(), new Student());

        //when
        when(studentService.findAllStudents()).thenReturn(students);

        //then
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("students", hasSize(2)))
                .andExpect(view().name("index"));

        verify(studentService).findAllStudents();
    }
}