package com.swissre.tecconference.introduction.corda.webserver.controller;

import com.swissre.tecconference.introduction.corda.flows.SimpleStarterFlow;
import com.swissre.tecconference.introduction.corda.states.CarState;
import com.swissre.tecconference.introduction.corda.webserver.NodeRPCConnection;
import com.swissre.tecconference.introduction.corda.webserver.controller.dto.CarDto;
import net.corda.core.identity.Party;
import net.corda.core.messaging.FlowHandle;
import net.corda.core.node.services.Vault;
import net.corda.core.node.services.vault.QueryCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@RestController
public class FirstController {

    private final NodeRPCConnection con;

    @Autowired
    public FirstController(NodeRPCConnection con) {
        this.con = con;
    }

    @GetMapping("/hello")
    public String helloWorld() {
        return "Hello World";
    }

    @PostMapping("/car")
    public ResponseEntity createCar(
            @RequestBody
                    CarDto car) throws ExecutionException, InterruptedException {
        Set<Party> parties = con.proxy.partiesFromName(car.getOwner(), true);

        // build car state
        CarState carState = new CarState(parties.stream().findFirst().get(), car.getLicensePlate());

        // call flow to persist Car
        FlowHandle<Void> flowHandle = con.proxy.startFlowDynamic(SimpleStarterFlow.class, carState);

        // await completition of car state creation
        flowHandle.getReturnValue().toCompletableFuture().get();

        // return created car
        return ResponseEntity.ok().build();
    }

    @GetMapping("/car")
    public ResponseEntity<List<CarDto>> getCar() {

        // get all Parties in the network
        List<Party> allParties =
                con.proxy.networkMapSnapshot().stream()
                        .flatMap(nodeInfo -> nodeInfo.getLegalIdentities().stream())
                        .collect(Collectors.toList());

        // define Vault search criteria
//        QueryCriteria criteria = new QueryCriteria.LinearStateQueryCriteria(allParties, Collections.singletonList(id));

        // get vault result
//        Vault.Page<CarState> page = con.proxy.vaultQueryByCriteria(criteria, CarState.class);
        Vault.Page<CarState> page = con.proxy.vaultQuery(CarState.class);
        List<CarDto> cars =
                page.getStates().stream()
                        .map(carStateStateAndRef ->
                                carStateToCarDto(carStateStateAndRef.getState().getData()))
                        .collect(Collectors.toList());

        //return created car
        return ResponseEntity.ok(cars);
    }

    private CarDto carStateToCarDto(CarState carState) {
        return new CarDto(carState.getLicensePlate(), carState.getCarOwner().toString(), carState.getId());
    }

    @GetMapping("/sample/car")
    public ResponseEntity<CarDto> getSampleCar() {

        String myX500Name = con.proxy.nodeInfo().getLegalIdentities().stream().findFirst().get().getName().toString();

        return ResponseEntity.ok(new CarDto("ZH-1234567", myX500Name, UUID.randomUUID()));
    }

}
