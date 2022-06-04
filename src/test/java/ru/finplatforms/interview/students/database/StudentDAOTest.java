package ru.finplatforms.interview.students.database;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ru.finplatforms.interview.students.domain.Student;

class StudentDAOTest {

    @Mock
    StudentRepository repository;

    @Mock
    StudentEntityMapper entityMapper;

    @InjectMocks
    StudentDAOImpl underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAllStudents_method_verification() {
        //given
        List<StudentEntity> studentEntities = List.of(new StudentEntity(), new StudentEntity());

        //when
        when(repository.findAll()).thenReturn(studentEntities);
        when(entityMapper.studentEntityToStudent(any(StudentEntity.class))).thenReturn(new Student());

        List<Student> students = underTest.findAllStudents();

        //then
        assertThat(students).hasSize(2);
        verify(repository).findAll();
        verify(entityMapper, times(2)).studentEntityToStudent(any(StudentEntity.class));
    }
}