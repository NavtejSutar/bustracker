package com.nexusbus.tracker.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusTrip {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="bus_trip_id")
    private Integer busTripId;

    @ManyToOne
    @JoinColumn(name="bus_id", nullable=false)
    @JsonBackReference("bus-trip")
    private Bus bus;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    @JsonBackReference("user-trip")
    private Users users;

    @ManyToOne
    @JoinColumn(name="route_id", nullable=false)
    @JsonBackReference("route-trip")
    private Route route;

    @Column(name="active")
    private Boolean active;

    @OneToOne(mappedBy="busTrip")
    private BusLocation busLocation;
}
