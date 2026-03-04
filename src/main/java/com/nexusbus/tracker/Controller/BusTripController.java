package com.nexusbus.tracker.Controller;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/busTrip")
    public ResponseEntity<?> startBusTrip(
        @RequestBody BusTripDto busTripDto, Authentication authentication
    ) {
        String emailId=authentication.getName();
        Optional<Users> usersOpt= usersRepo.findByEmailId(emailId);
        Users users=usersOpt.get();

        Optional<Route> routeOpt=routeRepo.findByRouteName(busTripDto.routeName());
        Route route=routeOpt.get();

        Optional<Bus> busOpt=busRepo.findByNumberPlate(busTripDto.numberPlate());
        Bus bus=busOpt.get();

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

        return ResponseEntity.ok(newTrip);
    }
    
}
