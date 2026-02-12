package com.nexusbus.tracker.Entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Route {
    @Id
    @Column(name="route_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer routeId;

    @Column(name="route_name", unique=true)
    private String routeName;

    @OneToMany(
        mappedBy="route",
        cascade = CascadeType.ALL
    )
    @OrderBy("sequence")
    @JsonIgnoreProperties({"route"})
    List<RouteStop> routeStops=new ArrayList<>();

    @OneToMany(mappedBy="route", cascade=CascadeType.ALL)
    @JsonManagedReference("route-trip")
    private List<BusTrip> busTrips=new ArrayList<>();
}

