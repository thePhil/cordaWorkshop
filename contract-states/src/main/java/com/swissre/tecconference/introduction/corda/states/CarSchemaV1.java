package com.swissre.tecconference.introduction.corda.states;

import com.google.common.collect.ImmutableList;
import lombok.*;
import net.corda.core.schemas.MappedSchema;
import net.corda.core.schemas.PersistentState;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

public class CarSchemaV1 extends MappedSchema {

    public CarSchemaV1() {
        super(CarSchemaV1.class, 1, ImmutableList.of(PersistentCarState.class));
    }

    @Entity
    @Table(name = "cars")
    @AllArgsConstructor(access = AccessLevel.PUBLIC)
    @NoArgsConstructor(access = AccessLevel.PUBLIC)
    @Getter(value = AccessLevel.PUBLIC)
    @Setter(value = AccessLevel.PUBLIC)
    public static class PersistentCarState extends PersistentState {

        @Column
        private String carOwner;

        @Column
        private String licensePlate;
        @Column
        private String linearId;

    }

}
