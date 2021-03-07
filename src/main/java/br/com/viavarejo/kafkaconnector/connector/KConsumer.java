package br.com.viavarejo.kafkaconnector.connector;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.Deserializer;

import br.com.viavarejo.kafkaconnector.config.SubscriptionConfig;

public abstract class KConsumer<K, V, C> {

    private final List<String> topics = new ArrayList<>();
    private String groupId;
    private SubscriptionConfig config;

    public KConsumer(final List<String> hosts) {
        if (hosts == null || hosts.isEmpty()) {
            throw new IllegalArgumentException("A lista de hosts não pode ser nula ou vazia.");
        }
        config.addHosts(hosts);
    }

    public KConsumer(final String groupId) {
        config = new SubscriptionConfig();
        this.groupId = groupId;
    }

    public String getGroupId() {
        return groupId;
    }

    public KConsumer<K, V, C> addHosts(final List<String> hosts) {
        config.addHosts(hosts);
        return this;
    }

    public KConsumer<K, V, C> addHost(final String host) {
        config.addHost(host);
        return this;
    }

    public KConsumer<K, V, C> configStringMessageDeserializer() {
        config.configStringMessageDeserializer();
        return this;
    }

    public KConsumer<K, V, C> configStringKeyDeserializer() {
        config.configStringKeyDeserializer();
        return this;
    }

    public KConsumer<K, V, C> configLongMessageDeserializer() {
        config.configLongMessageDeserializer();
        return this;
    }

    public KConsumer<K, V, C> configLongKeyDeserializer() {
        config.configLongKeyDeserializer();
        return this;
    }

    public KConsumer<K, V, C> configDoubleMessageDeserializer() {
        config.configDoubleMessageDeserializer();
        return this;
    }

    public KConsumer<K, V, C> configDoubleKeyDeserializer() {
        config.configDoubleKeyDeserializer();
        return this;
    }

    public KConsumer<K, V, C> configFloatMessageDeserializer() {
        config.configFloatMessageDeserializer();
        return this;
    }

    public KConsumer<K, V, C> configFloatKeyDeserializer() {
        config.configFloatKeyDeserializer();
        return this;
    }

    public KConsumer<K, V, C> configIntegerMessageDeserializer() {
        config.configIntegerMessageDeserializer();
        return this;
    }

    public KConsumer<K, V, C> configIntegerKeyDeserializer() {
        config.configIntegerKeyDeserializer();
        return this;
    }

    public KConsumer<K, V, C> configShortMessageDeserializer() {
        config.configShortMessageDeserializer();
        return this;
    }

    public KConsumer<K, V, C> configShortKeyDeserializer() {
        config.configShortKeyDeserializer();
        return this;
    }

    public KConsumer<K, V, C> configBytesMessageDeserializer() {
        config.configBytesMessageDeserializer();
        return this;
    }

    public KConsumer<K, V, C> configBytesKeyDeserializer() {
        config.configBytesKeyDeserializer();
        return this;
    }

    public KConsumer<K, V, C> configCustomKeyDeserializer(final Class<? extends Deserializer<?>> deserializerKeyType) {
        config.configCustomKeyDeserializer(deserializerKeyType);
        return this;
    }

    public KConsumer<K, V, C> configCustomMessageDeserializer(final Class<? extends Deserializer<?>> deserializerMessageType) {
        config.configCustomMessageDeserializer(deserializerMessageType);
        return this;
    }

    public KConsumer<K, V, C> configClientId(final String clientId) {
        config.configClientId(clientId);
        return this;
    }

    public String getClientId() {
        return config.getClientId();
    }

    public KConsumer<K, V, C> configPollAttempts(final int attempts) {
        config.configPollAttempts(attempts);
        return this;
    }

    public KConsumer<K, V, C> addTopic(final String topicName) {
        String topic = null;
        if (topicName == null) {
            throw new IllegalArgumentException("O tópico não pode ser nulo");
        }
        topic = topicName.trim();
        if (topic.isEmpty()) {
            throw new IllegalArgumentException("O tópico não pode ser vazio");
        }
        topics.add(topic);
        return this;
    }

    public SubscriptionConfig getConfig() {
        return config;
    }

    public List<String> getTopics() {
        return topics;
    }

    public int getPollAttempts() {
        return config.getPollAttempts();
    }

    public KConsumer<K, V, C> configMaxNumberMessages(final int maxNumberMessages) {
        config.configMaxNumberMessages(maxNumberMessages);
        return this;
    }

    public int getMaxNumberMessages() {
        return config.getMaxNumberMessages();
    }

    public void subscribe(final Consumer<C> callback) {
        subscribe(callback, null);
    }

    public void subscribe(final Consumer<C> callback, final Consumer<C> onError) {

        if (getGroupId() == null || getGroupId().trim().length() <= 0) {
            throw new IllegalArgumentException("Falha no consumo das mensagens do kafka. O grupo do consumer não deve ser nulo ou vazio.");
        }

        if (getClientId() == null || getClientId().trim().length() <= 0) {
            throw new IllegalArgumentException(
                            "Falha no consumo das mensagens do kafka. O CLIENT ID do consumer não deve ser nulo ou vazio.");
        }

        if (callback == null) {
            throw new IllegalArgumentException("Falha no consumo das mensagens do kafka. O callback de consumo não pode ser nulo.");
        }

        final org.apache.kafka.clients.consumer.Consumer<K, V> kConsumer = parseConsumer();

        ConsumerRecords<K, V> records = null;

        int attempts = getPollAttempts();
        final Duration waiting = Duration.ofMillis(100);
        // eh com esse laco que estamos implementando o mecanismo de heartbeat,
        // fundamental para comunicar ao group coordinator do kafka que o consumer esta
        // ativo no grupo, caso contrario o consumer sera marcado como inativo e nao vai
        // ler as mensagens dos topicos em que esta inscrito.
        while (--attempts >= 0) {
            try {
                records = kConsumer.poll(waiting);
            } catch (final WakeupException e) {
                kConsumer.close();
                throw new IllegalStateException("Falha na comunicação com o servidor Kafka", e);
            }

            if (records.count() > 0) {
                commitOffset(callback, onError, kConsumer, records);
                break;
            }
        }
        kConsumer.close();
    }

    public abstract void commitOffset(final Consumer<C> callback, final Consumer<C> onError,
                    final org.apache.kafka.clients.consumer.Consumer<K, V> kConsumer, final ConsumerRecords<K, V> records);

    private Map<String, Object> createConfigMap(final SubscriptionConfig config, final String groupId) {
        final Map<String, Object> configMap = new HashMap<>();
        configMap.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, config.appendHosts());
        configMap.put(ConsumerConfig.CLIENT_ID_CONFIG, config.getClientId());
        configMap.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, config.getKeyDeserializer());
        configMap.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, config.getMessageDeserializer());

        // Estamos fazendo o commit do offset manualmente sempre apos a leitura dos
        // registros pelo consumer.
        configMap.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);

        // Parametro que define que o consumer ira recuperar as mensagens a partir do offset mais
        // recente, isto eh, a partir da ultima mensagem recuperada, que pode ser diferente da
        // ultima mensagem publicada.
        configMap.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        // Configurando a que grupo de consumidores o consumer sera incluido.
        configMap.put("group.id", groupId);

        configMap.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, config.getMaxNumberMessages());
        configMap.put(ConsumerConfig.RECONNECT_BACKOFF_MS_CONFIG, 10000);

        return configMap;
    }

    private org.apache.kafka.clients.consumer.Consumer<K, V> parseConsumer() {
        addHosts(config.getHosts());
        final KafkaConsumer<K, V> kConsumer = new KafkaConsumer<>(createConfigMap(getConfig(), getGroupId()));
        kConsumer.subscribe(getTopics());
        return kConsumer;
    }

}
