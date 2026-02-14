package com.nexusbus.tracker.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nexusbus.tracker.Entities.Route;
import com.nexusbus.tracker.Entities.RouteStop;

public interface RouteStopRepo extends JpaRepository<RouteStop, Integer>{
    boolean existsByRoute_RouteIdAndBusStop_StopId(Integer routeId,Integer stopId);
    List<RouteStop> findByRoute_RouteId(Integer routeId);

    @Query("""
            SELECT rs.route
            FROM RouteStop rs
            WHERE rs.busStop.stopName = :source
                OR rs.busStop.stopName = :destination
            GROUP BY rs.route
            HAVING COUNT(DISTINCT rs.busStop.stopName)=2
            """)
    List<Route> findBySourceAndDestination(
        @Param("source") String source,
        @Param("destination") String destination);
}
