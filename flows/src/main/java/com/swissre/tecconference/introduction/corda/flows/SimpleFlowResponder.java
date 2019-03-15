package com.swissre.tecconference.introduction.corda.flows;

import co.paralleluniverse.fibers.Suspendable;
import lombok.AllArgsConstructor;
import net.corda.core.crypto.SecureHash;
import net.corda.core.flows.*;
import net.corda.core.transactions.SignedTransaction;
import net.corda.core.utilities.ProgressTracker;
import org.jetbrains.annotations.NotNull;

@InitiatedBy(SimpleStarterFlow.class)
@AllArgsConstructor
public class SimpleFlowResponder extends FlowLogic<net.corda.core.transactions.SignedTransaction> {
    private FlowSession commonSession;

    @Suspendable
    @Override
    public SignedTransaction call() throws FlowException {


        class SignTrxFlow extends SignTransactionFlow {
            private SignTrxFlow(FlowSession otherSession, ProgressTracker progressTracker) {
                super(otherSession, progressTracker);
            }

            @Override
            protected void checkTransaction(@NotNull SignedTransaction stx) throws FlowException {
            }
        }
        ;

        SecureHash txISigned = subFlow(new SignTrxFlow(commonSession, SignTransactionFlow.tracker())).getId();
        System.out.println(txISigned);

        return subFlow(new ReceiveFinalityFlow(commonSession, txISigned));
    }
}
