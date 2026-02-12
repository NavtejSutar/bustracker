package com.nexusbus.tracker.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nexusbus.tracker.Entities.RouteStop;

public interface RouteStopRepo extends JpaRepository<RouteStop, Integer>{
    
}
