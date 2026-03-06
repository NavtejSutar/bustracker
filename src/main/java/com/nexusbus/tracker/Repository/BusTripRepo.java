package com.nexusbus.tracker.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nexusbus.tracker.Entities.BusTrip;
import com.nexusbus.tracker.Entities.Users;

public interface BusTripRepo extends JpaRepository<BusTrip , Integer>{
    Boolean existsByUsersAndActiveTrue(Users users);
    Optional<BusTrip> findByUsersAndActiveTrue(Users users);
}
