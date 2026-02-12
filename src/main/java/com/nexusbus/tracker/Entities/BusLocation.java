package com.nexusbus.tracker.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusLocation {
    @Id
    @Column(name="bus_location_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer busLocationId;

    @Column(name="bus_latitude")
    private Double busLatitude;

    @Column(name="bus_longitude")
    private Double busLongitude;

    @OneToOne
    @JoinColumn(name="bus_trip_id" , unique=true)
    @JsonIgnore
    private BusTrip busTrip;
}
