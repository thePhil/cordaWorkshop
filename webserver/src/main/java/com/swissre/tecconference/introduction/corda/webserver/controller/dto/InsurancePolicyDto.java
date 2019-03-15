package com.swissre.tecconference.introduction.corda.webserver.controller.dto;

import lombok.Value;

import java.math.BigDecimal;
import java.util.UUID;

@Value
public class InsurancePolicyDto {
    private UUID id;

    private String insuredPerson;
    private String insuranceCompany;

    private CarDto insuredCar;

    private BigDecimal insuredValue;
    private String additionalClauses;
}
