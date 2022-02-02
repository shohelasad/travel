package com.element.travel.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.element.travel.model.Hiker;

@Repository
public interface HikerRepository extends JpaRepository<Hiker, Long> {
}
