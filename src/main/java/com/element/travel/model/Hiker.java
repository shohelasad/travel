package com.element.travel.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Email;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Hiker  {

    @Id
    @GeneratedValue
    private long id;
    
    private String name;
    
    @Email(message="{errors.invalid_email}")
    private String email;
    
    private int age;
    
    @JsonBackReference
    @ManyToOne (cascade=CascadeType.ALL)
	private Booking booking;

	@Override
	public String toString() {
		return "Hiker [name=" + name + ", email=" + email + ", age=" + age + "]";
	}
    
    
    
}
