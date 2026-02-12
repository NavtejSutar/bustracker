package com.nexusbus.tracker.Controller;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nexusbus.tracker.DTO.RouteDto;
import com.nexusbus.tracker.Entities.BusStop;
import com.nexusbus.tracker.Entities.Route;
import com.nexusbus.tracker.Entities.RouteStop;
import com.nexusbus.tracker.Repository.BusStopRepo;




@RestController

public class BusStopController {

    private final BusStopRepo busStopRepo;

    public BusStopController(BusStopRepo busStopRepo){
        this.busStopRepo=busStopRepo;
    }

    @GetMapping("/stop")
    public ResponseEntity<List<BusStop>> getBusStops() {
        return new ResponseEntity<>(busStopRepo.findAll(),HttpStatus.OK);
    }
    @GetMapping("/stop/{stopId}")
    public ResponseEntity<?> getBusStop(@PathVariable Integer stopId ) {
        Optional<BusStop> busStopOptional=busStopRepo.findById(stopId);
        return busStopOptional.map(busStop->ResponseEntity.ok(busStop))
        .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/StopName/{stopName}")
    public ResponseEntity<List<RouteDto>> getRouteName(@PathVariable String stopName){
        Optional<BusStop> stopOpt=busStopRepo.findByStopName(stopName);
        if(stopOpt.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        BusStop stop=stopOpt.get();
        List<RouteStop> routeStopList=stop.getRouteStops();
        List<RouteDto> result=new ArrayList<>();
        for(RouteStop rs : routeStopList){
            Route route=rs.getRoute();
            RouteDto currRouteDto=new RouteDto(route.getRouteId(),route.getRouteName());
            result.add(currRouteDto);
        }
        return ResponseEntity.ok(result);
    }
    @PostMapping("/stop")
    public ResponseEntity<BusStop> demo(
        @RequestBody BusStop busStop
    ) {
        return new ResponseEntity<>(busStopRepo.save(busStop),HttpStatus.CREATED);
    }
    
    @DeleteMapping("/stops/{stopId}")
    public ResponseEntity<BusStop> deleteBusStop(@PathVariable Integer stopId){
        busStopRepo.deleteById(stopId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
