package ru.finplatforms.interview.students.controllers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import ru.finplatforms.interview.students.domain.Student;

@Mapper(componentModel = "spring")
interface UIStudentDTOMapper {

    UIStudentDTOMapper INSTANCE = Mappers.getMapper(UIStudentDTOMapper.class);

    Student uiStudentDtoToStudent(UIStudentDTO studentEntity);
    UIStudentDTO studentToUiStudentDto(Student student);
}