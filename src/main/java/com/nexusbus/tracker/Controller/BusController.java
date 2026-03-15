package com.nexusbus.tracker.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nexusbus.tracker.Entities.Bus;
import com.nexusbus.tracker.Repository.BusRepo;


@RestController
public class BusController {
    
    private final BusRepo busRepo;

    public BusController(BusRepo busRepo){
        this.busRepo=busRepo;
    }

    @PostMapping("/admin/bus")
    public ResponseEntity<?> addBus(@RequestBody Bus bus){
        try{
            Bus newBus= busRepo.save(bus);
            return ResponseEntity.status(HttpStatus.CREATED).body(newBus);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/bus")
    @PreAuthorize("hasAnyRole('admin', 'driver')")
    public ResponseEntity<?> searchBus(@RequestParam String bus) {
        List<Bus> list=busRepo.findByNumberPlateContaining(bus);
        return ResponseEntity.ok().body(list);
    }

    // In BusTripController.java
@GetMapping("/admin/tripHistory")
public ResponseEntity<?> getTripHistory() {
    return ResponseEntity.ok(busTripRepo.findByActiveFalse());
}
    

}
