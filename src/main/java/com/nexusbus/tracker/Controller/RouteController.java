package com.nexusbus.tracker.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nexusbus.tracker.DTO.StopDto;
import com.nexusbus.tracker.Entities.BusStop;
import com.nexusbus.tracker.Entities.Route;
import com.nexusbus.tracker.Entities.RouteStop;
import com.nexusbus.tracker.Repository.RouteRepo;

@RestController
public class RouteController {

    private final RouteRepo routeRepo;

    public RouteController(RouteRepo routeRepo){
        this.routeRepo=routeRepo;
    }

    @GetMapping("/route")
    public ResponseEntity<List<Route>> getAllRoutes() {
            List<Route> list=routeRepo.findAll();
        return ResponseEntity.ok(list);
    }
    
    @PostMapping("/admin/route")
    public ResponseEntity<Route> postRoute(@RequestBody Route route) {
        try{
            Route result=routeRepo.save(route);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/route/{routeId}")
    public ResponseEntity<?> getRoute(@PathVariable Integer routeId) {
        Optional<Route> routeOptional=routeRepo.findById(routeId);
        return routeOptional.map(route->ResponseEntity.ok(route)).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/routeName/{routeName}")
    public ResponseEntity<List<StopDto>> getStopDto(@PathVariable String routeName) {
        Optional<Route> routeOpt= routeRepo.findByRouteName(routeName);
        if(routeOpt.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        Route route=routeOpt.get();
        List<RouteStop> routeStops=route.getRouteStops();
        List<StopDto> result=new ArrayList<>();
        for(RouteStop rs : routeStops){
            BusStop stop=rs.getBusStop();
            StopDto newStop=new StopDto(
                stop.getStopName(),stop.getStopLatitude(),stop.getStopLongitude()
            );
            result.add(newStop);
        }
        return ResponseEntity.ok(result);
    }
    
    
    @PutMapping("route/{routeId}")
    public ResponseEntity<?> updateRoute(@PathVariable Integer routeId, @RequestBody Route route) {
        
        return routeRepo.findById(routeId)
            .map(oldRoute->{
                oldRoute.setRouteName(route.getRouteName());
                Route newRoute=routeRepo.save(oldRoute);
                return ResponseEntity.ok(newRoute);
            }).orElseGet(()->ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/route/{routeId}")
    public ResponseEntity<?> deleteRoute(@PathVariable Integer routeId){
        return routeRepo.findById(routeId)
            .map(deleteRoute->{
                routeRepo.delete(deleteRoute);
                return ResponseEntity.noContent().build();
            }).orElseGet(()->ResponseEntity.notFound().build());
    }

}
