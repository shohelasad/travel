package com.element.travel;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.element.travel.model.Trail;
import com.element.travel.service.TrailService;


@SpringBootApplication
public class TravelMainApplication {
	private static final Logger LOGGER = LoggerFactory.getLogger(TravelMainApplication.class);
	
	@Autowired 
    private TrailService trailService; 

	public static void main(String[] args) {
		SpringApplication.run(TravelMainApplication.class, args);
	}
	
	@PostConstruct
    public void runAfterStartup() {
		LOGGER.info("Initilize Trail data...");
		
		List<Trail> trails = new ArrayList<>();
        Trail trail = new Trail(); 
        trail.setName("Shire"); 
        trail.setStartAt("07:00");
        trail.setEndAt("09:00");
        trail.setMinimumAge(0);
        trail.setMaximumAge(100);
        trail.setUnitPrice(29.90);
        trails.add(trail);
        
        trail = new Trail(); 
        trail.setName("Gondor"); 
        trail.setStartAt("07:00");
        trail.setEndAt("09:00");
        trail.setMinimumAge(5);
        trail.setMaximumAge(100);
        trail.setUnitPrice(59.90);
        trails.add(trail);

        trail = new Trail(); 
        trail.setName("Mordor"); 
        trail.setStartAt("14:00");
        trail.setEndAt("19:00");
        trail.setMinimumAge(18);
        trail.setMaximumAge(40);
        trail.setUnitPrice(99.90);
        trails.add(trail);
        
        this.trailService.saveAll(trails);  
        
        LOGGER.info("Trail data is ready.");
    }

}
