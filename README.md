# Corda Introduction -- Building your first CorDapp

Corda is a Distributed Ledger Platform. It combines the advantages of blockchains (immutability, traceability) with the
necessary privacy, required in an enterprise environment.

Corda is a product from the company R3, based in London. There exist two versions of Corda. The Open Source Version as well as an Enterprise derivative.
The Open Source Version of Corda (Corda OSS) is licensed under the Apache 2.0 license.

* [Corda Website](https://www.corda.net/)
* [Corda Documentation](https://docs.corda.net/)
* [Corda Getting Setup](https://docs.corda.net/head/getting-set-up.html)
* [Corda Java Template](https://github.com/corda/cordapp-template-java)
* [Corda Business Network Membership Service](https://github.com/corda/corda-solutions/tree/master/bn-apps/memberships-management)

## Project Structure
### Data -- Contracts and States
The first module will contain the shared, structured, information AND the *smart contract* governing state evolution.
Here we also introduce the term **Governing Contract**. A governing contract is a Java class that inherits from 

### Business Logic -- Flows
The second module will contain the business logic

### Spring Boot Webserver
We are going to avoid using the integrated Corda Webserver, since it is deprecated and are going to use a Spring Boot
based Webserver instead.

## Getting Setup
Please follow the instructions under [Corda Getting Setup](https://docs.corda.net/head/getting-set-up.html).

Make sure you have checked out the following git repository, which I prepared to have an easy start into our workshop.
[Corda Workshop Template](https://github.com/thePhil/cordaWorkshop)

## Sample Scenario
During the workshop we are going to work on a very simple insurance application, a new car owner is buying an insurance policy to cover damages to his car. 
Optionally we are going to also include a very simple claims process without a payment.


## Getting Started

In order to build the software do the following
`./gradlew clean build deployNodes -x test`

If you are using a normal terminal, execute the following line in order to start the local network with three nodes:
`./build/nodes/runnodes`

If you are running under a `tmux` environment execute the included script:
`./startNodes.sh`

The `startNodes.sh` script needs to be adapted every time you change a configuration in the nodes. For a new version 
of your corDapp restart your nodes.

The webserver can be started either through the functionality in IntelliJ or via a command line execution. Just 
ensure you are including the proper command line arguments. An example can be found in the webserver project [Gradle 
File](./webserver/build.gradle) with the task: `runSpringServerPartyA`.
