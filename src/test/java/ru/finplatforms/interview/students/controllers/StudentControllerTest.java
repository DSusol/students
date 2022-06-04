package ru.finplatforms.interview.students.controllers;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    UIStudentDTOMapper dtoMapper;

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
    void display_student_list_verification() throws Exception {
        //given
        List<Student> students = List.of(new Student(), new Student());

        //when
        when(studentService.findAllStudents()).thenReturn(students);
        when(dtoMapper.studentToUiStudentDto(any(Student.class))).thenReturn(new UIStudentDTO());

        //then
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("students", hasSize(2)))
                .andExpect(view().name("index"));

        verify(studentService).findAllStudents();
        verify(dtoMapper, times(2)).studentToUiStudentDto(any(Student.class));
    }

    @Test
    void new_student_form_verification() throws Exception {
        mockMvc.perform(get("/students/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("createOrUpdateForm"));
    }

    @Test
    void update_student_form_verification() throws Exception {
        //given
        Student student = new Student();
        UIStudentDTO studentDTO = new UIStudentDTO();

        //when
        when(studentService.findStudentById(2L)).thenReturn(student);
        when(dtoMapper.studentToUiStudentDto(student)).thenReturn(studentDTO);

        //then
        mockMvc.perform(get("/students/2/update"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("student", is(studentDTO)))
                .andExpect(view().name("createOrUpdateForm"));

        verify(studentService).findStudentById(anyLong());
        verify(dtoMapper).studentToUiStudentDto(any(Student.class));
    }

    @Test
    void save_student_verification() throws Exception {

        //when
        when(dtoMapper.uiStudentDtoToStudent(any(UIStudentDTO.class))).thenReturn(new Student());

        //then
        mockMvc.perform(post("/students/new"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));

        verify(dtoMapper).uiStudentDtoToStudent(any(UIStudentDTO.class));
        verify(studentService).saveStudent(any(Student.class));
    }

    @Test
    void update_student_verification() throws Exception {

        //when
        when(dtoMapper.uiStudentDtoToStudent(any(UIStudentDTO.class))).thenReturn(new Student());

        //then
        mockMvc.perform(post("/students/2/update"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));

        verify(dtoMapper).uiStudentDtoToStudent(any(UIStudentDTO.class));
        verify(studentService).saveStudent(any(Student.class));
    }

    @Test
    void delete_student_verification() throws Exception {
        mockMvc.perform(get("/students/2/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));

        verify(studentService).deleteStudentById(2L);
    }
}