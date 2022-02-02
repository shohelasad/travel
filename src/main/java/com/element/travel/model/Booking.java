package com.element.travel.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Booking {

	@Id
    @GeneratedValue
    private long id;
    
    private String bookingId;
    
    @NotNull
    @ManyToOne
    private Trail trail;
    
    private double totalPrice;
	
	@JsonManagedReference
	@OneToMany(mappedBy="booking", cascade=CascadeType.ALL)
	private List<Hiker> hikers = new ArrayList<>();
	
    
	public Booking(long id, String bookingId, Trail trail, List<Hiker> hikers, double totalPrice) {
		this.id = id;
		this.bookingId = bookingId;
		this.trail = trail;
		this.hikers = hikers;
		this.totalPrice = totalPrice;
	}

	@Override
	public String toString() {
		return "Booking [bookingId=" + bookingId + ", trail=" + trail + ", totalPrice="
				+ totalPrice + "]";
	}
	
}
