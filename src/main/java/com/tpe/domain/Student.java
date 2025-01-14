package com.tpe.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity

// Lombok annotations
@Getter // for getters
@Setter // for setters
@AllArgsConstructor // constructor for all fields
@NoArgsConstructor  // empty constructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(nullable = false, length = 25)  // Length of the Column
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

    @OneToMany(mappedBy = "student")
    private List<Book> books = new ArrayList<>();
}
