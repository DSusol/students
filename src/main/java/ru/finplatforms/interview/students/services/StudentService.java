package ru.finplatforms.interview.students.services;

import java.util.List;

import ru.finplatforms.interview.students.domain.Student;

public interface StudentService {

    List<Student> findAllStudents();
}
