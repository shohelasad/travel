package com.element.travel;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

import com.element.travel.model.Trail;
import com.element.travel.repository.TrailRepository;

@ComponentScan("com")
@DataJpaTest
public class TrailRepositoryTest {
	
	@Autowired
    private TrailRepository trailRepository;
	
	List<Trail> trails;
	
	@BeforeEach
   	public void setUp() {
		trails = new ArrayList<>();
    	trails.add(new Trail(1L, "Shire", "07:00", "09:00", 5, 100, 29.90)); 
    	trails.add(new Trail(2L, "Gondor", "09:00", "14:00", 15, 50, 49.90)); 
    	trails.add(new Trail(3L, "Mordor", "014:00", "19:00", 25, 40, 99.90)); 
    	trails = trailRepository.saveAll(trails);
   	}
	

    @Test
    public void shouldReturnFindAll() {
        List<Trail> expected = trailRepository.findAll();
        assertEquals(expected, trails);
    }

    @Test
    public void ShouldReturnById(){
        final Long id = 1L; 
        final Optional<Trail> expected = trailRepository.findById(id);
        assertThat(expected).isNotNull();
    }
}