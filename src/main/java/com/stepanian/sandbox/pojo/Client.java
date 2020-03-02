package com.stepanian.sandbox.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Client {

    private Integer id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
}
