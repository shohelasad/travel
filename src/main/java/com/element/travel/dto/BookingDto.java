package com.element.travel.dto;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;

@Data
public class BookingDto {

    private long trailId;

	private Set<HikerDto> hikers = new HashSet<>();
    
}
