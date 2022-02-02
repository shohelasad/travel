package com.element.travel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.element.travel.model.Trail;

@Repository
public interface TrailRepository extends JpaRepository<Trail, Long> {

}
