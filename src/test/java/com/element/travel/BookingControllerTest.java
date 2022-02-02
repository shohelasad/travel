package com.element.travel;


import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.element.travel.dto.BookingDto;
import com.element.travel.dto.HikerDto;
import com.element.travel.exception.ResouceNotFoundException;
import com.element.travel.model.Booking;
import com.element.travel.model.Hiker;
import com.element.travel.model.Trail;
import com.element.travel.rest.BookingController;
import com.element.travel.service.BookingService;
import com.element.travel.service.TrailService;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;


@ActiveProfiles("test")
@WebMvcTest(BookingController.class)
public class BookingControllerTest {
    private String URL = "/api/v1/booking";
    
    @Autowired
	private MockMvc mvc;

    @MockBean
    private BookingService bookingService;
    
    @MockBean
	private TrailService trailService;
	

	private Trail trail;
	private Booking booking;
	
	@BeforeEach
    void setUp() {
    	this.trail = new Trail(1L, "Shire", "07:00", "09:00", 5, 100, 29.90); 
    	
      	final String bookingId = "booking1001";
    	booking = new Booking();
        Trail trail = new Trail(); 
        trail.setName("Shire"); 
        trail.setStartAt("07:00");
        trail.setEndAt("09:00");
        trail.setMaximumAge(5);
        trail.setMaximumAge(100);
        trail.setUnitPrice(29.90);

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
    }
	
	@Test
	public void shouldFetchOneById() throws Exception {
		final String bookingId = "booking1001";
		List<Hiker> hikers = new ArrayList<>();
        Hiker hiker = new Hiker(); 
        hiker.setName("test"); 
        hiker.setEmail("test@email.com");
        hikers.add(hiker);
		Booking booking = new Booking(1L, bookingId, trail, hikers, hikers.size() * this.trail.getUnitPrice());

		given(bookingService.findByBookingId(bookingId)).willReturn(Optional.of((booking)));

		this.mvc.perform(get(URL + "/{id}", bookingId)
			.contentType(MediaType.APPLICATION_JSON)).
			 andExpect(status().isOk())
			.andExpect(jsonPath("bookingId", is(booking.getBookingId())));
	}
	
	@Test
    void shouldReturn404WhenFindById() throws Exception {
		final String bookingId = "booking1001";
        given(bookingService.findByBookingId(bookingId)).willReturn(Optional.empty());
        
        this.mvc.perform(get(URL + "/{id}", bookingId))
                .andExpect(status().isNotFound());
    }
    
    
    @Test
    public void shouldDeleteBookingWhenExist() throws Exception {
    	final String bookingId = "booking1001";
    	Booking booking = new Booking();
        Trail trail = new Trail(); 
        trail.setName("Shire"); 
        trail.setStartAt("07:00");
        trail.setEndAt("09:00");
        trail.setMaximumAge(5);
        trail.setMaximumAge(100);
        trail.setUnitPrice(29.90);

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
        
        given(bookingService.findByBookingId(bookingId)).willReturn(Optional.of(booking));
        doNothing().when(bookingService).deleteById(booking.getId());

        this.mvc.perform(delete(URL + "/{bookingId}", bookingId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookingId", is(booking.getBookingId())));

    }

    @Test
    public void shouldReturn404WhenNotExist() throws Exception {
        String bookingId = "bookingId";
        given(bookingService.findByBookingId(bookingId))
    	.willThrow(new ResouceNotFoundException("Booking not found for this id : " + bookingId));

        this.mvc.perform(delete(URL + "/{bookingId}", bookingId))
                .andExpect(status().isNotFound());

    }


    @Test
    public void ShoudCreateANewBooking() throws Exception {
    	BookingDto bookingDto = new BookingDto();
        Trail trail = new Trail(); 
        trail.setName("Shire"); 
        trail.setStartAt("07:00");
        trail.setEndAt("09:00");
        trail.setMaximumAge(5);
        trail.setMaximumAge(100);
        trail.setUnitPrice(29.90);

		Set<HikerDto> hikers = new HashSet<>();
        HikerDto hiker = new HikerDto(); 
        hiker.setName("test"); 
        hiker.setEmail("test@email.com");
        hikers.add(hiker);

        bookingDto.setTrailId(trail.getId());
        bookingDto.setHikers(hikers);  
        
        given(bookingService.createBooking(bookingDto)).willReturn(booking);

        mvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(booking)))
                .andExpect(status().isCreated());

    }
    
    private static byte[] toJson(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }

}