package com.element.travel;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

import com.element.travel.model.Booking;
import com.element.travel.model.Hiker;
import com.element.travel.model.Trail;
import com.element.travel.repository.BookingRepository;
import com.element.travel.repository.TrailRepository;

@ComponentScan("com")
@DataJpaTest
public class BookingRepositoryTest {
	
	@Autowired
    private TrailRepository trailRepository;
	
	@Autowired
	private BookingRepository bookingRepository;
	
	private Trail trail;
	
	final String bookingId = "booking1001";
	
	@BeforeEach
   	public void setUp() {
    	trail = new Trail(1L, "Shire", "07:00", "09:00", 5, 100, 29.90); 
    	trailRepository.save(trail);
    	
    	Booking booking = new Booking();
		List<Hiker> hikers = new ArrayList<>();
        Hiker hiker = new Hiker(); 
        hiker.setBooking(booking);
        hiker.setName("test"); 
        hiker.setEmail("test@email.com");
        hikers.add(hiker);
        
        booking.setId(1L);
        booking.setBookingId(bookingId);
        //booking.setTrail(trail);
        booking.setHikers(hikers);  
        booking.setTotalPrice(hikers.size() * trail.getUnitPrice());

        booking.setTrail(trail);
        bookingRepository.save(booking);
   	}


    @Test
    void shouldCreateNewBooking() {
    	final String bookingId = "booking1002";
    	Booking booking = new Booking();
		List<Hiker> hikers = new ArrayList<>();
        Hiker hiker = new Hiker(); 
        hiker.setBooking(booking);
        hiker.setName("test"); 
        hiker.setEmail("test@email.com");
        hikers.add(hiker);
        
        booking.setId(1L);
        booking.setBookingId(bookingId);
        booking.setTrail(trail);
        booking.setHikers(hikers);  
        booking.setTotalPrice(hikers.size() * trail.getUnitPrice());

        Booking savedBooking = bookingRepository.save(booking);

        assertThat(savedBooking).isNotNull();
    }
    

    @Test
    void ShouldReturnById(){  
        final Optional<Booking> expected = bookingRepository.findByBookingId(bookingId);
        assertThat(expected).isNotNull();
    }

    @Test
    void shouldBeDelete() {   
    	Optional<Booking> booking = bookingRepository.findByBookingId(bookingId);
    	bookingRepository.deleteById(booking.get().getId());
    	
    	final Optional<Booking> expected = bookingRepository.findByBookingId(bookingId);

        assertThat(!expected.isPresent());
    }

}