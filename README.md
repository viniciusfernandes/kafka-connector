# PARA A INSTALACAO LOCAL DO ZOOKEEPER
	1) para a instalação do zookeeper basta descompactar o arquivo em algum diretório, por exemplo C:\Zookeeper\3.4.14
	2) renomear o arquivo zoo_sample.cfg para zoo.cfg
	3) editar a variável dataDir=/tmp/zookeeper para dataDir=C:\Zookeeper\3.4.14\data
	4) configurar a variável de ambiente ZOOKEEPER_HOME e o path %ZOOKEEPER_HOME%\bin
	5) executar o zookeeper com o comando zkserver

# PARA A INSTALACAO LOCAL DO KAFKA 
	1) para a instalação do kafka basta descompactar o arquivo em algum diretório, por exemplo C:\Kafka\2.12
	2) editar a variável de ambiente KAFKA_HOME=C:\Kafka\2.12
	3) editar o path %KAFKA_HOME%\bin\windows
	5) para rodar o kafka execute o comando kafka-server-start.bat %KAFKA_HOME%\config\server.properties

# COMANDOS UTEIS PARA LISTAR OS TOPICOS E MENSAGENS NO KAFKA
	1) para criar um tópico execute o comando: kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic teste
	2) para listar os tópicos: kafka-topics.bat --list --zookeeper localhost:2181 
	3) para exibir os detalhes de um tópico: kafka-topics.bat --describe --zookeeper localhost:2181 --topic [Topic Name]
	4) para deletar um tópico: kafka-run-class.bat kafka.admin.TopicCommand --delete --topic [topic_to_delete] --zookeeper localhost:2181
	5) para exibir as mensagens de um tópico: kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic VOUCHER_LIBERADO --from-beginning --zookeeper localhost:2181
	6) para listar os grupos de consumers: kafka-consumer-groups.bat  --list --bootstrap-server localhost:9092 --zookeeper localhost:2181
	
# EXEMPLOS DE APLICACAO
	1) Veja o pacote de exemplo "br.com.viavarejo.kafkaconnector.example" no projeto
	2) Para configurar o sistema de log do exemplo, vá no diretório src/main/resources e remova sufixo EXAMPLO do arquivo log4j.properties.
	3) No arquivo log4j.properties defina o nome do arquivo de log do sistema.

# SAMPLE CODE DE CRIACAO DO KAFKA CONNECTOR
        final KafkaConnector kafkaConnector = KafkaConnectorFactory.createConnector(Collections.singletonList(<lista de hosts>);

# SAMPLE CODE DE CRIACAO E CONFIGURACAO DE UMA MENSAGEM
	final KafkaMessage message =
                        new KafkaMessage(<nome do topico>, <chave>, <valor>).configClientId(<id do cliente>);

# SAMPLE CODE DE CRIACAO E CONFIGURACAO DE UMA MENSAGEM COMPLEXA
	1) Para publicar um objeto complexo como valor da mensagem é necessário a implementação de um Serializador customizado. Para isso você pode extender a classe CustomSerializer, que já contém muita coisa feita, ou fazer tudo do zero extendendo o ObjectSerializer.
	
	final KafkaMessage message =
                        new KafkaMessage(<nome do topico>, <chave>, <valor>).configClientId(<id do cliente>).configCustomMessageSerializer(ClienteSerializer.class);;

# SAMPLE CODE DE PUBLISH DE UMA MENSAGEM
	kafkaConnector.publish(message,
                        mensagen -> <faz alguma coisa no callback>);

# SAMPLE CODE DE CRIACAO E CONFIGURACAO DO CONSUMER
	1) Nesse exemplo se está fazendo um subscribe em um topico em que o valor da mensagem é um objeto complexo, e para a sua deserialização foi configurado um deserializador customizado.
	
        final KafkaMessageConsumer voucherClienteConsumer = new KafkaMessageConsumer("EXAMPLE_GROUP");
        voucherClienteConsumer.addTopic(<NOME DO TOPICO>)
                        .configClientId(<ID DO CONSUMER>)
                        .configKeyDeserializer(Long.class)
                        .configCustomMessageDeserializer(ClienteDeserializer.class);
                        
# SAMPLE CODE DE SUBSCRIBE DO CONSUMER

        kafkaConnector.subscribe(voucherClienteConsumer);
        kafkaConnector.subscribe(voucherClienteConsumer, message -> {<faz alguma coisa no callback>});


