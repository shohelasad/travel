package com.element.travel;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.element.travel.model.Trail;
import com.element.travel.repository.TrailRepository;
import com.element.travel.service.TrailService;


@ExtendWith(MockitoExtension.class)
public class TrailServiceTest {
	
	@InjectMocks
	private TrailService trailService;

    @Mock
    private TrailRepository trailRepository;


    @Test
    void shouldReturnFindAll() {
    	List<Trail> trails = new ArrayList<>();
    	trails.add(new Trail(1L, "Shire", "07:00", "09:00", 5, 100, 29.90)); 
    	trails.add(new Trail(1L, "Gondor", "09:00", "14:00", 15, 50, 49.90)); 
    	trails.add(new Trail(1L, "Mordor", "014:00", "19:00", 25, 40, 99.90)); 

        given(trailRepository.findAll()).willReturn(trails);

        List<Trail> expected = trailService.findAllTrails();

        assertEquals(expected, trails);
    }

    @Test
    void ShouldReturnById(){
        final Long id = 1L; 
        Trail trail = new Trail(1L, "Shire", "07:00", "09:00", 5, 100, 29.90); 

        given(trailRepository.findById(id)).willReturn(Optional.of(trail));

        final Optional<Trail> expected = trailService.findTrailById(id);

        assertThat(expected).isNotNull();
    }
}