package com.element.travel.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.element.travel.model.Trail;
import com.element.travel.service.TrailService;


@RestController
@RequestMapping("/api/v1/trail")
public class TrailController {
	private static final Logger LOGGER = LoggerFactory.getLogger(TrailController.class);
    
    private final TrailService trailService;
	
    @Autowired
	public TrailController(TrailService trailService) {
        this.trailService = trailService;
    }


    @GetMapping
    public List<Trail> getAllTrails() {
    	LOGGER.info("Fethcing all trails");
        return trailService.findAllTrails();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Trail> getTrailById(@PathVariable Long id) {
    	LOGGER.info("Fethcing a trail with Id: " + id);
        return trailService.findTrailById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
