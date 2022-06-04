package ru.finplatforms.interview.students.database;

import java.util.List;

import ru.finplatforms.interview.students.domain.Student;

public interface StudentDAO {

    List<Student> findAllStudents();

    Student findStudentById(Long id);

    Student saveStudent(Student student);
}
