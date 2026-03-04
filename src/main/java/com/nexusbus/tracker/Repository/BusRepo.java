package com.nexusbus.tracker.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nexusbus.tracker.Entities.Bus;

public interface BusRepo extends JpaRepository<Bus, Integer>{
    List<Bus> findByNumberPlateContaining(String numberPlate);
    Optional<Bus> findByNumberPlate(String numberPlate);
}
