package com.tpe.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    private Long id;

    @NotNull(message = "First name can't be NULL.")
    @NotBlank(message = "First name can't be Empty.")
    @Size(min = 2, max = 25, message = "First name must be between {min}-{max} characters")
    private String firstName;

    @Column(nullable = false, length = 25)
    private String lastName;

    private Integer grade;

    @Column(nullable = false, length = 50, unique = true)
    @Email(message = "Please enter a valid email address")
    private String email;

    private String phoneNumber;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd//yyyy HH:mm:ss", timezone = "New York")
    @Setter(AccessLevel.NONE)
    private LocalDateTime createDate = LocalDateTime.now();
}
