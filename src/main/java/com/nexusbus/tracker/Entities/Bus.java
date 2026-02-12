package com.nexusbus.tracker.Entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bus {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="bus_id")
    private Integer busId;

    @Column(name="number_plate")
    private String numberPlate;

    @OneToMany(mappedBy="bus", cascade=CascadeType.ALL)
    @JsonManagedReference("bus-trip")
    private List<BusTrip> busTrips=new ArrayList<>();
}
