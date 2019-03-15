package com.swissre.tecconference.introduction.corda.states;

import lombok.AllArgsConstructor;
import net.corda.core.contracts.LinearState;
import net.corda.core.contracts.UniqueIdentifier;
import net.corda.core.identity.AbstractParty;
import net.corda.core.identity.Party;
import net.corda.core.schemas.MappedSchema;
import net.corda.core.schemas.PersistentState;
import net.corda.core.schemas.QueryableState;
import net.corda.core.serialization.CordaSerializable;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@CordaSerializable
@AllArgsConstructor
public class InsurancePolicyState  implements LinearState {

    private final UUID id = UUID.randomUUID();

    private final Party Insurer;
    private final Party PolicyOwner;
    private UniqueIdentifier insuredCar;

    private final BigDecimal insuredValue;
    private final String additionalClauses;

    @NotNull
    @Override
    public UniqueIdentifier getLinearId() {
        return new UniqueIdentifier(id.toString(), id);
    }

    @NotNull
    @Override
    public List<AbstractParty> getParticipants() {
        return Arrays.asList(Insurer, PolicyOwner);
    }
}
