package com.element.travel.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Trail  {

	@Id
    @GeneratedValue
    private long id;
    
    @NotNull
    @Column(nullable=false, unique=true)
    private String name;
    
    private String startAt;
    
    private String endAt;
    
    private int minimumAge;
    
    private int maximumAge;
    
    private double unitPrice;
    

	@Override
	public String toString() {
		return "Trail [name=" + name + ", startAt=" + startAt + ", endAt=" + endAt + ", minimumAge=" + minimumAge
				+ ", maximumAge=" + maximumAge + ", unitPrice=" + unitPrice + "]";
	}
    
    

}
