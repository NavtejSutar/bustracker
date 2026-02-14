package com.nexusbus.tracker.Repository;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nexusbus.tracker.Entities.Route;

public interface RouteRepo extends JpaRepository<Route, Integer>{
    Optional<Route> findByRouteName(String routeName);
    List<Route> findByRouteNameContaining(String routeName);
}
