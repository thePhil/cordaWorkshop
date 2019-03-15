tmux new-window -c "$(pwd)/build/nodes/Notary" -n Notary '/usr/lib/jvm/java-8-jdk/jre/bin/java' '-Dcapsule.jvm.args=-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5006 -javaagent:drivers/jolokia-jvm-1.6.0-agent.jar=port=7006,logHandlerClass=net.corda.node.JolokiaSlf4jAdapter' '-Dname=Notary' '-jar' '/home/philipp/git/cordaWorkshop/build/nodes/Notary/corda.jar' '--log-to-console' '--logging-level=DEBUG'
tmux new-window -c "$(pwd)/build/nodes/PartyA" -n PartyA '/usr/lib/jvm/java-8-jdk/jre/bin/java' '-Dcapsule.jvm.args=-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 -javaagent:drivers/jolokia-jvm-1.6.0-agent.jar=port=7005,logHandlerClass=net.corda.node.JolokiaSlf4jAdapter' '-Dname=PartyA' '-jar' '/home/philipp/git/cordaWorkshop/build/nodes/PartyA/corda.jar' '--log-to-console' '--logging-level=DEBUG'


# sleep 20
tmux new-window -c "$(pwd)/build/nodes/PartyB" -n PartyB '/usr/lib/jvm/java-8-jdk/jre/bin/java' '-Dcapsule.jvm.args=-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5007 -javaagent:drivers/jolokia-jvm-1.6.0-agent.jar=port=7007,logHandlerClass=net.corda.node.JolokiaSlf4jAdapter' '-Dname=PartyB' '-jar' '/home/philipp/git/cordaWorkshop/build/nodes/PartyB/corda.jar' '--log-to-console' '--logging-level=DEBUG'


sleep 25

# server partyA
tmux new-window -c "$(pwd)" -n PartyAServer 'java' '-jar webserver/build/libs/webserver-0.0.1.jar --server.port=10050 --config.rpc.host=localhost --config.rpc.port=10006 --config.rpc.username=user1 --config.rpc.password=test'

# server partyB
tmux new-window -c "$(pwd)" -n PartyBServer 'java' '-jar webserver/build/libs/webserver-0.0.1.jar --server.port=10051 --config.rpc.host=localhost --config.rpc.port=10009 --config.rpc.username=user1 --config.rpc.password=test'
