package ru.elagin.springcourse.dto;

import lombok.Value;

import java.time.LocalDate;

@Value
public class CustomerFilter {
    Integer limit;
    Integer offset;
    Integer tabnum;
    String name;
    String surname;
    String email;
    LocalDate birth;
}
