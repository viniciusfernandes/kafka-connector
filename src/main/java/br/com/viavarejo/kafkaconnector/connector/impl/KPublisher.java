package br.com.viavarejo.kafkaconnector.connector.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.Serializer;

import br.com.viavarejo.kafkaconnector.config.PublishingConfig;
import br.com.viavarejo.kafkaconnector.connector.PublishingException;

public class KPublisher {

    private final String topicName;
    private final Object key;
    private final Object value;

    private final PublishingConfig config;

    public KPublisher(final String topicName, final Object key, final Object value) {
        if (topicName == null || topicName.trim().isEmpty()) {
            throw new IllegalArgumentException("O tópico da mensagem é obrigatório.");
        }
        if (value == null) {
            throw new IllegalArgumentException("O valor da mensagem é obrigatório.");
        }

        this.topicName = topicName;
        this.key = key;
        this.value = value;

        config = new PublishingConfig();
        if (key != null) {
            config.configKeySerializer(key.getClass());
        }
        config.configMessageSerializer(value.getClass());
    }

    public KPublisher addHosts(final List<String> hosts) {
        config.addHosts(hosts);
        return this;
    }

    public KPublisher addHost(final String host) {
        config.addHost(host);
        return this;
    }

    public Object getKey() {
        return key;
    }

    public Object getValue() {
        return value;
    }

    public KPublisher configClientId(final String clientId) {
        config.configClientId(clientId);
        return this;
    }

    public String getClientId() {
        return config.getClientId();
    }

    public PublishingConfig getConfig() {
        return config;
    }

    public String getTopicName() {
        return topicName;
    }

    public KPublisher configCustomKeySerializer(final Class<? extends Serializer<?>> serializerClass) {
        config.configCustomKeySerializer(serializerClass);
        return this;
    }

    public KPublisher configCustomMessageSerializer(final Class<? extends Serializer<?>> serializerClass) {
        config.configCustomMessageSerializer(serializerClass);
        return this;
    }

    public boolean hasConfigSerializer() {
        return config.hasConfigSerializer();
    }

    @Override
    public String toString() {
        return "[topicName=" + topicName + ", key=" + key + ", value=" + value + "]";
    }

    public void publish() {
        publish(null);
    }

    public void publish(final Runnable onPublish) {
        if (value == null) {
            throw new IllegalArgumentException("O valor da mensagem a ser publicada no kafka nao pode ser nulo.");
        }
        if (getClientId() == null) {
            throw new IllegalArgumentException("A mensagem a ser publicada no kafka deve conter um clientId. Mensagem: " + toString());
        }
        if (!hasConfigSerializer()) {
            throw new IllegalArgumentException(
                            String.format("A mensagem deve ter a classes de serialização configurada. Mensagem: %s", toString()));
        }

        final KafkaProducer<Object, Object> producer = new KafkaProducer<>(createConfigMap(getConfig()));

        final Future<RecordMetadata> publishing =
                        producer.send(new ProducerRecord<Object, Object>(getTopicName(), getKey(), getValue()), (metadata, exception) -> {
                            if (onPublish != null) {
                                onPublish.run();
                            }
                        });
        try {
            // Recuperando o MetaData que conte
            final RecordMetadata metadata = publishing.get();
            if (!metadata.hasOffset()) {
                throw new PublishingException(String.format("Falha na publicação da mensagem no Kafka. Mensagem: %s", toString()));
            }
        } catch (final Exception e) {
            throw new PublishingException(String.format(
                            "Falha na comunicação com o Kafka. Não foi possível publicar a mensagem enviada. Mensagem: %s", toString()), e);
        } finally {
            producer.close();
        }

    }

    private Map<String, Object> createConfigMap(final PublishingConfig config) {
        final Map<String, Object> configMap = new HashMap<>();
        configMap.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, config.appendHosts());
        configMap.put(ProducerConfig.CLIENT_ID_CONFIG, config.getClientId());
        configMap.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, config.getKeySerializer());
        configMap.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, config.getMessageSerializer());

        // Configurando o timeout de publicacao das mensagens para que se evite que o publisher faca
        // varias tentativas de publicacao no caso de falha na comunicacao com o kafka broker. O
        // valor configurado eh indicado para um cenario de alta consistencia.
        configMap.put(ProducerConfig.RETRIES_CONFIG, 10);
        configMap.put(ProducerConfig.TRANSACTION_TIMEOUT_CONFIG, 6000);

        // Configurando que cada mensagem enviada esteja em todas as réplicas de sincronização
        // respeitando o atributo min.insync.replicas
        configMap.put(ProducerConfig.ACKS_CONFIG, "all");

        configMap.put(ProducerConfig.RECONNECT_BACKOFF_MS_CONFIG, 10000);
        return configMap;
    }

}
