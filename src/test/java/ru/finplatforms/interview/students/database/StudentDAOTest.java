package ru.finplatforms.interview.students.database;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

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
    void find_allS_students_method_verification() {
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

    @Test
    void find_student_by_id_method_verification() {
        //given
        StudentEntity studentEntity = new StudentEntity();
        Student student = Student.builder().firstName("Ivan").build();

        //when
        when(repository.findById(2L)).thenReturn(Optional.of(studentEntity));
        when(entityMapper.studentEntityToStudent(studentEntity)).thenReturn(student);

        Student foundStudent = underTest.findStudentById(2L);

        //then
        assertThat(foundStudent).isEqualTo(student);
        verify(repository).findById(anyLong());
        verify(entityMapper).studentEntityToStudent(any(StudentEntity.class));
    }

    @Test
    void save_student_method_verification() {
        //given
        Student sourceStudent = new Student();
        StudentEntity sourceStudentEntity = new StudentEntity();
        StudentEntity savedStudentEntity = new StudentEntity();
        Student savedStudent = Student.builder().firstName("Ivan").build();

        //when
        when(entityMapper.studentToStudentEntity(sourceStudent)).thenReturn(sourceStudentEntity);
        when(repository.save(sourceStudentEntity)).thenReturn(savedStudentEntity);
        when(entityMapper.studentEntityToStudent(savedStudentEntity)).thenReturn(savedStudent);

        Student resultStudent = underTest.saveStudent(sourceStudent);

        //then
        assertThat(resultStudent).isEqualTo(savedStudent);
        verify(entityMapper).studentToStudentEntity(any(Student.class));
        verify(repository).save(any(StudentEntity.class));
        verify(entityMapper).studentEntityToStudent(any(StudentEntity.class));
    }

    @Test
    void delete_student_by_id_verification() {
        //when
        underTest.deleteStudentById(2L);

        //then
        verify(repository).deleteById(2L);
    }
}