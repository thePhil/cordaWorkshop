package com.swissre.tecconference.introduction.corda.webserver.controller.dto;

import lombok.Value;

import java.util.UUID;

@Value
public class CarDto {
    private String licensePlate;

    // X500 Name representation of owner
    private String owner;

    private UUID id;
}
