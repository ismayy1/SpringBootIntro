package com.tpe.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tpe.domain.Student;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO {

    @Setter(AccessLevel.NONE)   // setter is optional, we can keep/remove it if we want to
    private Long id;

    @NotNull(message = "First name can't be NULL.")
    @NotBlank(message = "First name can't be Empty.")
    @Size(min = 2, max = 25, message = "First name must be between {min}-{max} characters")
    private String name;

    private String lastName;

    private Integer grade;

    @Email(message = "Please enter a valid email address")
    private String email;

    private String phoneNumber;

    @Setter(AccessLevel.NONE)
    private LocalDateTime createDate = LocalDateTime.now();

    public StudentDTO(Student student) {
        this.id = student.getId();
        this.name = student.getFirstName();
        this.lastName = student.getLastName();
        this.grade = student.getGrade();
        this.phoneNumber = student.getPhoneNumber();
        this.email = student.getEmail();
        this.createDate = student.getCreateDate();
    }
}
