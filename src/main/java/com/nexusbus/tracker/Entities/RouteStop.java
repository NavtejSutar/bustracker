package com.nexusbus.tracker.Entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RouteStop {
    @Id
    @Column(name="route_stop_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer routeStopId;
    
    @ManyToOne
    @JoinColumn(name="route_id", nullable=false)
    @JsonIgnoreProperties({"routeStops","busTrips"})
    private Route route;

    @ManyToOne
    @JoinColumn(name="stop_id", nullable=false)
    @JsonIgnoreProperties({"routeStops"})
    private BusStop busStop;

    @Column(name="sequence")
    private Integer sequence;
}
