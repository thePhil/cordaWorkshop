package com.swissre.tecconference.introduction.corda.flows;

import co.paralleluniverse.fibers.Suspendable;
import com.swissre.tecconference.introduction.corda.states.CarState;
import com.swissre.tecconference.introduction.corda.states.CreateCarCommand;
import lombok.AllArgsConstructor;
import net.corda.core.contracts.Command;
import net.corda.core.flows.*;
import net.corda.core.identity.AbstractParty;
import net.corda.core.identity.Party;
import net.corda.core.transactions.SignedTransaction;
import net.corda.core.transactions.TransactionBuilder;
import net.corda.core.utilities.ProgressTracker;
import org.jetbrains.annotations.Nullable;

import java.security.PublicKey;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@InitiatingFlow
@StartableByRPC
@AllArgsConstructor
public class SimpleStarterFlow extends FlowLogic<Void> {

    private CarState flowInputState;

    private static ProgressTracker.Step SIGNED_MY_TRX = new ProgressTracker.Step("Signed my Own Trx");
    private static ProgressTracker.Step SIGNED_RESPOND_TRX = new ProgressTracker.Step("Signed Response Trx");
    private static ProgressTracker.Step SIG_GATHERING = new ProgressTracker.Step("Collect Signatures") {
        @Nullable
        @Override
        public ProgressTracker childProgressTracker() {
            return CollectSignaturesFlow.tracker();
        }
    };
    private static ProgressTracker.Step FINALIZING = new ProgressTracker.Step("Finalizing") {
        @Nullable
        @Override
        public ProgressTracker childProgressTracker() {
            return FinalityFlow.tracker();
        }
    };

    private final ProgressTracker progressTracker = new ProgressTracker(
            SIGNED_MY_TRX,
            SIGNED_RESPOND_TRX,
            SIG_GATHERING,
            FINALIZING);

    @Suspendable
    @Override
    public Void call() throws FlowException {
        List<AbstractParty> stateParticipants = flowInputState.getParticipants();

        List<PublicKey> signingKeys =
                stateParticipants.stream()
                        .map(AbstractParty::getOwningKey)
                        .collect(Collectors.toList());

        Party notary = getServiceHub().getNetworkMapCache().getNotaryIdentities().stream().findFirst().get();
        Party partyB = getServiceHub().getIdentityService().partiesFromName("PartyB", false).stream().findFirst().get();

        signingKeys.add(partyB.getOwningKey());

        Command cmd = new Command(new CreateCarCommand(), signingKeys);

        TransactionBuilder tb = new TransactionBuilder(notary)
                .addCommand(cmd)
                .addOutputState(flowInputState);

        SignedTransaction strx = getServiceHub().signInitialTransaction(tb, flowInputState.getCarOwner().getOwningKey());
        progressTracker.setCurrentStep(SIGNED_MY_TRX);

        FlowSession ses = this.initiateFlow(notary);
        FlowSession sesB = this.initiateFlow(partyB);

        progressTracker.setCurrentStep(SIG_GATHERING);

        SignedTransaction fullySignedTrx = subFlow(new CollectSignaturesFlow(strx, Collections.singletonList(sesB),
                SIG_GATHERING.childProgressTracker()));

        progressTracker.setCurrentStep(FINALIZING);
        SignedTransaction notarizedTrx = subFlow(
                new FinalityFlow(fullySignedTrx,
                Collections.singletonList(sesB),
                FINALIZING.childProgressTracker()));

        System.out.println("Finalization done." + notarizedTrx.getId());

        return null;
    }
}

