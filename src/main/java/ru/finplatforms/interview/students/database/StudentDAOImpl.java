package ru.finplatforms.interview.students.database;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;

@Repository
@Transactional
@AllArgsConstructor
public class StudentDAOImpl implements StudentDAO {

    private final StudentRepository studentRepository;
    private final StudentEntityMapper studentEntityMapper;
}
