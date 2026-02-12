package com.nexusbus.tracker.Entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
public class BusStop {
    @Id
    @Column(name="stop_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer stopId;

    @Column(name="stop_name")
    private String stopName;

    @Column(name="stop_latitude")
    private Double stopLatitude;

    @Column(name="stop_longitude")
    private Double stopLongitude;

    @OneToMany(
        mappedBy="busStop",
        cascade= CascadeType.ALL
    )
    @OrderBy("sequence")
    @JsonIgnoreProperties({"busStop"})
    List<RouteStop> routeStops=new ArrayList<>();
}
