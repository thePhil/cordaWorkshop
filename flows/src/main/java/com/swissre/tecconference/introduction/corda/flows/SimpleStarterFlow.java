package com.swissre.tecconference.introduction.corda.flows;

import co.paralleluniverse.fibers.Suspendable;
import net.corda.core.flows.*;

@InitiatingFlow
@StartableByRPC
public class SimpleStarterFlow extends FlowLogic<Void> {

    @Suspendable
    @Override
    public Void call() throws FlowException {
        return null;
    }
}

