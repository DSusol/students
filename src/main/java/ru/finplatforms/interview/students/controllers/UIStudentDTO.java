package ru.finplatforms.interview.students.controllers;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UIStudentDTO {

    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String group;

    @DateTimeFormat(pattern = "MM/dd/yy")
    private LocalDate birthDate;
}
