package com.element.travel.dto;

import javax.validation.constraints.Email;

import lombok.Data;

@Data
public class HikerDto {
    
    private String name;
    
    @Email(message="{errors.invalid_email}")
    private String email;
    
    private int age;
    
}
