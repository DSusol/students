package ru.finplatforms.interview.students.services;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.finplatforms.interview.students.database.StudentDAO;
import ru.finplatforms.interview.students.domain.Student;

@Service
@RequiredArgsConstructor
class StudentServiceImpl implements StudentService {

    private final StudentDAO studentDAO;

    @Override
    public List<Student> findAllStudents() {
        return studentDAO.findAllStudents();
    }

    @Override
    public Student findStudentById(Long id) {
        return studentDAO.findStudentById(id);
    }

    @Override
    public Student saveStudent(Student student) {
        return studentDAO.saveStudent(student);
    }
}
