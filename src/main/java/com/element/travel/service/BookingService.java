package com.element.travel.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.element.travel.dto.BookingDto;
import com.element.travel.exception.BadRequestException;
import com.element.travel.exception.ResouceNotFoundException;
import com.element.travel.model.Booking;
import com.element.travel.model.Hiker;
import com.element.travel.model.Trail;
import com.element.travel.repository.BookingRepository;
import com.element.travel.repository.TrailRepository;


@Service
@Transactional
public class BookingService {
	private static final Logger LOGGER = LoggerFactory.getLogger(BookingService.class);
	
	private final TrailRepository trailRepository;

    @Autowired
    public BookingService(TrailRepository trailRepository) {
        this.trailRepository = trailRepository;
    }

	@Autowired
    private BookingRepository bookingRepository;

	public Booking createBooking(BookingDto bookingDto) {
		LOGGER.debug("Booking a trail {} for hikers {}", bookingDto.getTrailId(), bookingDto.getHikers());
		Booking booking = new Booking();
		//set bookingId
		String bookingId = UUID.randomUUID().toString();
		booking.setBookingId(bookingId);
		//set trail
		Optional<Trail> trailOptional = trailRepository.findById(bookingDto.getTrailId());
		if(!trailOptional.isPresent()) {
			LOGGER.info("Trail not found for this id : " + bookingDto.getTrailId());
			throw new ResouceNotFoundException("Trail not found for this id : " + bookingDto.getTrailId());
		}
		
		Trail trail = trailOptional.get();
		booking.setTrail(trail);
		
		//check validation
		int count = (int) bookingDto.getHikers().stream().filter(hiker-> hiker.getAge() > trail.getMaximumAge() || hiker.getAge() < trail.getMinimumAge()).count();
		if(count > 0) {
			LOGGER.info("Hiker age does not support for this trail {}", booking.getTrail());
			throw new BadRequestException("Hiker age does not support for this trail");
		}
		
		//set Hikers	
		List<Hiker> hikers = bookingDto.getHikers().stream().map(hikerDto-> {
			Hiker newHiker = new Hiker();
			newHiker.setName(hikerDto.getName());
			newHiker.setEmail(hikerDto.getEmail());
			newHiker.setAge(hikerDto.getAge());
			newHiker.setBooking(booking);
			return newHiker;
	    }).collect(Collectors.toList());
		
		booking.setHikers(hikers);
		booking.setTotalPrice(hikers.size() * trail.getUnitPrice());
		
		return bookingRepository.save(booking);
	}
	
	public Optional<Booking> findByBookingId(String bookingId) {
		Optional<Booking> booking = bookingRepository.findByBookingId(bookingId);
		
		if(!booking.isPresent()) {
			LOGGER.info("Booking not found for this id : " + bookingId);
			throw new ResouceNotFoundException("Booking not found for this id : " + bookingId);
		}
		
		return booking;
	}

	//This is hard delete, can do with a soft delete following business requirement
    public void deleteById(Long id) {
    	bookingRepository.deleteById(id);
    }

}
