package ru.finplatforms.interview.students.database;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import ru.finplatforms.interview.students.domain.Student;

@Mapper(componentModel = "spring")
interface StudentEntityMapper {

    StudentEntityMapper INSTANCE = Mappers.getMapper(StudentEntityMapper.class);

    Student studentEntityToStudent(StudentEntity studentEntity);

    StudentEntity studentToStudentEntity(Student student);
}