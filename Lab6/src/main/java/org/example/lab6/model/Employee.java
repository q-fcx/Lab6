package org.example.lab6.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
public class Employee {

    @NotNull
    @Size(min = 2, max = 4)
    private String id;

    @NotNull
    @Size(min = 4, max = 12)
    @Pattern(regexp="^[A-Za-z]*$",message = "Invalid Input")
    private String name;

    //@Email
    private String email;

    @Pattern(regexp = "^05\\d{8}$", message = "Phone number must start with '05' and be exactly 10 digits")
    @Digits(fraction = 0, integer = 10, message = "Phone numbers consists of 10 digits only")
    private String phoneNumber;

    @NotNull
    @Pattern(regexp = "/\\s[0-1]{1}[0-9]{0,2}/")
    @Size(min = 25, max = 150)
    private int age;

    //@NotNull
    @Pattern(regexp = "^(supervisor|coordinator)$", message = "only supervisor and coordinator position are allowed")
    private String position;
    private boolean onLeave = false;

    @NotNull
    @JsonFormat(pattern = "yyyy/MM/dd")
    private String hireDate;

    @NotNull
    @Positive
    private int annualLeave;
}
