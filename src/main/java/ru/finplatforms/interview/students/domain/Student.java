package ru.finplatforms.interview.students.domain;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private LocalDate birthDate;
    private String group;
}
