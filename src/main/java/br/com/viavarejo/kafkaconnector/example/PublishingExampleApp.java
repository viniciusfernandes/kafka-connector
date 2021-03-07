package br.com.viavarejo.kafkaconnector.example;

import java.util.logging.Logger;

import br.com.viavarejo.kafkaconnector.connector.impl.KPublisher;

public class PublishingExampleApp {

    private static final String VIA_VAREJO = "VIAVAREJO";

    private static final Logger LOGGER = Logger.getLogger(PublishingExampleApp.class.getName());

    public static void main(String[] args) {
        KPublisher publisher = new KPublisher("primeiro_teste", 1, "Mensagem 1");
       // publisher.addHost("localhost:")
    }
    /*
     * public static void main(final String[] args) { publishingSingleMessageExample();
     * publishingMessageExample(); publishingMessagesExample(); publishingMessageObjectExample(); }
     * 
     * private static void publishingSingleMessageExample() { final KafkaConnector kafkaConnector =
     * KafkaConnectorFactory.createConnector(Collections.singletonList(BROKER.getDescription()));
     * final String topico = "TESTE_TOPIC"; final Long key = 9999L; final String value =
     * "Data liberacao: " + new Date();
     * 
     * final KafkaMessage voucherMessage = new KafkaMessage(topico, key,
     * value).configClientId(VIA_VAREJO);
     * 
     * kafkaConnector.publish(voucherMessage, message -> LOGGER.info(String.
     * format("EXECUTANDO CALLBACK DA PUBLICACAO DA MENSAGEM. CHAVE: %s, VALOR: %s",
     * message.getKey(), message.getValue()))); }
     * 
     * private static void publishingMessageExample() { final SimpleDateFormat df = new
     * SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
     * 
     * final KafkaConnector kafkaConnector =
     * KafkaConnectorFactory.createConnector(Collections.singletonList(BROKER.getDescription()));
     * 
     * KafkaMessage voucherMessage = null; KafkaMessage creditoMessage = null; KafkaMessage
     * dataValidadeMessage = null; Long numVoucher = null; int valCredito = 0;
     * 
     * final Calendar cal = Calendar.getInstance(); final String data = df.format(cal.getTime());
     * final String dataVoucher = df.format(cal.getTime()); for (int i = 1; i <= 5; i++) {
     * 
     * numVoucher = gerarNumVoucher(); valCredito = i * 1000; voucherMessage = new
     * KafkaMessage(VOUCHER_LIBERADO_TOPIC.getDescription(), numVoucher, "Data liberacao: " + data);
     * voucherMessage.configClientId(VIA_VAREJO);
     * 
     * dataValidadeMessage = new KafkaMessage(DATA_VALIDADE_VOUCHER_TOPIC.getDescription(),
     * numVoucher, dataVoucher); dataValidadeMessage.configClientId(VIA_VAREJO);
     * 
     * creditoMessage = new KafkaMessage(CREDITO_LIBERADO_TOPIC.getDescription(), numVoucher,
     * valCredito); creditoMessage.configClientId(VIA_VAREJO);
     * 
     * kafkaConnector.publish(voucherMessage); kafkaConnector.publish(dataValidadeMessage);
     * kafkaConnector.publish(creditoMessage, credMessage -> LOGGER.info(
     * String.format("O CREDITO DE R$%s FOI CRIADO PARA O VOUCHER %s", credMessage.getValue(),
     * credMessage.getKey()))); } }
     * 
     * private static void publishingMessagesExample() { final SimpleDateFormat df = new
     * SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
     * 
     * final KafkaConnector kafkaConnector =
     * KafkaConnectorFactory.createConnector(Collections.singletonList(BROKER.getDescription()));
     * final KafkaMessages messages = new KafkaMessages();
     * 
     * final long voucher1 = gerarNumVoucher(); final long voucher2 = gerarNumVoucher();
     * 
     * final String desc1 = "INCLUINDO O VOUCHER " + voucher1 + " NA DATA " + df.format(new Date());
     * final String desc2 = "INCLUINDO O VOUCHER " + voucher2 + " NA DATA " + df.format(new Date());
     * 
     * final KafkaMessage message1 = new KafkaMessage(VOUCHER_LIBERADO_TOPIC.getDescription(),
     * voucher1, desc1).configClientId(VIA_VAREJO);
     * 
     * final KafkaMessage message2 = new KafkaMessage(VOUCHER_LIBERADO_TOPIC.getDescription(),
     * voucher2, desc2).configClientId(VIA_VAREJO);
     * 
     * messages.addMessage(message1).addMessage(message2); kafkaConnector.publish(messages,
     * mensagens ->
     * LOGGER.info("Notificando o parceiro de que já existem mensagens para consumir."));
     * 
     * }
     * 
     * private static void publishingMessageObjectExample() { final KafkaConnector kafkaConnector =
     * KafkaConnectorFactory.createConnector(Collections.singletonList(BROKER.getDescription()));
     * 
     * final long voucher = gerarNumVoucher(); final Cliente cliente = new Cliente(1234,
     * "Machado de Assis", "999.999.999-99");
     * 
     * final KafkaMessage clienteMessage = new KafkaMessage(VOUCHER_CLIENTE_TOPIC.getDescription(),
     * voucher, cliente); clienteMessage.configClientId(VIA_VAREJO);
     * clienteMessage.configCustomMessageSerializer(ClienteSerializer.class);
     * 
     * kafkaConnector.publish(clienteMessage, message ->
     * LOGGER.info("Notificando a inclusão do voucher para o cliente.")); }
     * 
     * private static Long gerarNumVoucher() { return (long) (1000000 * Math.random()); }
     */
}
