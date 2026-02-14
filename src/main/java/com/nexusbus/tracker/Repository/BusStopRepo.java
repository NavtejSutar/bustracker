package com.nexusbus.tracker.Repository;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nexusbus.tracker.Entities.BusStop;

public interface BusStopRepo extends JpaRepository<BusStop, Integer>{
    Optional<BusStop> findByStopName(String stopName);
    List<BusStop> findByStopNameContaining(String stopName);
}
