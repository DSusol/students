package ru.finplatforms.interview.students.database;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import ru.finplatforms.interview.students.domain.Student;

@Repository
@Transactional
@AllArgsConstructor
class StudentDAOImpl implements StudentDAO {

    private final StudentRepository repository;
    private final StudentEntityMapper entityMapper;

    @Override
    public List<Student> findAllStudents() {
        List<StudentEntity> studentEntities = repository.findAll();
        return studentEntities.stream()
                .map(entityMapper::studentEntityToStudent)
                .collect(Collectors.toList());
    }
}