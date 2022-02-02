package com.element.travel.rest;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.element.travel.dto.BookingDto;
import com.element.travel.model.Booking;
import com.element.travel.service.BookingService;


@RestController
@RequestMapping("/api/v1/booking")
public class BookingController {
	private static final Logger LOGGER = LoggerFactory.getLogger(BookingService.class);
	
	private final BookingService bookingService;

	@Autowired
	public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable(value = "id") String id) {
    	LOGGER.info("Fetching a booing with booking Id : " + id);
        return bookingService.findByBookingId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Booking createBooking(@RequestBody @Valid BookingDto booking) {
    	LOGGER.info("Creating a booing with {} : " + booking);
        return bookingService.createBooking(booking);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Booking> deleteBooking(@PathVariable(value = "id") String id) {
    	LOGGER.info("Deleing a booing with booking Id : " + id);
        return bookingService.findByBookingId(id)
                .map(booking -> {
                	bookingService.deleteById(booking.getId());
                    return ResponseEntity.ok(booking);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
}
