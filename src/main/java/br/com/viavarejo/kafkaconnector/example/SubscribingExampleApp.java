package br.com.viavarejo.kafkaconnector.example;

import java.util.logging.Logger;

public class SubscribingExampleApp {

    private static final String EXAMPLE_GROUP = "EXAMPLE_GROUP";
    private static final Logger LOGGER = Logger.getLogger(SubscribingExampleApp.class.getName());
    /*
     * public static void main(final String[] args) { consumingSingleMessageExample();
     * consumingMessageExample(); consumingObjectMessageExample(); }
     * 
     * public static void consumingSingleMessageExample() { final KafkaConnector kafkaConnector =
     * KafkaConnectorFactory.createConnector(Collections.singletonList(BROKER.getDescription()));
     * final String topico = "TOPICO_TESTE"; final String clientId = "CLIENTE-TESTE";
     * 
     * final KafkaMessages messages = new KafkaMessages(); messages.addMessage(new
     * KafkaMessage(topico, "k1", "MENSAGEM 1").configClientId(clientId)); messages.addMessage(new
     * KafkaMessage(topico, "k2", "MENSAGEM 2").configClientId(clientId)); messages.addMessage(new
     * KafkaMessage(topico, "k3", "MENSAGEM 3").configClientId(clientId));
     * 
     * kafkaConnector.publish(messages);
     * 
     * final KafkaMessageConsumer consumer = new KafkaMessageConsumer(EXAMPLE_GROUP);
     * consumer.addTopic(topico) .configClientId(clientId) .configStringKeyDeserializer()
     * .configStringMessageDeserializer() .configMaxNumberMessages(1);// Consumindo apenas uma
     * mensagem do topico.
     * 
     * final List<String> mensagensPublicadas = new ArrayList<>();
     * kafkaConnector.subscribe(consumer, message -> mensagensPublicadas.add((String)
     * message.getValue()));
     * 
     * LOGGER.info(String.format("Recuperou %d mensagem.", mensagensPublicadas.size())); }
     * 
     * public static void consumingMessageExample() { final KafkaConnector kafkaConnector =
     * KafkaConnectorFactory.createConnector(Collections.singletonList(BROKER.getDescription()));
     * 
     * // Exemplo de um unico subscription em dois topicos e tratando as mensagens de // formas
     * distintas. Note que nesse cenario os tipos de de serializacao e deserializacao // devem ser
     * os mesmos. final KafkaMessageConsumer voucherConsumer = new
     * KafkaMessageConsumer(EXAMPLE_GROUP);
     * voucherConsumer.addTopic(VOUCHER_LIBERADO_TOPIC.getDescription())
     * .addTopic(DATA_VALIDADE_VOUCHER_TOPIC.getDescription()) .configClientId("BB3")
     * .configLongKeyDeserializer() .configStringMessageDeserializer();
     * 
     * kafkaConnector.subscribe(voucherConsumer, message -> { if
     * (VOUCHER_LIBERADO_TOPIC.getDescription().equals(message.getTopicName())) {
     * LOGGER.info("VOUCHER " + message.getKey() + " LIBERADO. " + message.getValue()); } else if
     * (DATA_VALIDADE_VOUCHER_TOPIC.getDescription().equals(message.getTopicName())) {
     * LOGGER.info("A DATA DE VALIDADE DO VOUCHER " + message.getKey() + " É " +
     * message.getValue()); } });
     * 
     * final KafkaMessageConsumer creditoConsumer = new KafkaMessageConsumer(EXAMPLE_GROUP);
     * creditoConsumer.addTopic(CREDITO_LIBERADO_TOPIC.getDescription()) .configClientId("BB3")
     * .configLongKeyDeserializer() .configIntegerMessageDeserializer() .configPollAttempts(12)
     * .configBatchCommit(false); kafkaConnector.subscribe(creditoConsumer, message ->
     * LOGGER.info("CONSUINDO O CREDITO DE R$" + message.getValue() + " PARA O VOUCHER " +
     * message.getKey())); }
     * 
     * public static void consumingObjectMessageExample() { final KafkaConnector kafkaConnector =
     * KafkaConnectorFactory.createConnector(Collections.singletonList(BROKER.getDescription()));
     * 
     * final KafkaMessageConsumer voucherClienteConsumer = new KafkaMessageConsumer(EXAMPLE_GROUP);
     * voucherClienteConsumer.addTopic(VOUCHER_CLIENTE_TOPIC.getDescription());
     * voucherClienteConsumer.configClientId("BB3");
     * 
     * voucherClienteConsumer.configLongKeyDeserializer();
     * voucherClienteConsumer.configCustomMessageDeserializer(ClienteDeserializer.class);
     * 
     * final Consumer<KafkaMessage> callback = message -> LOGGER .info("O VOUCHER DO CLIENTE \"" +
     * message.getValue() + "\"" + " É O DE NUMERO " + message.getKey());
     * kafkaConnector.subscribe(voucherClienteConsumer, callback); }
     */
}
