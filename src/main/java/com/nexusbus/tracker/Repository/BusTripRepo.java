package com.nexusbus.tracker.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nexusbus.tracker.Entities.BusTrip;

public interface BusTripRepo extends JpaRepository<BusTrip , Integer>{
    
}
