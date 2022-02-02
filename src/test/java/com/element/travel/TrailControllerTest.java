package com.element.travel;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.element.travel.model.Trail;
import com.element.travel.rest.TrailController;
import com.element.travel.service.TrailService;



@ActiveProfiles("test")
@WebMvcTest(TrailController.class)
public class TrailControllerTest {

	private String URL = "/api/v1/trail";

	@Autowired
	private MockMvc mvc;

	@MockBean
	private TrailService trailService;
	
	private List<Trail> trails;
	
	@BeforeEach
    void setUp() {
        this.trails = new ArrayList<>();
    	this.trails.add(new Trail(1L, "Shire", "07:00", "09:00", 5, 100, 29.90)); 
    	this.trails.add(new Trail(1L, "Gondor", "09:00", "14:00", 15, 50, 49.90)); 
    	this.trails.add(new Trail(1L, "Mordor", "014:00", "19:00", 25, 40, 99.90)); 
    }

	@Test
	public void shouldFetchAll() throws Exception {
		given(trailService.findAllTrails()).willReturn(trails);

        this.mvc.perform(get(URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(trails.size())));
	}
	
	
	@Test
	public void shouldFetchOneById() throws Exception {
		final Long id = 1L;
		Trail trail = new Trail(1L, "Shire", "07:00", "09:00", 5, 100, 29.90);

		given(trailService.findTrailById(id)).willReturn(Optional.of((trail)));

		this.mvc.perform(get(URL + "/{id}", id)
			.contentType(MediaType.APPLICATION_JSON)).
			 andExpect(status().isOk())
			.andExpect(jsonPath("name", is(trail.getName())));
	}
	
	@Test
    void shouldReturn404WhenFindById() throws Exception {
        final Long id = 1L;
        given(trailService.findTrailById(id)).willReturn(Optional.empty());
        
        this.mvc.perform(get(URL + "/{id}", id))
                .andExpect(status().isNotFound());
    }

}