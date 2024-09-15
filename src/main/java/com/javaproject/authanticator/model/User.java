package com.javaproject.authanticator.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Size(min = 3, max = 50)
    private String name;

    @Email(message = "E-mail not valid")
    @NotEmpty(message = "E-mail must not be empty")
    private String email;

    @Size(min = 5, max = 20)
    private String password;




}