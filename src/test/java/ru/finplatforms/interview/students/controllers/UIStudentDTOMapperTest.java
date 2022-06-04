package ru.finplatforms.interview.students.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ru.finplatforms.interview.students.domain.Student;

class UIStudentDTOMapperTest {

    UIStudentDTOMapper underTest;

    @BeforeEach
    void setUp() {
        underTest = UIStudentDTOMapper.INSTANCE;
    }

    @Test
    void student_dto_to_student_conversion() {
        //given
        UIStudentDTO studentDTO = UIStudentDTO.builder()
                .id(2L)
                .firstName("Ivan")
                .middleName("Ivanovich")
                .lastName("Ivanov")
                .birthDate(LocalDate.of(2022, 6, 4))
                .group("1A")
                .build();

        //when
        Student student = underTest.uiStudentDtoToStudent(studentDTO);

        //then
        assertThat(student).isNotNull()
                .extracting("id", "firstName", "middleName", "lastName", "birthDate", "group")
                .contains(2L, "Ivan", "Ivanovich", "Ivanov", LocalDate.of(2022, 6, 4), "1A");
    }

    @Test
    void student_to_student_entity_conversion() {
        Student student = Student.builder()
                .id(2L)
                .firstName("Ivan")
                .middleName("Ivanovich")
                .lastName("Ivanov")
                .birthDate(LocalDate.of(2022, 6, 4))
                .group("1A")
                .build();

        //when
        UIStudentDTO studentDTO = underTest.studentToUiStudentDto(student);

        //then
        assertThat(studentDTO).isNotNull()
                .extracting("id", "firstName", "middleName", "lastName", "birthDate", "group")
                .contains(2L, "Ivan", "Ivanovich", "Ivanov", LocalDate.of(2022, 6, 4), "1A");
    }
}