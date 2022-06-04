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

    @Override
    public Student findStudentById(Long id) {
        return entityMapper.studentEntityToStudent(
                repository
                        .findById(id)
                        //todo: incorporate exception handler
                        .orElseThrow(() -> new IllegalArgumentException("no Student was found for id=" + id)));
    }

    @Override
    public Student saveStudent(Student student) {
        StudentEntity savedStudentEntity = repository.save(entityMapper.studentToStudentEntity(student));
        return entityMapper.studentEntityToStudent(savedStudentEntity);
    }
}