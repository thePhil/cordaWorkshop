package com.swissre.tecconference.introduction.corda.flows;

import co.paralleluniverse.fibers.Suspendable;
import net.corda.core.flows.FlowException;
import net.corda.core.flows.FlowLogic;
import net.corda.core.flows.FlowSession;
import net.corda.core.flows.InitiatedBy;

@InitiatedBy(SimpleStarterFlow.class)
public class SimpleFlowResponder extends FlowLogic<Void> {
    private FlowSession commonSession;

    public SimpleFlowResponder(FlowSession session) {
        this.commonSession = session;
    }


    @Suspendable
    @Override
    public Void call() throws FlowException {
        return null;
    }
}
