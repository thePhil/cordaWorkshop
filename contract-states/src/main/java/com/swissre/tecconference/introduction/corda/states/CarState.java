package com.swissre.tecconference.introduction.corda.states;

import com.google.common.collect.ImmutableList;
import com.swissre.tecconference.introduction.corda.contracts.CarCreateContract;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.corda.core.contracts.BelongsToContract;
import net.corda.core.contracts.LinearState;
import net.corda.core.contracts.UniqueIdentifier;
import net.corda.core.identity.AbstractParty;
import net.corda.core.identity.Party;
import net.corda.core.schemas.MappedSchema;
import net.corda.core.schemas.PersistentState;
import net.corda.core.schemas.QueryableState;
import net.corda.core.serialization.CordaSerializable;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Getter
@CordaSerializable
@BelongsToContract(CarCreateContract.class)
public class CarState implements LinearState, QueryableState {
    private final UUID id = UUID.randomUUID();

    private final Party carOwner;
    //private final Party insurer;

    // more participants
    //private final Party governmentBody;

    private final String licensePlate;


    @NotNull
    @Override
    public UniqueIdentifier getLinearId() {
        return new UniqueIdentifier(licensePlate, id);
    }

    @NotNull
    @Override
    public PersistentState generateMappedObject(@NotNull MappedSchema schema) {

        if (schema instanceof CarSchemaV1) {
            return new CarSchemaV1.PersistentCarState(
                    carOwner.getName().toString(),
                    licensePlate,
                    id.toString());
        } else {
            throw new IllegalArgumentException("Unknown schema");
        }
    }

    @NotNull
    @Override
    public Iterable<MappedSchema> supportedSchemas() {
        return ImmutableList.of(new CarSchemaV1());
    }

    @NotNull
    @Override
    public List<AbstractParty> getParticipants() {
        //return Arrays.asList(carOwner, insurer);
        return Collections.singletonList(carOwner);
    }
}

