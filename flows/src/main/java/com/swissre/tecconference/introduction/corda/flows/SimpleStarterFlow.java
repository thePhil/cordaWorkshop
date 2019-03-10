package com.swissre.tecconference.introduction.corda.flows;

import co.paralleluniverse.fibers.Suspendable;
import lombok.AllArgsConstructor;
import net.corda.core.flows.*;

public class SimpleStarterFlow {

    @InitiatingFlow
    @StartableByRPC
    class Initiator extends FlowLogic<Void> {

        @Suspendable
        @Override
        public Void call() throws FlowException {
            return null;
        }
    }


    @InitiatedBy(Initiator.class)
    @AllArgsConstructor
    class Responder extends FlowLogic<Void> {

        private FlowSession commonSession;

        @Suspendable
        @Override
        public Void call() throws FlowException {
            return null;
        }
    }
}
