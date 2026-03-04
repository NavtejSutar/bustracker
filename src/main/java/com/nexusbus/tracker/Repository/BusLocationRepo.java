package com.nexusbus.tracker.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nexusbus.tracker.Entities.BusLocation;

public interface BusLocationRepo extends JpaRepository<BusLocation , Integer> {
    
}
