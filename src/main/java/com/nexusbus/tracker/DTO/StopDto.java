package com.nexusbus.tracker.DTO;

public record StopDto(
    Integer stopId,
    String stopName,
    Double stopLatitude,
    Double stopLongitude
) {
    
}
