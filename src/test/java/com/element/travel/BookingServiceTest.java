package com.element.travel;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.element.travel.dto.BookingDto;
import com.element.travel.dto.HikerDto;
import com.element.travel.model.Booking;
import com.element.travel.model.Trail;
import com.element.travel.repository.BookingRepository;
import com.element.travel.service.BookingService;


@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {
	
	@Mock
	private BookingService bookingService;
    
    Trail trail;
    
    @BeforeEach
   	public void setUp() {
    	trail = new Trail(1L, "Shire", "07:00", "09:00", 5, 100, 29.90); ;
   	}

    @Test
    void shouldSavedBookingSuccessFullyAn() {
    	final String bookingId ="bookingId1001";
    	BookingDto bookingDto = new BookingDto();
		Set<HikerDto> hikers = new HashSet<>();
        HikerDto hiker = new HikerDto(); 
        hiker.setName("test"); 
        hiker.setEmail("test@email.com");
        hikers.add(hiker);

        bookingDto.setTrailId(trail.getId());
        bookingDto.setHikers(hikers);  

        bookingService.createBooking(bookingDto);
        Optional<Booking> booking = bookingService.findByBookingId(bookingId);

        assertThat(booking).isNotNull();
    }
    
    @Test
    void ShouldReturnById(){
    	final String bookingId ="bookingId1001";
    	BookingDto bookingDto = new BookingDto();
		Set<HikerDto> hikers = new HashSet<>();
        HikerDto hiker = new HikerDto(); 
        hiker.setName("test"); 
        hiker.setEmail("test@email.com");
        hikers.add(hiker);

        bookingDto.setTrailId(trail.getId());
        bookingDto.setHikers(hikers);  

        Optional<Booking> expected = bookingService.findByBookingId(bookingId);

        assertThat(expected).isNotNull();
    }
    
    
    @Test
    void shouldBeDelete() {
    	final Long id = 1L;
        
    	bookingService.deleteById(id);
    	bookingService.deleteById(id);
    	
        verify(bookingService, times(2)).deleteById(id);
    }

}