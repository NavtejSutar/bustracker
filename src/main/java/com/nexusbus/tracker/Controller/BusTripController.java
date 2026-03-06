package com.nexusbus.tracker.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.nexusbus.tracker.DTO.BusTripDto;
import com.nexusbus.tracker.Entities.Bus;
import com.nexusbus.tracker.Entities.BusLocation;
import com.nexusbus.tracker.Entities.BusTrip;
import com.nexusbus.tracker.Entities.Route;
import com.nexusbus.tracker.Entities.Users;
import com.nexusbus.tracker.Repository.BusLocationRepo;
import com.nexusbus.tracker.Repository.BusRepo;
import com.nexusbus.tracker.Repository.BusTripRepo;
import com.nexusbus.tracker.Repository.RouteRepo;
import com.nexusbus.tracker.Repository.UsersRepo;

import jakarta.transaction.Transactional;


@RestController
public class BusTripController {
    private final BusTripRepo busTripRepo;
    private final BusLocationRepo busLocationRepo;
    private final UsersRepo usersRepo;
    private final RouteRepo routeRepo;
    private final BusRepo busRepo;

    public BusTripController(
        BusTripRepo busTripRepo,
        BusLocationRepo busLocationRepo,
        UsersRepo usersRepo,
        RouteRepo routeRepo,
        BusRepo busRepo
    ){
        this.busTripRepo=busTripRepo;
        this.busLocationRepo=busLocationRepo;
        this.usersRepo=usersRepo;
        this.routeRepo=routeRepo;
        this.busRepo=busRepo;
    }

    @PostMapping("/driver/busTrip")
    @Transactional
    public ResponseEntity<?> startBusTrip(
        @RequestBody BusTripDto busTripDto, Authentication authentication
    ) {
        String emailId=authentication.getName();
        Users users = usersRepo.findByEmailId(emailId)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "User not found"));
        if(busTripRepo.existsByUsersAndActiveTrue(users)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No Active User");
        }

        Route route = routeRepo.findByRouteName(busTripDto.routeName())
            .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Route not found"));

        Bus bus = busRepo.findByNumberPlate(busTripDto.numberPlate())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Bus not found"));

        BusTrip busTrip=new BusTrip();
        busTrip.setUsers(users);
        busTrip.setRoute(route);
        busTrip.setBus(bus);
        busTrip.setActive(true);

        BusTrip newTrip=busTripRepo.save(busTrip);

        BusLocation location=new BusLocation();
        location.setBusTrip(newTrip);
        location.setBusLatitude(null);
        location.setBusLongitude(null);

        BusLocation busLocation= busLocationRepo.save(location);

        return ResponseEntity.status(HttpStatus.CREATED).body(newTrip);
    }
    @GetMapping("/driver/myBusTrip")
    public ResponseEntity<?> getBusTrip(Authentication authentication){
        String emailId=authentication.getName();
        Users users = usersRepo.findByEmailId(emailId)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "User not found"));
        if(!busTripRepo.existsByUsersAndActiveTrue(users)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No Active User");
        }
        return ResponseEntity.status(HttpStatus.OK).body(
            busTripRepo.findByUsersAndActiveTrue(users)
        );
    }
}
